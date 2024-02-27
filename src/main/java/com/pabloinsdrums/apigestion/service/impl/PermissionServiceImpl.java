package com.pabloinsdrums.apigestion.service.impl;

import com.pabloinsdrums.apigestion.dto.permission.SavePermission;
import com.pabloinsdrums.apigestion.dto.permission.ShowPermission;
import com.pabloinsdrums.apigestion.exception.ObjectNotFoundException;
import com.pabloinsdrums.apigestion.model.entity.security.GrantedPermission;
import com.pabloinsdrums.apigestion.model.entity.security.Operation;
import com.pabloinsdrums.apigestion.model.entity.security.Role;
import com.pabloinsdrums.apigestion.repository.security.OperationRepository;
import com.pabloinsdrums.apigestion.repository.security.PermissionRepository;
import com.pabloinsdrums.apigestion.repository.security.RoleRepository;
import com.pabloinsdrums.apigestion.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Page<ShowPermission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(this::mapEntityToShowDto);
    }

    private ShowPermission mapEntityToShowDto(GrantedPermission grantedPermission) {
        if(grantedPermission == null) return null;

        ShowPermission showPermissionDto = new ShowPermission();
        showPermissionDto.setId(grantedPermission.getId());
        showPermissionDto.setRole(grantedPermission.getRole().getName());
        showPermissionDto.setOperation(grantedPermission.getOperation().getName());
        showPermissionDto.setHttpMethod(grantedPermission.getOperation().getHttpMethod());
        showPermissionDto.setModule(grantedPermission.getOperation().getModule().getName());

        return showPermissionDto;
    }

    @Override
    public Optional<ShowPermission> findOneById(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .map(this::mapEntityToShowDto);
    }

    @Override
    public ShowPermission createOne(SavePermission savePermission) {
        GrantedPermission newPermission = new GrantedPermission();

        Operation operation = operationRepository.findByName(savePermission.getOperation())
                    .orElseThrow(() -> new ObjectNotFoundException("Operation not found. Operation: " + savePermission.getOperation()));
        newPermission.setOperation(operation);

        Role role = roleRepository.findByName(savePermission.getRole()).orElseThrow(
                () -> new ObjectNotFoundException("Role not found. Role: " + savePermission.getRole())
        );
        newPermission.setRole(role);

        permissionRepository.save(newPermission);
        return this.mapEntityToShowDto(newPermission);
    }

    @Override
    public ShowPermission deleteOneById(Long permissionId) {
        GrantedPermission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ObjectNotFoundException("Permission not found. Permission: "+ permissionId));

        permissionRepository.delete(permission);

        return this.mapEntityToShowDto(permission);
    }
}
