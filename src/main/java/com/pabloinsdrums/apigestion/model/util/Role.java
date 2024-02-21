package com.pabloinsdrums.apigestion.model.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    ADMINISTRATOR(Arrays.asList(
        RolePermission.READ_ALL_PRODUCTS,
        RolePermission.READ_ONE_PRODUCT,
        RolePermission.CREATE_ONE_PRODUCT,
        RolePermission.UPDATE_ONE_PRODUCT,
        RolePermission.DISABLE_ONE_PRODUCT,
        RolePermission.DELETE_ONE_PRODUCT,

        RolePermission.READ_ALL_CATEGORIES,
        RolePermission.READ_ONE_CATEGORY,
        RolePermission.CREATE_ONE_CATEGORY,
        RolePermission.UPDATE_ONE_CATEGORY,
        RolePermission.DISABLE_ONE_CATEGORY,
        RolePermission.DELETE_ONE_CATEGORY,

        RolePermission.READ_MY_PROFILE
    )),
    ASSISTANT_ADMINISTRATOR(Arrays.asList(
        RolePermission.READ_ALL_PRODUCTS,
        RolePermission.READ_ONE_PRODUCT,
        RolePermission.UPDATE_ONE_PRODUCT,

        RolePermission.READ_ALL_CATEGORIES,
        RolePermission.READ_ONE_CATEGORY,
        RolePermission.UPDATE_ONE_CATEGORY,

        RolePermission.READ_MY_PROFILE
    )),
    CUSTOMER(List.of(
        RolePermission.READ_MY_PROFILE
    ));

    private List<RolePermission> permissions;

    private void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
