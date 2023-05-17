package vn.btl.hdvauthenservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class AuthenException extends RuntimeException{

    private String code;
    private String message;
    private HttpStatus status;

    private Object data;

    public AuthenException() {
        super();
    }

    public AuthenException(AuthenErrorCode code) {
        super();
        this.code = code.code();
        this.message = code.message();
        this.status = code.status();
    }

    public AuthenException(AuthenErrorCode code, String message) {
        super();
        this.code = code.code();
        this.message = message;
        this.status = code.status();
    }

    public AuthenException(AuthenErrorCode code, Map<?, ?> data) {
        super();
        this.code = code.code();
        this.message = code.message();
        this.status = code.status();
        this.data = data;
    }
}
