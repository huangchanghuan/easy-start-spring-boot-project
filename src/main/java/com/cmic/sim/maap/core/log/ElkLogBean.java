package com.cmic.sim.maap.core.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ElkLogBean {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private Date logTime;//打印日志时间 精确到毫秒2014-09-09 09:09:09:222
    private String accountType;//用户类型 0：passid 1:手机号 2:邮箱
    private String account;//用户 passid/手机号/邮箱
    private String operateType;//操作类型 见附件1：操作码表
    private String operateState;//操作状态 0：成功 1：失败
    private String channel;//渠道 0：wap 1：web 2：client
    private String sessionID;//会话ID
    private Date requestTime;//请求时间 精确到毫秒2014-09-09 09:09:09:222
    private Date responseTime;//响应时间 精确到毫秒2014-09-09 09:09:09:222
    private String transactionID;//事务ID
    private String sourceIP;//源IP
    private String serverAddress;//服务地址 IP:Port形式 标明在哪个服务器的哪个节点执行的
    private Object request;//请求体
    private Object response;//响应体

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public void setOperateState(String operateState) {
        this.operateState = operateState;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    private static String formatField(Object field) {
        if (field instanceof Date) {
            return dateFormat.format(field);
        }
        return field == null ? "" : String.valueOf(field);
    }

    private static String formatFields(Object... fields) {
        StringBuilder logString = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            logString.append(formatField(fields[i]));
            if (i < fields.length - 1) {
                logString.append("|");
            }
        }
        return logString.toString();
    }

    @Override
    public String toString() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("request", request);
//        jsonObject.put("response", response);
//        return formatFields(logTime, accountType, account, operateType, operateState, channel, sessionID, requestTime, responseTime, transactionID, sourceIP, serverAddress, jsonObject);
        return null;
    }
}
