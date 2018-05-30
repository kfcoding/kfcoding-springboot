package com.cuiyun.kfcoding.rest.modular.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.ErrorTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.base.enums.ThirdpartAuthTypeEnum;
import com.cuiyun.kfcoding.rest.modular.common.model.Thirdpart;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IThirdpartService;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.github.application.OauthGithub;
import com.cuiyun.kfcoding.rest.util.STSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 请求验证的
 *
 * @author maple
 * @Date 2017/8/24 14:22
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(description = "权限相关接口")
public class AuthController extends BaseController{

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private IThirdpartService thirdpartService;

    @Value("${oss.bucketName}")
    private String bucketName;


    @RequestMapping(value = "${jwt.auth-path}", method = RequestMethod.GET)
    @ApiOperation(value = "获取token", notes="")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String authType ,@RequestParam String code) {
//        String authType = authRequest.getAuthType();
//        String code = authRequest.getCode();
        //若是github登陆
        if (authType.equals(ThirdpartAuthTypeEnum.GITHUB.getValue())) {

            System.err.println("getTokenByCode：" + code);
            // 取消了授权
            if (StringUtils.isBlank(code)) {
                throw new KfCodingException(BizExceptionEnum.GITHUB_CANCAL_OAUTH);
            }
            try {
                //获取token
                String gitHubToken = OauthGithub.me().getTokenByCode(code);
                if (StringUtils.isBlank(gitHubToken))
                    throw new KfCodingException(BizExceptionEnum.GITHUB_CANCAL_OAUTH);
                JSONObject userInfo = OauthGithub.me().getUserInfo(gitHubToken);
                Thirdpart thirdpart = JSON.parseObject(userInfo.toJSONString(), new TypeReference<Thirdpart>() {
                });
                Thirdpart tempThirdPart = new Thirdpart();
                tempThirdPart.setThirdpartId(thirdpart.getId());
                // 如果信息不存在则添加用户
                User user;
                tempThirdPart = thirdpartService.selectOne(new EntityWrapper<>(tempThirdPart));
                if(tempThirdPart == null){
                    user = new User();
                    // TODO: 2018/5/19  密码生成策略
                    user.setPassword(RandomStringUtils.random(10, "1234567890"));
                    user.setAvatarUrl(thirdpart.getAvatarUrl());
                    user.setName(thirdpart.getLogin());
                    userService.insertOrUpdate(user);
                    tempThirdPart = changeThirdPart(thirdpart, tempThirdPart);
                    tempThirdPart.setUserId(user.getId());
                    tempThirdPart.setAuthType(ThirdpartAuthTypeEnum.GITHUB);
                    thirdpartService.insert(tempThirdPart);
                } else { // 存在就更新用户信息
                    user = userService.selectById(tempThirdPart.getUserId());
                    user.setAvatarUrl(thirdpart.getAvatarUrl());
                    user.setName(thirdpart.getLogin());
                    userService.insertOrUpdate(user);
                    tempThirdPart = changeThirdPart(thirdpart, tempThirdPart);
                    tempThirdPart.setAuthType(ThirdpartAuthTypeEnum.GITHUB);
                    thirdpartService.updateById(tempThirdPart);
                }
                String token = jwtTokenUtil.generateToken(tempThirdPart.getUserId().toString(), jwtTokenUtil.getRandomKey());
                map.put("token", token);
                SUCCESSTIP.setResult(map);
                return ResponseEntity.ok(SUCCESSTIP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ErrorTip errorTip = new ErrorTip(BizExceptionEnum.AUTH_REQUEST_ERROR.getCode(), BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage());
        return ResponseEntity.ok(errorTip);
    }

    /**
     *  构造信息
     */
    private Thirdpart changeThirdPart(Thirdpart thirdpart, Thirdpart tempThirdPart){

        if (tempThirdPart == null){
            String tempId = thirdpart.getId();
            thirdpart.setThirdpartId(tempId);
            thirdpart.setId(null);
            return thirdpart;
        } else {
            String tempId = tempThirdPart.getId();
            String userId = tempThirdPart.getUserId();
            BeanUtils.copyProperties(tempThirdPart , thirdpart);
            thirdpart.setId(tempId);
            thirdpart.setUserId(userId);
        }
        return thirdpart;
    }

    @RequestMapping(value = "/auth/sts/{kongfuid}", method = RequestMethod.GET)
    @ApiOperation(value = "获取sts临时身份", notes="")
    public ResponseEntity<?> getSts(HttpServletRequest request, @PathVariable String kongfuid) throws ClientException {
        StringBuffer sb = new StringBuffer();
        sb.append(bucketName).append("/").append(kongfuid).append("/*");
        AssumeRoleResponse response = STSUtil.instance().getAssumeRoleResponse(bucketName + "/" + kongfuid + "/*");
        map.put("assumeRoleResponse", response);
        SUCCESSTIP.setResult(map);
        return ResponseEntity.ok(SUCCESSTIP);
    }
}
