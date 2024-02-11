package com.pabloinsdrums.apigestion.model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_ADMINISTRATOR(Arrays.asList(
        RolePermission.READ_ALL_PRODUCTS,
        RolePermission.READ_ONE_PRODUCT,
        RolePermission.CREATE_ONE_PRODUCT,
        RolePermission.UPDATE_ONE_PRODUCT,
        RolePermission.DISABLE_ONE_PRODUCT,

        RolePermission.READ_ALL_CATEGORIES,
        RolePermission.READ_ONE_CATEGORY,
        RolePermission.CREATE_ONE_CATEGORY,
        RolePermission.UPDATE_ONE_CATEGORY,
        RolePermission.DISABLE_ONE_CATEGORY,

        RolePermission.READ_MY_PROFILE
    )),
    ROLE_ASSISTANT_ADMINISTRADOR(Arrays.asList(
        RolePermission.READ_ALL_PRODUCTS,
        RolePermission.READ_ONE_PRODUCT,
        RolePermission.UPDATE_ONE_PRODUCT,

        RolePermission.READ_ALL_CATEGORIES,
        RolePermission.READ_ONE_CATEGORY,
        RolePermission.UPDATE_ONE_CATEGORY,

        RolePermission.READ_MY_PROFILE
    )),
    ROLE_COSTUMER(List.of(
        RolePermission.READ_MY_PROFILE
    ));

    private List<RolePermission> permissions;

    private void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
