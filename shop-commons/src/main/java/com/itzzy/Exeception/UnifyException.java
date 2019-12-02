package com.itzzy.Exeception;

import com.itzzy.commons.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UnifyException {
    @ExceptionHandler(value = DaoException.class)
    @ResponseBody
    public ServerResponse exceptionresponse(DaoException ex) {
        return ServerResponse.errornologin(ex.getCode());
    }
}

