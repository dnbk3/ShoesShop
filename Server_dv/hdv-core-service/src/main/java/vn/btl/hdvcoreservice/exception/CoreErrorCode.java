package vn.btl.hdvcoreservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
public enum CoreErrorCode {

    SUCCESS(HttpStatus.OK, "Thành công"),
    PERMISSION_DENIED(HttpStatus.OK,"Permission Denied"),
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Có lỗi xảy ra, xin vui lòng thử lại sau ít phút"),
    ENTITY_NOT_EXISTS(HttpStatus.NOT_FOUND,"Thực thể không tồn tại"),
    ENTITY_EXISTED(HttpStatus.BAD_REQUEST,"Thực thể đã tồn tại"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"Truyền sai tham số"),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "Mã truy cập hết hạn"),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "Không tìm thấy mã truy cập"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "Mã truy cập không hợp lệ"),
    CANNOT_SEND_MESSAGE(HttpStatus.BAD_REQUEST,"Không thể gửi thông báo"),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST, "Thông tin xác thực bị thiếu hoặc không chính xác"),
    MISS_PARAM(HttpStatus.BAD_REQUEST, "Truyền lên thiếu tham số");

    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
