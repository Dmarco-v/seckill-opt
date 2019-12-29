package com.dmarco.seckill.exception;

import com.dmarco.seckill.result.CodeMsg;
import com.dmarco.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Dmarco
 */

@ControllerAdvice //类似于对controller的切面
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){

        if(e instanceof GlobalException){
            GlobalException exception=(GlobalException) e;
            return Result.error(exception.getCm());
        }else if(e instanceof BindException){
            BindException exception=(BindException)e;
            List<ObjectError> errors= exception.getAllErrors();
            ObjectError error=errors.get(0);
            String msg=error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
