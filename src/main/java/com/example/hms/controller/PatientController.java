
package com.example.hms.controller;

import com.example.hms.dto.PatientDto;
import com.example.hms.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project Name : Hospital Management System
 * Module       : Patient Management - Controller
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Exposes REST API endpoints for patient management.
 * Handles HTTP requests for patient CRUD operations.
 */

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    // Inject the service to handle patient business logic
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Add a new patient
    @PostMapping
    public ResponseEntity<PatientDto> addPatient(
            @Valid @RequestBody PatientDto patientDto) {

        PatientDto savedPatient = patientService.addPatient(patientDto);

        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    // Retrieve all patients
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {

        return ResponseEntity.ok(patientService.getAllPatients());
    }

    // Retrieve a patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Update an existing patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDto patientDto) {

        return ResponseEntity.ok(patientService.updatePatient(id, patientDto));
    }

    // Delete a patient by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}

