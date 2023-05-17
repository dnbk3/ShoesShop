package vn.btl.hdvauthenservice.data.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvauthenservice.data.sql.model.OtpMessageEntity;

public interface OtpMessageRepository extends JpaRepository<OtpMessageEntity, Long>, JpaSpecificationExecutor<OtpMessageEntity> {

}