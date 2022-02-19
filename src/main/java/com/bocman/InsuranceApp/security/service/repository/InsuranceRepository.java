package com.bocman.InsuranceApp.security.service.repository;

import com.bocman.InsuranceApp.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    List<Insurance> findByPersonId (Long personId);

    @Transactional
    void deleteByPersonId(Long personId);
}
