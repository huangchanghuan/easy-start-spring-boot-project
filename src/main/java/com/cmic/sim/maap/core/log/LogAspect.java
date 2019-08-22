package com.cmic.sim.maap.core.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger("athena-log");
    private ElkLogBean elkLogBean = new ElkLogBean();

    @Pointcut("execution(* com.cmic.sim.maap.system.controller.*.*(..))")
    public void ELKLog() {
    }

    @Before("ELKLog()")
    public void before(JoinPoint joinPoint) {
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            elkLogBean.setLogTime(new Date());
            elkLogBean.setRequestTime(new Date());
            elkLogBean.setSessionID(request.getSession().getId());
            elkLogBean.setSourceIP(getClientIp(request));
            elkLogBean.setServerAddress(getServerIp(request) + ":" + request.getLocalPort());
            if (joinPoint.getArgs().length > 0) {
                elkLogBean.setRequest(joinPoint.getArgs()[0]);
            }
        } catch (Exception e) {
        }
    }

    @AfterReturning(pointcut = "ELKLog()", returning = "result")
    public void afterReturning(Object result) {
        try {
            elkLogBean.setResponseTime(new Date());
            elkLogBean.setResponse(result);
            logger.info(elkLogBean.toString());
        } catch (Exception e) {
        }
    }

    private static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    private static String getServerIp(HttpServletRequest request) {
        String remoteAddr = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            remoteAddr = addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getLocalAddr();
        }
        return remoteAddr;
    }
}
