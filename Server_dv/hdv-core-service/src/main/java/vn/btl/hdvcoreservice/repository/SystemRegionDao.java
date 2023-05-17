package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvcoreservice.repository.entity.SystemRegion;

import java.util.List;

public interface SystemRegionDao extends JpaRepository<SystemRegion, Long>, JpaSpecificationExecutor<SystemRegion> {

    List<SystemRegion> findByItemNameContainsIgnoreCaseAndParentItemId(String keyword, Long parentId);
}