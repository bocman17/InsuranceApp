package com.bocman.InsuranceApp.security.service.repository;

import com.bocman.InsuranceApp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByNameContaining(String name);
}
