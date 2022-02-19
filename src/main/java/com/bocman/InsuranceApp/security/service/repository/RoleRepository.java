package com.bocman.InsuranceApp.security.service.repository;

import com.bocman.InsuranceApp.model.ERole;
import com.bocman.InsuranceApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
