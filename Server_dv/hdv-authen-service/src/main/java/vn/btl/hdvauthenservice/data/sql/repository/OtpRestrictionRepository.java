package vn.btl.hdvauthenservice.data.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvauthenservice.data.sql.model.OtpRestrictionEntity;

import java.util.Optional;

public interface OtpRestrictionRepository extends JpaRepository<OtpRestrictionEntity, Integer>, JpaSpecificationExecutor<OtpRestrictionEntity> {

    Optional<OtpRestrictionEntity> findByIdentity(String identity);
    Optional<OtpRestrictionEntity> findByIdentityAndTypeAndDeletedIsFalse(String identity, String type);

}