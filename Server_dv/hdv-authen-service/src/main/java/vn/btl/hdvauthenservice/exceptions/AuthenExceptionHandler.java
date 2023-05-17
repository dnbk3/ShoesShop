package vn.btl.hdvauthenservice.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.btl.hdvauthenservice.utils.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
public class AuthenExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthenExceptionHandler.class);

    @ExceptionHandler(value = AuthenException.class)
    public Object handleAppException(HttpServletRequest request, AuthenException re)
            throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(re.getMessage());
        return new ResponseEntity<>(errorResponse, re.getStatus());
    }



    @ExceptionHandler(value = Exception.class)
    public Object handleCoreException(HttpServletRequest request, Exception re)
            throws IOException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(re.getMessage());
        re.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }


}