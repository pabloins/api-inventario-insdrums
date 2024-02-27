package com.pabloinsdrums.apigestion.service;

import com.pabloinsdrums.apigestion.dto.permission.SavePermission;
import com.pabloinsdrums.apigestion.dto.permission.ShowPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PermissionService {
    Page<ShowPermission> findAll(Pageable pageable);

    Optional<ShowPermission> findOneById(Long permissionId);

    ShowPermission createOne(SavePermission savePermission);

    ShowPermission deleteOneById(Long permissionId);
}
