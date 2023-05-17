package vn.btl.hdvauthenservice.data.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.btl.hdvauthenservice.data.sql.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByCustomerId(Long customerId);

    UserEntity findOneByPhoneAndActiveIsTrue(String phone);
    UserEntity findOneByEmailAndActiveIsTrue(String email);

    UserEntity findOneByPhoneAndVerifiedIsTrue(String phone);

    UserEntity findOneByEmailAndVerifiedIsTrue(String email);

    UserEntity findOneByEmail(String email);
}
