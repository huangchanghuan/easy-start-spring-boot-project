package com.cmic.sim.maap.core.exception;

import com.cmic.sim.maap.core.enums.ResultCode;
import com.cmic.sim.maap.core.wrapper.WrapMapper;
import com.cmic.sim.maap.core.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author haiqiang
 * @date 2019/4/8 10:51
 */
@Slf4j
@ControllerAdvice
public class ControllerException {
    /**
     * 参数验证不通过
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Wrapper unknownException1(MethodArgumentNotValidException e){
        log.error("参数异常:{}",e.getBindingResult().getFieldError().getDefaultMessage());
        return WrapMapper.wrap(ResultCode.CODE_905.getCode(),e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 参数验证不通过
     * @param e
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public Wrapper unknownException2(ConstraintViolationException e){
//        log.error("参数异常:{}",e.getMessage());
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return WrapMapper.wrap(ResultCode.CODE_905.getCode(),"参数验证失败" + message);
    }
    /**
     * 业务异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper businessException(BusinessException e) {
        log.error("业务异常={}", e.getMessage(), e);
        return WrapMapper.wrap(e.getCode() == 0 ? Wrapper.ERROR_CODE : e.getCode(), e.getMessage());
    }

    /**
     * 对控制层未知异常进行统一处理
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object unknownException(Exception e){
        log.error("全局异常:{}",e.getMessage());
        e.printStackTrace();
        return WrapMapper.error();
    }


}
