package vn.btl.hdvauthenservice.data.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.btl.hdvauthenservice.data.sql.model.OtpEntity;

import java.util.Date;
import java.util.List;

public interface OtpRepository extends JpaRepository<OtpEntity, Integer>, JpaSpecificationExecutor<OtpEntity> {

    List<OtpEntity> findByOtpReferenceIdAndDeletedIsFalse(String referenceId);

    @Query(nativeQuery = true, value = "SELECT verified FROM otp WHERE otp_reference_id=:referenceId")
    boolean isVerified(String referenceId);


    @Query(nativeQuery = true, value = "SELECT COUNT(distinct otp_reference_id) FROM otp  WHERE  identity=:identity AND type =:type  AND created_date >=:time AND deleted=TRUE")
    long countGeneratedOtpByIdentityAndDeleted( String identity, String type,  Date time);




}