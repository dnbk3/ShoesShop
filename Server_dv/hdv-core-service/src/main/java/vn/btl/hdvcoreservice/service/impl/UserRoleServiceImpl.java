package vn.btl.hdvcoreservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.btl.hdvcoreservice.exception.CoreErrorCode;
import vn.btl.hdvcoreservice.exception.CoreException;
import vn.btl.hdvcoreservice.repository.RoleDao;
import vn.btl.hdvcoreservice.repository.UserRoleDao;
import vn.btl.hdvcoreservice.repository.entity.Role;
import vn.btl.hdvcoreservice.repository.entity.UserRole;
import vn.btl.hdvcoreservice.response.UserRoleResponse;
import vn.btl.hdvcoreservice.service.UserRoleService;
import vn.btl.hdvcoreservice.utils.AppUtils;
import vn.btl.hdvcoreservice.utils.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserRoleServiceImpl implements UserRoleService {

    private static int ROLE_ADMIN = 1;
    private static int ROLE_EMPLOYEE = 2;

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<UserRoleResponse> getRoleUser(Long userId) {
        List<UserRoleResponse> responses = userRoleDao.findAllByUserId(userId).stream().map(o -> {
            UserRoleResponse userRoleResponse = new UserRoleResponse();
            AppUtils.copyPropertiesIgnoreNull(o, userRoleResponse);
            Role role = roleDao.findById(o.getRoleId()).orElseThrow(() -> new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS));
            userRoleResponse.setRoleName(role.getName());
            return userRoleResponse;
        }).collect(Collectors.toList());
        return responses;
    }

    @Override
    public UserRole updateUserRole(Long userId, Integer roleId) {
        UserRole role = userRoleDao.findByUserIdAndRoleId(userId, roleId);
        if (role == null)
            throw new CoreException(CoreErrorCode.ENTITY_NOT_EXISTS);
        userRoleDao.delete(role);
        return userRoleDao.save(new UserRole(userId, roleId));
    }

    @Override
    public boolean checkRole(Long userId, Operator.ACTION action) {
        List<UserRole> userRoles = userRoleDao.findAllByUserId(userId);
        log.info("check role user {}" , userRoles);
        for (UserRole userRole : userRoles){
            boolean check = isValid(userRole.getRoleId(), Operator.builder().action(action).build());
            log.info("check role {}", check);
            if(check == true)
                return true;
        }
        return false;
    }

    @Override
    public UserRole addRoleUser(Long userId, Integer roleId) {
        log.info("add role user {} {}", userId, roleId);
        UserRole userRole = new UserRole(userId, roleId);
        return userRoleDao.save(userRole);
    }


    @Data
    @AllArgsConstructor
    public class MatrixKey implements Comparable<MatrixKey> {

        private long roleId;
        private Operator.ACTION action;

        @Override
        public int compareTo(MatrixKey o) {
            if (roleId > o.roleId)
                return 1;
            if (roleId < o.roleId)
                return -1;
            else if (action != o.action)
                return 1;
            return 0;
        }
    }

    public boolean isValid(long roleId, Operator operator) {
        MatrixKey key = new MatrixKey(roleId, operator.getAction());
        if (permissionMap.containsKey(key))
            return permissionMap.get(key);
        return false;
    }

    private static HashMap<MatrixKey, Boolean> permissionMap = new HashMap<>();

    {
        // ADMIN
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_PRODUCT), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_PRODUCT), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_PRODUCT), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_PRODUCT), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_SHIPPER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_SHIPPER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_SHIPPER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_SHIPPER), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_SUPPLIER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_SUPPLIER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_SUPPLIER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_SUPPLIER), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_CATEGORY), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_CATEGORY), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_CATEGORY), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_CATEGORY), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_LIST_CUSTOMER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_CUSTOMER_DETAIL), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_CUSTOMER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_CUSTOMER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_CUSTOMER), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_ODER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_ORDER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_ORDER_STATUS), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_ORDER), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_ORDER), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_USER_ROLE), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_USER_ROLE), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.DELETE_USER_ROLE), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.UPDATE_USER_ROLE), true);

        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.VIEW_REPORT), true);
        permissionMap.put(new MatrixKey(ROLE_ADMIN, Operator.ACTION.CREATE_BILLING), true);

        // EMPLOYEE

        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.VIEW_PRODUCT), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.UPDATE_PRODUCT), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.CREATE_PRODUCT), true);

        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.VIEW_CATEGORY), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.UPDATE_CATEGORY), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.CREATE_CATEGORY), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.DELETE_CATEGORY), true);


        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.VIEW_ORDER), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.UPDATE_ORDER_STATUS), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.UPDATE_ORDER), true);

        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.VIEW_SHIPPER), true);
        permissionMap.put(new MatrixKey(ROLE_EMPLOYEE, Operator.ACTION.VIEW_SUPPLIER), true);

    }
}
