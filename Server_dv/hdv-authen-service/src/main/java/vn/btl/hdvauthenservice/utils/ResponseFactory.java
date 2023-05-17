package vn.btl.hdvauthenservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseFactory {

    public static ResponseEntity success() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static ResponseEntity success(Object data, Class clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse();
        responseObject.setData(clazz.cast(data));
        return ResponseEntity.ok(responseObject);
    }

    public static ResponseEntity permissionDenied(String messages) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity badRequest(String messages) {
        return ResponseEntity.badRequest().body(messages);
    }

    public static ResponseEntity success(List<Object> data) {
        GeneralList<Object> responseObject = new GeneralList<>();
        responseObject.setData(data);
        return ResponseEntity.ok(responseObject);
    }

    public static <T> ResponseEntity success(List<T> data, PageResponse pageResponse) {
        GeneralPageResponse<T> responseObject = new GeneralPageResponse<>(data, pageResponse);
        return ResponseEntity.ok(responseObject);
    }

}
