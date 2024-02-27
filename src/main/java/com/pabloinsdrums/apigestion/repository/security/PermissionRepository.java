package com.pabloinsdrums.apigestion.repository.security;

import com.pabloinsdrums.apigestion.model.entity.security.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<GrantedPermission, Long> {
}
