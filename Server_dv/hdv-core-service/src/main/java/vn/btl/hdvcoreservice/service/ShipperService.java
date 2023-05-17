package vn.btl.hdvcoreservice.service;

import org.springframework.http.ResponseEntity;
import vn.btl.hdvcoreservice.request.ShipperRequest;

public interface ShipperService {

    ResponseEntity getAllShipper(String keyword);

    ResponseEntity addShipper(ShipperRequest request);

    ResponseEntity updateShipper(ShipperRequest request, Integer shipperId);

    ResponseEntity getOneShipper(Integer shipperId);

    ResponseEntity deleteShipper(Integer shipperId);
}
