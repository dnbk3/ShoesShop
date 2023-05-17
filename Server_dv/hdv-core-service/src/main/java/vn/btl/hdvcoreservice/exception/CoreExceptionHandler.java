package vn.btl.hdvcoreservice.exception;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.btl.hdvcoreservice.utils.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.BindException;

@ControllerAdvice
public class CoreExceptionHandler {


    @ExceptionHandler(value = CoreException.class)
    public Object handleAppException(HttpServletRequest request, CoreException re)
            throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(re.getMessage());
        return new ResponseEntity<>(errorResponse, re.getStatus());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Object handleMissingParamException(HttpServletRequest request, MissingServletRequestParameterException re)
            throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Miss params");
        return new ResponseEntity<>(errorResponse, CoreErrorCode.MISS_PARAM.getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public Object handleException(HttpServletRequest request, BindException re) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(CoreErrorCode.GENERAL_ERROR.getMessage());
        return new ResponseEntity<>(errorResponse, CoreErrorCode.GENERAL_ERROR.getStatus());
    }

}
