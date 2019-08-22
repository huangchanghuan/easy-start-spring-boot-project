package com.cmic.sim.maap.core.enums;

/**
 * @author haiqiang
 * @date 2019/2/22 14:07
 */
public enum ResultCode {

    CODE_0(0,"SUCCESS"),
    CODE_601(601,"查询失败"),
    CODE_602(602,"更新失败"),
    CODE_603(603,"删除数据失败"),
    CODE_604(604,"新增数据失败"),
    CODE_605(605,"用户文件上传失败"),
    CODE_606(606,"用户签名失败"),
    CODE_607(607,"签名文件回传失败"),
    CODE_608(608,"验签失败"),
    CODE_609(609,"私钥加密失败"),
    CODE_701(701,"请求正在处理，请稍后"),
    CODE_897(897,"该签名相关证书不存在！"),
    CODE_898(898,"当前用户未进行实名认证！"),
    CODE_899(899,"当前登录用户不匹配"),
    CODE_900(900,"用户申请证书失败"),
    CODE_901(901,"合成新签章文件失败"),
    CODE_902(902,"上传的文件为空或者非PDF文件"),
    CODE_903(903,"该用户已经申请证书"),
    CODE_904(904,"SIM业务繁忙，请重试"),
    CODE_905(905,"请求参数信息错误,请检验"),
    CODE_906(906,"选择签名的页数超过实际页数"),
    CODE_907(907,"待展示内容超过117个字节"),
    CODE_908(908,"该文件已经完成签名的最大次数，请勿重试"),
    CODE_909(909,"该用户不存在，请确认用户ID"),
    CODE_910(910,"服务器未知异常"),
    CODE_911(911,"已存在授权"),
    CODE_912(912,"已存在授权流程,正在运行处理,稍后再试"),
    CODE_913(913,"不存在该印章"),
    CODE_914(914,"不存在对应apId的企业"),
    CODE_915(915,"找不到用户,userId=%s"),
    CODE_916(916,"找不到签名记录,signatureId=%s"),
    CODE_917(917,"该签名未完成,signatureId=%s"),
    CODE_918(918,"印章ID不能为空");

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
