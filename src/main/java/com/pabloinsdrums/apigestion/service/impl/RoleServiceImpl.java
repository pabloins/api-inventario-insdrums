package com.pabloinsdrums.apigestion.service.impl;

import com.pabloinsdrums.apigestion.model.entity.security.Role;
import com.pabloinsdrums.apigestion.repository.security.RoleRepository;
import com.pabloinsdrums.apigestion.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Value("${security.default.role}")
    private String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
}
