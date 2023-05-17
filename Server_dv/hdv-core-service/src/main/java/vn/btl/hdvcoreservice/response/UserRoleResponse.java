package vn.btl.hdvcoreservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleResponse {

    private Long userId;
    private Integer roleId;
    private String roleName;
}
