package com.cuiyun.kfcoding.rest.modular.base.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cuiyun.kfcoding.core.base.tips.SuccessTip;
import com.cuiyun.kfcoding.core.base.warpper.BaseControllerWarpper;
import com.cuiyun.kfcoding.core.page.PageInfoBT;
import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.core.util.FileUtil;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.modular.common.model.User;
import com.cuiyun.kfcoding.rest.modular.common.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: kfcoding
 * @description: 所有controller的基础类
 * @author: maple
 * @create: 2018-05-05 21:11
 **/
public class BaseController {

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    protected IUserService userService;

    protected static String SUCCESS = "SUCCESS";
    protected static String ERROR = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected SuccessTip SUCCESSTIP = new SuccessTip();

    protected Map<String, Object> MAP = new HashMap<>();

    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }

    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }

    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }

    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 返回前台文件流
     *
     * @author maple
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {
        byte[] bytes = FileUtil.toByteArray(filePath);
        return renderFile(fileName, bytes);
    }

    /**
     * 返回前台文件流
     *
     * @author maple
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }

    protected User getUser(HttpServletRequest request){
        String token = (String) request.getAttribute("token");
        if (StringUtils.isBlank(token)){
//            User user = new User();
//            user.setName("测试");
//            return user;
            return null;
        }
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.selectById(userId);
        return user;
    }
}

