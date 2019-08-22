package com.cmic.sim.maap.core.enums;

/**
 * 响应枚举类
 * TODO 需要定义code的规则
 * @author huangchanghuan
 * @date 2019/2/22 14:07
 */
public enum ResultCode {
    /**
     * success
     */
    CODE_0(0,"SUCCESS"),

    /**
     * 请求参数信息错误
     */
    CODE_905(905,"请求参数信息错误,请检验");


    private Integer code;
    private String message;

    private ResultCode(Integer code, String message){
        this.code = code;
        this.message =message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
