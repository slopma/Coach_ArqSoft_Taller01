package com.caba.coachsystem.repository;

import com.caba.coachsystem.entidad.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    Coach findByEmail(String email);
}