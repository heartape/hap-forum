package com.heartape.hap.handler;

import com.heartape.hap.exception.BusinessException;
import com.heartape.hap.response.ErrorResult;
import com.heartape.hap.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 违反注解约束
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request){
        String path = request.getRequestURI();
        log.info("\nexception:{},\npath:{}\ncaused by:{}",e.getClass(),path,e.getMessage());
        e.printStackTrace();
        return ErrorResult.error(ResultCode.PARAM_IS_INVALID, path);
    }

    /**
     * 验证参数封装错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request){
        String path = request.getRequestURI();
        log.info("\nexception:{},\npath:{}\ncaused by:{}",e.getClass(),path,e.getMessage());
        e.printStackTrace();
        return ErrorResult.error(ResultCode.PARAM_IS_INVALID, path);
    }

    /**
     * 参数绑定错误
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleBindException(BindException e, HttpServletRequest request){
        String path = request.getRequestURI();
        log.info("\nexception:{},\npath:{}\ncaused by:{}",e.getClass(),path,e.getMessage());
        e.printStackTrace();
        return ErrorResult.error(ResultCode.PARAM_IS_INVALID, path);
    }

    /**
     * 在@validated注解参数验证错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        String path = request.getRequestURI();
        log.info("\nexception:{},\npath:{}\ncaused by:{}",e.getClass(),path,e.getMessage());
        e.printStackTrace();
        return ErrorResult.error(ResultCode.PARAM_IS_INVALID, path);
    }

    /**
     * 自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ErrorResult handleBusinessException(BusinessException e, HttpServletRequest request){
        String path = request.getRequestURI();
        log.info("\nexception:{},\npath:{}\ncaused by:{}",e.getClass(),path,e.getMessage());
        e.printStackTrace();
        return ErrorResult.error(e.getExceptionEnum(),path);
    }

    /**
     * 所有异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult globalError(Throwable e, HttpServletRequest request){
        String path = request.getRequestURI();
        log.info("\nexception:{},\npath:{}\ncaused by:{}",e.getClass(),path,e.getMessage());
        e.printStackTrace();
        return ErrorResult.error(ResultCode.SYSTEM_INNER_ERROR, path);
    }
}
