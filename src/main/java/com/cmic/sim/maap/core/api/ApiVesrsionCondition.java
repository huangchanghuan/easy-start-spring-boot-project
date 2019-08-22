package com.cmic.sim.maap.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

public class ApiVesrsionCondition implements RequestCondition<ApiVesrsionCondition> {
    private static final Logger logger = LoggerFactory.getLogger(ApiVesrsionCondition.class);

    private int apiVersion;

    public ApiVesrsionCondition(int apiVersion){
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVesrsionCondition combine(ApiVesrsionCondition other) {
        logger.debug("====combine");
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVesrsionCondition(other.getApiVersion());
    }

    @Override
    public ApiVesrsionCondition getMatchingCondition(HttpServletRequest request) {
        logger.debug("====getMatchingCondition");
        String ver = request.getHeader("Api-Version");
        // 如果请求的版本号大于等于配置版本号， 则满足
        if(ver==null||"".equals(ver)||Integer.valueOf(ver) >= this.apiVersion) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(ApiVesrsionCondition other, HttpServletRequest request) {
        logger.debug("====compareTo");
        // 优先匹配最新的版本号
        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        logger.debug("====getApiVersion");
        return apiVersion;
    }

    public static void main(String[] args) {

    }
}
