package com.cuiyun.kfcoding.rest.modular.github.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import com.cuiyun.kfcoding.rest.modular.github.application.OauthGithub;
import com.cuiyun.kfcoding.rest.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;


/**
 * @program: kfcoding
 * @description: github第三方登录控制类
 * @author: maple
 * @create: 2018-05-07 10:37
 **/
@RestController
@RequestMapping("/api/github")
@CrossOrigin(origins = "*")
@Api(description = "gitHub权限相关接口")
public class OauthGithubController extends BaseController{
    //OAuth2.0标准协议建议，利用state参数来防止CSRF攻击。可存储于session或其他cache中
    private static final String SESSION_STATE = "GITHUB";
    private static Logger log = LoggerFactory.getLogger(OauthGithubController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @ResponseBody
    @RequestMapping(path = "/callback", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "回调接口", notes="")
    public ResponseEntity<SuccessTip> callback(HttpServletRequest request) throws URISyntaxException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String url = request.getParameter("redirect");

        System.err.println("回调函数测试："+code+state);
        // 取消了授权
        if (StringUtils.isBlank(state)|| StringUtils.isBlank(code)){
            throw new KfCodingException(BizExceptionEnum.GITHUB_CANCAL_OAUTH);
        }
        // 清除state以防下次登录授权失败
        // session.removeAttribute(SESSION_STATE);
        // 获取用户信息
        try{
            JSONObject userInfo = OauthGithub.me().getUserInfoByCode(code);
            // 将user信息保存进数据库
            // 转为user类
            User user = JSON.parseObject(userInfo.toJSONString(), new TypeReference<User>(){});
            // 保存user
            userService.insertOrUpdate(user);

            log.debug(userInfo.toString());
            String token  =jwtTokenUtil.generateToken(user.getId().toString(), state);

            URI uri = new URI(url);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            ResponseEntity responseEntity = new ResponseEntity(token, httpHeaders, HttpStatus.SEE_OTHER);

            // 请求重定向
            return responseEntity;
        }catch(Exception e){
            e.printStackTrace();
        }
        URI uri = new URI(url);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        // 请求重定向
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        //return ResponseEntity.ok(SUCCESSTIP);
    }

    /**
     * 构造授权请求url
     * @return void    返回类型
     * @throws
     */
    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "构造授权请求url", notes="")
    public ResponseEntity<SuccessTip> index(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException {

        String state = TokenUtil.randomState();
        //state就是一个随机数，保证安全
        try {
            String url = OauthGithub.me().getAuthorizeUrl(state);
            URI uri = new URI(url);

            // SUCCESSTIP.setMessage("redirect:"+url);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            // 请求重定向
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new KfCodingException(BizExceptionEnum.GITHUB_ERROR_URL);
    }
}

