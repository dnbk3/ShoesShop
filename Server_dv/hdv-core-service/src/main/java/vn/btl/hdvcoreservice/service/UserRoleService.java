package vn.btl.hdvcoreservice.service;

import vn.btl.hdvcoreservice.repository.entity.UserRole;
import vn.btl.hdvcoreservice.response.UserRoleResponse;
import vn.btl.hdvcoreservice.utils.Operator;
import vn.btl.hdvcoreservice.utils.ResponseFactory;

import java.util.List;

public interface UserRoleService {

    public List<UserRoleResponse> getRoleUser(Long userId);

    public UserRole updateUserRole(Long userId, Integer roleId);

    public boolean checkRole(Long userId, Operator.ACTION action);

    public UserRole addRoleUser(Long userId, Integer roleId);
}
