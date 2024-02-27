package com.pabloinsdrums.apigestion.controller;

import com.pabloinsdrums.apigestion.dto.permission.SavePermission;
import com.pabloinsdrums.apigestion.dto.permission.ShowPermission;
import com.pabloinsdrums.apigestion.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<ShowPermission>> findAll(Pageable pageable) {
        Page<ShowPermission> permissionPage = permissionService.findAll(pageable);

        if(permissionPage.hasContent()) {
            return ResponseEntity.ok(permissionPage);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermission> findOneById(@PathVariable Long permissionId) {
        Optional<ShowPermission> permission = permissionService.findOneById(permissionId);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<ShowPermission> createOne(@RequestBody @Valid SavePermission savePermission) {
        ShowPermission permission = permissionService.createOne(savePermission);
        return ResponseEntity.status(HttpStatus.CREATED).body(permission);
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ShowPermission> deleteOneById(@PathVariable Long permissionId) {
        ShowPermission permission = permissionService.deleteOneById(permissionId);
        return ResponseEntity.ok(permission);
    }
}
