package com.bocman.InsuranceApp.controller;

import com.bocman.InsuranceApp.exception.ResourceNotFoundException;
import com.bocman.InsuranceApp.model.Person;
import com.bocman.InsuranceApp.security.service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons(@RequestParam(required = false) String name) {
        List<Person> persons = new ArrayList<>();

        if (name == null) {
            personRepository.findAll().forEach(persons::add);
        } else {
            personRepository.findByNameContaining(name).forEach(persons::add);
        }

        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with " + id + " id Not found"));

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person _person = personRepository.save(person);
        return new ResponseEntity<>(_person, HttpStatus.CREATED);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
        Person _person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with " + id + " id Not found"));
        _person.setName(person.getName());
        _person.setEmail(person.getEmail());
        _person.setMobile(person.getMobile());
        _person.setCity(person.getCity());
        _person.setStreet(person.getStreet());
        _person.setZipcode(person.getZipcode());

        return new ResponseEntity<>(personRepository.save(_person), HttpStatus.OK);
    }

    @DeleteMapping("/persons/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deletePersonById(@PathVariable("id") Long id) {
        personRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/persons")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllPersons() {
        personRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
