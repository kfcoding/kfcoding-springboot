package com.cuiyun.kfcoding.rest.modular.github.controller;

import com.alibaba.fastjson.JSONObject;
import com.cuiyun.kfcoding.core.base.controller.BaseController;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.exception.KfCodingException;
import com.cuiyun.kfcoding.rest.common.exception.BizExceptionEnum;
import com.cuiyun.kfcoding.rest.modular.github.application.OauthGithub;
import com.cuiyun.kfcoding.rest.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


/**
 * @program: kfcoding
 * @description: github第三方登录控制类
 * @author: maple
 * @create: 2018-05-07 10:37
 **/
@RestController
@RequestMapping("/api/github")
public class OauthGithubController extends BaseController{
    //OAuth2.0标准协议建议，利用state参数来防止CSRF攻击。可存储于session或其他cache中
    private static final String SESSION_STATE = "GITHUB";
    private static Logger log = LoggerFactory.getLogger(OauthGithubController.class);

    @ResponseBody
    @RequestMapping("/callback")
    public ResponseEntity<SuccessTip> callback(HttpServletRequest request){
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        // 取消了授权
        if (StringUtils.isBlank(state)|| StringUtils.isBlank(code)){
            throw new KfCodingException(BizExceptionEnum.GITHUB_CANCAL_OAUTH);
        }
        //清除state以防下次登录授权失败
        //session.removeAttribute(SESSION_STATE);
        //获取用户信息
        try{
            JSONObject userInfo = OauthGithub.me().getUserInfoByCode(code);
            log.debug(userInfo.toString());
            String type = "github";
            // 将相关信息存储数据库...

            SUCCESSTIP.setMessage(userInfo.toString());
            return ResponseEntity.ok(SUCCESSTIP);
        }catch(Exception e){
            e.printStackTrace();
        }
        //这里你们可以自己修改，授权成功后，调到首页
        return ResponseEntity.ok(SUCCESSTIP);
    }

    /**
     * 构造授权请求url
     * @return void    返回类型
     * @throws
     */
    @ResponseBody
    @RequestMapping("/login")
    public ResponseEntity<SuccessTip> index(HttpServletRequest request, HttpServletResponse response){

        String state = TokenUtil.randomState();
        //state就是一个随机数，保证安全
        try {
            String url = OauthGithub.me().getAuthorizeUrl(state);
            SUCCESSTIP.setMessage("redirect:"+url);
            return ResponseEntity.ok(SUCCESSTIP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new KfCodingException(BizExceptionEnum.GITHUB_ERROR_URL);
    }
}

