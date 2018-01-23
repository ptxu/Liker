package com.liker.services.http.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.liker.services.http.common.ResponseResult;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 异常统一处理类
 * @author xupengtao
 * @date 2018年1月12日 下午6:32:45
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @Title: operateExp
     * @Description: 异常处理
     * @param ex
     *            RuntimeException
     * @param request
     *            HttpServletRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> operateExp(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResponseResult<?> result = new ResponseResult<>();
        result.setCode(500);
        result.setMessage(String.format("system error： %s", ex.getMessage()));
        LOGGER.error("system error", ex);
        response.setContentType("application/json;charset=utf-8");
        return ResponseEntity.ok(result.toJson());
    }

}