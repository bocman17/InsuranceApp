package com.bocman.InsuranceApp.controller;

import com.bocman.InsuranceApp.exception.ResourceNotFoundException;
import com.bocman.InsuranceApp.model.Insurance;
import com.bocman.InsuranceApp.security.service.repository.InsuranceRepository;
import com.bocman.InsuranceApp.security.service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class InsuranceController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;

    @GetMapping("/persons/{personId}/insurances")
    public ResponseEntity<List<Insurance>> getAllInsurancesByPersonId(@PathVariable("personId") Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person with " + id + " id Not found");
        }

        List<Insurance> insurances = insuranceRepository.findByPersonId(id);
        return new ResponseEntity<>(insurances, HttpStatus.OK);
    }

    @GetMapping("/insurances/{id}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable("id") Long id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance with " + id + " id Not found"));

        return new ResponseEntity<>(insurance, HttpStatus.OK);
    }

    @PostMapping("/persons/{personId}/insurances")
    public ResponseEntity<Insurance> createInsurance(@PathVariable("personId") Long id, @RequestBody Insurance insurance){
        Insurance _insurance = personRepository.findById(id).map(person -> {
            insurance.setPerson(person);
            return insuranceRepository.save(insurance);
        }).orElseThrow(() -> new ResourceNotFoundException("Person with " + id + " id Not found"));

        return new ResponseEntity<>(_insurance, HttpStatus.CREATED);
    }

    @PutMapping("/insurances/{id}")
    public ResponseEntity<Insurance> updateInsurance(@PathVariable("id") Long id, @RequestBody Insurance insurance) {
        Insurance _insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance with " + id + " id Not Found"));

        _insurance.setInsuranceObject(insurance.getInsuranceObject());
        _insurance.setType(insurance.getType());
        _insurance.setPrice(insurance.getPrice());
        _insurance.setFromDate(insurance.getFromDate());
        _insurance.setToDate(insurance.getToDate());

        return new ResponseEntity<>(insuranceRepository.save(_insurance), HttpStatus.OK);
    }

    @DeleteMapping("/insurances/{id}")
    public ResponseEntity<HttpStatus> deleteInsuranceById(@PathVariable("id") Long id) {
        insuranceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/persons/{personId}/insurances")
    public ResponseEntity<List<Insurance>> deleteAllInsurancesByPersonId(@PathVariable("personId") Long id) {
        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person with " + id + " id Not Found");
        }

        insuranceRepository.deleteByPersonId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
