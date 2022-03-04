package com.encora.eci.repositories;

import com.encora.eci.entities.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Long> {
    Optional<EmployeePosition> findByPositionName(String positionName);
}
