package com.daxiang.controller;

import com.daxiang.exception.BusinessException;
import com.daxiang.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiangyitao.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 未处理的异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error("未处理的异常", e);
        return Response.error(e.getMessage());
    }

    /**
     * 权限不足
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public Response handleAccessDeniedException() {
        return Response.accessDenied();
    }

    /**
     * BusinessException
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Response handleBusinessException(BusinessException e) {
        return Response.fail(e.getMessage());
    }

    /**
     * 参数校验不通过，非@RequestBody
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Response handleBindException(BindException e) {
        return Response.fail(e.getFieldError().getDefaultMessage());
    }

    /**
     * 参数校验不通过，@RequestBody
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }

}
