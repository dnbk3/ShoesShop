package vn.btl.hdvcoreservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.ShipperDao;
import vn.btl.hdvcoreservice.repository.entity.Shipper;
import vn.btl.hdvcoreservice.request.ShipperRequest;
import vn.btl.hdvcoreservice.service.ShipperService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

@Service
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    private ShipperDao shipperDao;

    @Override
    public ResponseEntity getAllShipper(String keyword){
        List<Shipper> shippers = shipperDao.findByNameContainsIgnoreCaseOrPhoneContainsIgnoreCase(keyword, keyword);
        return ResponseFactory.success(shippers, List.class);
    }

    @Override
    public ResponseEntity addShipper(ShipperRequest request){
        Shipper shipper = new Shipper();
        AppUtils.copyPropertiesIgnoreNull(request, shipper);
        return ResponseFactory.success(shipperDao.save(shipper), Shipper.class);
    }

    @Override
    public ResponseEntity updateShipper(ShipperRequest request, Integer shipperId){
        Shipper shipper = shipperDao.findById(shipperId).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        AppUtils.copyPropertiesIgnoreNull(request, shipper);
        return ResponseFactory.success(shipperDao.save(shipper), Shipper.class);
    }

    @Override
    public ResponseEntity getOneShipper(Integer shipperId){
        Shipper shipper = shipperDao.findById(shipperId).orElseThrow(
                () -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        return ResponseFactory.success(shipper, Shipper.class);
    }

    @Override
    public ResponseEntity deleteShipper(Integer shipperId){
        Shipper shipper = shipperDao.findById(shipperId).orElseThrow(
                () -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
        shipperDao.delete(shipper);
        return ResponseFactory.success();
    }
}
