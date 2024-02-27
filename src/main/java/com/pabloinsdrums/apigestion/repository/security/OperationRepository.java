package com.pabloinsdrums.apigestion.repository.security;

import com.pabloinsdrums.apigestion.model.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    Optional<Operation> findByName(String operationId);
    @Query("SELECT o FROM Operation o where o.permitAll = true")
    List<Operation> findByPubliccAcces();
}
