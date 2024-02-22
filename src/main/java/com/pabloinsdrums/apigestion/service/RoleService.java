package com.pabloinsdrums.apigestion.service;

import com.pabloinsdrums.apigestion.model.entity.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
