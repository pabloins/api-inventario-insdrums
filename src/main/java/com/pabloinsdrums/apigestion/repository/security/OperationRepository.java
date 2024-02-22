package com.pabloinsdrums.apigestion.repository.security;

import com.pabloinsdrums.apigestion.model.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("SELECT o FROM Operation o where o.permitAll = true")
    List<Operation> findByPublicAccess();
}
