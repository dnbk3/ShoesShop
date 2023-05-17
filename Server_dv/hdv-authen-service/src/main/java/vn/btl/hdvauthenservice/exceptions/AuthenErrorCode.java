package vn.btl.hdvauthenservice.exceptions;

import org.springframework.http.HttpStatus;

public enum AuthenErrorCode {

    SUCCESS(HttpStatus.OK, "200", "Thành công"),
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CORE-500", "Có lỗi xảy ra, xin vui lòng thử lại sau ít phút"),
    ENTITY_NOT_EXISTS(HttpStatus.NOT_FOUND,"CORE-404","Thực thể không tồn tại"),
    ENTITY_EXISTED(HttpStatus.BAD_REQUEST,"CORE-409","Thực thể đã tồn tại"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"CORE-402","Truyền sai tham số"),
    INVALID_SHOPID(HttpStatus.BAD_REQUEST,"CORE-403","ShopId isn't config in pod service"),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "CORE-405", "Mã truy cập hết hạn"),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "CORE-406", "Không tìm thấy mã truy cập"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "CORE-407", "Mã truy cập không hợp lệ"),
    CANNOT_SEND_MESSAGE(HttpStatus.BAD_REQUEST,"CORE-408","Không thể gửi thông báo"),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST, "CORE-409", "Thông tin xác thực bị thiếu hoặc không chính xác"),
    INVALID_USER_PASS(HttpStatus.UNAUTHORIZED, "CORE-401","Tài khoản hoặc mật khẩu không đúng, quý khách vui lòng kiểm tra và đăng nhập lại"),

    USER_INACTIVE(HttpStatus.UNAUTHORIZED, "CORE-401","Tài khoản đã tạm khóa, quý khách vui lòng kiểm tra và đăng nhập lại"),
    DATE_NOT_VALID(HttpStatus.BAD_REQUEST, "CORE-500", "Thời gian truyền vào không hợp lệ"),

    CANT_SEND_OTP(HttpStatus.SERVICE_UNAVAILABLE, "CORE-601", "Lỗi khi gửi tin nhắn mã OTP"), //"Error when send otp notification message"
    OTP_REFERENCE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "CORE-602", "Không tìm thấy mã tham chiếu OTP"), //OTP reference id is not found
    ERROR_SEND_OTP(HttpStatus.BAD_REQUEST, "CORE-603", "Gửi tin nhắn thông báo OTP không thành công"), //"send otp notification message is not successful"
    OTP_REFERENCE_ID_IS_REQUIRED(HttpStatus.BAD_REQUEST, "CORE-604", "Yêu cầu mã tham chiếu OTP"), //Otp_reference_id must be specified
    MAXIMUM_OTP_GENERATION_REACHED(HttpStatus.BAD_REQUEST, "CORE-605", "Đã đạt tối đa số lần gửi OTP"), //Maximum OTP generation reached
    CANT_INSTANT_REGENERATE_OTP_AFTER_CREATE(HttpStatus.BAD_REQUEST, "CORE-606", "Vui lòng không gửi lại quá nhanh"),
    OTP_USED(HttpStatus.BAD_REQUEST, "CORE-607", "Mã OTP đã được sử dụng"), //OTP code is already used
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "CORE-608", "Mã OTP đã hết hạn"), //OTP expired
    INVALID_OTP_REGISTER_REQUEST(HttpStatus.BAD_REQUEST, "CORE-609", "Yêu cầu số điện thoại khi đăng ký"), //Invalid OTP request
    INVALID_OTP_TYPE(HttpStatus.BAD_REQUEST, "CORE-610", "Kiểu OTP không hợp lệ"), //Invalid OTP type
    INVALID_MOBILE_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "CORE-611", "Định dạng số điện thoại không hợp lệ"), //Invalid mobile number format
    OTP_VERIFICATION_FAIL(HttpStatus.BAD_REQUEST, "CORE-612", "Xác minh mã OTP không thành công"), //OTP Verification fail
    MOBILE_REGISTERED(HttpStatus.BAD_REQUEST, "CORE-613", "Số điện thoại đã được đăng ký"), //"phone_number_registered", "Phone number is already registered before"
    EMAIL_REGISTERED(HttpStatus.BAD_REQUEST, "CORE-613", "Email đã được đăng ký"), //"phone_number_registered", "Phone number is already registered before"
    MOBILE_NOT_REGISTERED(HttpStatus.BAD_REQUEST, "CORE-614", "Số điện thoại chưa được đăng ký"), //"phone_number_not_registered", "Phone number is not registered"
    EMAIL_NOT_REGISTERED(HttpStatus.BAD_REQUEST, "CORE-614", "Email chưa được đăng ký"), //"phone_number_registered", "Phone number is already registered before"
    MAXIMUM_OTP_VERIFICATION_REACHED(HttpStatus.BAD_REQUEST, "CORE-615", "Đã đạt tối đa số lần gửi OTP"), //Maximum OTP generation reached
    MOBILE_BLOCKED(HttpStatus.FORBIDDEN, "CORE-616", "Số điện thoại này đã bị chặn do yêu cầu quá nhiều OTP"), //"blocked_mobile", "This mobile is blocked because request too many OTP"
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "CORE-617", "Mật khẩu không hợp lệ"), //"blocked_mobile", "This mobile is blocked because request too many OTP"
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "CORE-618", "Yêu cầu không hợp lệ"),
    PASS_NOT_EQUAL(HttpStatus.BAD_REQUEST,"CORE-619","Mật khẩu mới không được trùng với mật khẩu cũ"),//"blocked_mobile", "This mobile is blocked because request too many OTP"
    OLD_PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST,"CORE-620","Mật khẩu cũ không đúng"),

    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "CORE-711", "Định dạng email không hợp lệ"); //Invalid email format

    private final HttpStatus status;
    private String code;
    private String message;

    AuthenErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }

}
