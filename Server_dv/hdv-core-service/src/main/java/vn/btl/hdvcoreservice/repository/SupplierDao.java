package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvcoreservice.repository.entity.Supplier;

import java.util.List;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer>{

    List<Supplier> findByNameContainsIgnoreCase(String keyword);

    Supplier findOneBySupplierId(Integer id);
}
