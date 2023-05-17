package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.Shipper;

import java.util.List;

@Repository
public interface ShipperDao extends JpaRepository<Shipper, Integer> {

    List<Shipper> findByNameContainsIgnoreCaseOrPhoneContainsIgnoreCase(String keyword1, String keyword2);
}
