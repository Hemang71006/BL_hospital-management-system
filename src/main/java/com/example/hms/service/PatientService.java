
package com.example.hms.service;

import com.example.hms.dto.PatientDto;
import com.example.hms.entity.Patient;
import com.example.hms.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project Name : Hospital Management System
 * Module       : Patient Management - Service
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Handles business logic for patient management.
 * Provides operations to add, retrieve, update and delete patients.
 */

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Inject the repository to perform patient database operations
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Add a new patient to the database
    public PatientDto addPatient(PatientDto patientDto) {

        Patient patient = mapToEntity(patientDto);

        Patient savedPatient = patientRepository.save(patient);

        return mapToDto(savedPatient);
    }

    // Retrieve all patient records
    public List<PatientDto> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Retrieve a patient using the patient ID
    public PatientDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));

        return mapToDto(patient);
    }

    // Update an existing patient's information
    public PatientDto updatePatient(Long id, PatientDto patientDto) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));

        patient.setName(patientDto.name());
        patient.setAge(patientDto.age());
        patient.setGender(patientDto.gender());
        patient.setDisease(patientDto.disease());
        patient.setAddress(patientDto.address());
        patient.setPhoneNumber(patientDto.phoneNumber());

        Patient updatedPatient = patientRepository.save(patient);

        return mapToDto(updatedPatient);
    }

    // Delete a patient using the patient ID
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));

        patientRepository.delete(patient);
    }

    // Convert patient DTO into a database entity
    private Patient mapToEntity(PatientDto dto) {

        return Patient.builder()
                .name(dto.name())
                .age(dto.age())
                .gender(dto.gender())
                .disease(dto.disease())
                .address(dto.address())
                .phoneNumber(dto.phoneNumber())
                .build();
    }

    // Convert database entity into a response DTO
    private PatientDto mapToDto(Patient patient) {

        return new PatientDto(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getDisease(),
                patient.getAddress(),
                patient.getPhoneNumber()
        );
    }
}

