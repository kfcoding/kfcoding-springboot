package com.cuiyun.kfcoding.rest.modular.auth.controller;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.controller.dto.AuthPasswordRequest;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.auth.validator.impl.DbValidator;
import com.cuiyun.kfcoding.rest.modular.auth.validator.impl.GithubValidator;
import com.cuiyun.kfcoding.rest.modular.base.controller.BaseController;
import com.cuiyun.kfcoding.rest.util.STSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * 请求验证的
 *
 * @author maple
 * @Date 2017/8/24 14:22
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(description = "权限相关接口")
public class AuthController extends BaseController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GithubValidator githubValidator;

    @Autowired
    private DbValidator dbValidator;

    @Value("${oss.bucketName}")
    private String bucketName;


    @RequestMapping(value = "${jwt.auth-path}", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "获取token", notes="")
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request, @RequestBody AuthPasswordRequest authPasswordRequest) {

        map = new HashMap<>();
        SUCCESSTIP = new SuccessTip();

        String userId = null;
        switch (authPasswordRequest.getAuthType()) {
            case GITHUB:  // 若是github登陆
                userId = githubValidator.validate(authPasswordRequest);
                break;
            case PASSWORD: // 若是账号密码登陆
                userId = dbValidator.validate(authPasswordRequest);
                break;
        }
        if (userId == null)
            throw new KfCodingException(BizExceptionEnum.AUTH_REQUEST_ERROR);

        String token = jwtTokenUtil.generateToken(userId, jwtTokenUtil.getRandomKey());
        map.put("token", token);
        SUCCESSTIP.setResult(map);
        return ResponseEntity.ok(SUCCESSTIP);
    }



    @RequestMapping(value = "/auth/sts/{kongfuid}", method = RequestMethod.GET)
    @ApiOperation(value = "获取sts临时身份", notes="")
    public ResponseEntity<?> getSts(@PathVariable String kongfuid) throws ClientException {
        StringBuffer sb = new StringBuffer();
        sb.append(bucketName).append("/").append(kongfuid).append("/*");
        AssumeRoleResponse response = STSUtil.instance().getAssumeRoleResponse(bucketName + "/" + kongfuid + "/*");
        map = new HashMap<>();
        SUCCESSTIP = new SuccessTip();
        map.put("assumeRoleResponse", response);
        SUCCESSTIP.setResult(map);
        return ResponseEntity.ok(SUCCESSTIP);
    }
}
