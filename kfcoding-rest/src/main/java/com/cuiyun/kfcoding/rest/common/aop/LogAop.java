package com.cuiyun.kfcoding.rest.common.aop;

import com.cuiyun.kfcoding.core.support.http.HttpKit;
import com.cuiyun.kfcoding.rest.common.annotion.BussinessLog;
import com.cuiyun.kfcoding.rest.common.constant.dictmap.base.AbstractDictMap;
import com.cuiyun.kfcoding.rest.common.log.LogManager;
import com.cuiyun.kfcoding.rest.common.log.LogObjectHolder;
import com.cuiyun.kfcoding.rest.common.log.factory.LogTaskFactory;
import com.cuiyun.kfcoding.rest.modular.auth.util.JwtTokenUtil;
import com.cuiyun.kfcoding.rest.util.Contrast;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志记录
 *
 * @author maple
 * @date 2016年12月6日 下午8:48:30
 */
@Aspect
@Component
public class LogAop {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(com.cuiyun.kfcoding.rest.common.annotion.BussinessLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果当前用户未登录，不做日志
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String token = (String) request.getAttribute("token");
        String userId = jwtTokenUtil.getUsernameFromToken(token);
        if (userId == null){
            return;
        }
//        ShiroUser user = ShiroKit.getUser();
//        if (null == user) {
//            return;
//        }

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.value();
        String key = annotation.key();
        Class dictClass = annotation.dict();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        //如果涉及到修改,比对变化
        String msg;
        if (bussinessName.indexOf("修改") != -1 || bussinessName.indexOf("编辑") != -1) {
            Object obj1 = LogObjectHolder.me().get();
            Map<String, String> obj2 = HttpKit.getRequestParameters();
            msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
        } else {
            Map<String, String> parameters = HttpKit.getRequestParameters();
            AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
            msg = Contrast.parseMutiKey(dictMap,key,parameters);
        }

//        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(), bussinessName, className, methodName, msg));
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(userId, bussinessName, className, methodName, msg));
    }
}