package vn.btl.hdvcoreservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class CoreException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public CoreException(CoreErrorCode errorCode){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public CoreException(CoreErrorCode errorCode, String message){
        this.status = errorCode.getStatus();
        this.message = message;
    }
}
