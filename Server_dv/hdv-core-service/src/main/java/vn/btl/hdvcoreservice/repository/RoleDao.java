package vn.btl.hdvcoreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.btl.hdvcoreservice.repository.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

}