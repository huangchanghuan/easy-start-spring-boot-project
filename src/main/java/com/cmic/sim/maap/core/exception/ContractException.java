package com.cmic.sim.maap.core.exception;

import com.cmic.sim.maap.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haiqiang
 * @date 2019/2/22 14:03
 */
@Slf4j
public class ContractException extends BusinessException {
    private static final long serialVersionUID = -8269204545659819322L;


    /**
     * Instantiates a new Uac rpc exception.
     */
    public ContractException() {
    }

    /**
     * Instantiates a new Uac rpc exception.
     *
     * @param code      the code
     * @param msgFormat the msg format
     * @param args      the args
     */
    public ContractException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
        log.info("<== ContractException, code:" + this.code + ", message:" + super.getMessage());

    }

    /**
     * Instantiates a new Uac rpc exception.
     *
     * @param code the code
     * @param msg  the msg
     */
    public ContractException(int code, String msg) {
        super(code, msg);
        log.info("<== ContractException, code:" + this.code + ", message:" + super.getMessage());
    }

    /**
     * Instantiates a new Uac rpc exception.
     *
     * @param codeEnum the code enum
     */
    public ContractException(ResultCode codeEnum) {
        super(codeEnum.getCode(), codeEnum.getMessage());
        log.info("<== ContractException, code:" + this.code + ", message:" + super.getMessage());
    }

    /**
     * Instantiates a new Uac rpc exception.
     *
     * @param codeEnum the code enum
     * @param args     the args
     */
    public ContractException(ResultCode codeEnum, Object... args) {
        super(codeEnum, args);
        log.info("<== OpcRpcException, code:" + this.code + ", message:" + super.getMessage());
    }
}
