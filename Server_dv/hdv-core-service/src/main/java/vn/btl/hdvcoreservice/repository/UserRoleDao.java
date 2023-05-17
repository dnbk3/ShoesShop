package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvcoreservice.repository.entity.UserRole;

import java.util.List;

public interface UserRoleDao extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findAllByUserId(Long userId);

    UserRole findByUserIdAndRoleId(Long userId, Integer role);
}