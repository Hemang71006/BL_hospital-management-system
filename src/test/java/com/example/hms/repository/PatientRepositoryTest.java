
package com.example.hms.repository;

import com.example.hms.entity.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Project Name : Hospital Management System
 * Module       : Patient Repository Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests database operations provided by PatientRepository.
 * Verifies saving, retrieving, updating and deleting
 * patient records.
 */

@SpringBootTest
@Transactional
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Test whether a patient can be saved successfully
    @Test
    void shouldSavePatient() {

        // Create a sample patient
        Patient patient = createPatient();

        // Save the patient in the database
        Patient savedPatient = patientRepository.save(patient);

        // Verify that the patient has been saved
        assertThat(savedPatient.getId()).isNotNull();
        assertThat(savedPatient.getName()).isEqualTo("Test Patient");
        assertThat(savedPatient.getAge()).isEqualTo(35);
    }

    // Test whether a patient can be retrieved using patient ID
    @Test
    void shouldFindPatientById() {

        // Create and save a patient
        Patient patient = patientRepository.saveAndFlush(createPatient());

        Long patientId = patient.getId();

        // Clear the persistence context to fetch from the database
        entityManager.clear();

        // Retrieve the patient using ID
        Optional<Patient> result = patientRepository.findById(patientId);

        // Verify the retrieved patient details
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Patient");
        assertThat(result.get().getDisease()).isEqualTo("Fever");
    }

    // Test whether patient details can be updated
    @Test
    void shouldUpdatePatient() {

        // Create and save a patient
        Patient patient = patientRepository.saveAndFlush(createPatient());

        Long patientId = patient.getId();

        // Clear the persistence context
        entityManager.clear();

        // Retrieve the existing patient
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow();

        // Update the patient's details
        existingPatient.setName("Updated Patient");
        existingPatient.setAge(40);

        // Save the updated patient
        patientRepository.saveAndFlush(existingPatient);

        // Clear the persistence context again
        entityManager.clear();

        // Retrieve the updated patient
        Patient updatedPatient = patientRepository.findById(patientId)
                .orElseThrow();

        // Verify the updated details
        assertThat(updatedPatient.getName()).isEqualTo("Updated Patient");
        assertThat(updatedPatient.getAge()).isEqualTo(40);
    }

    // Test whether a patient can be deleted using patient ID
    @Test
    void shouldDeletePatient() {

        // Create and save a patient
        Patient patient = patientRepository.saveAndFlush(createPatient());

        Long patientId = patient.getId();

        // Delete the patient
        patientRepository.deleteById(patientId);
        patientRepository.flush();

        // Verify that the patient no longer exists
        Optional<Patient> result = patientRepository.findById(patientId);

        assertThat(result).isEmpty();
    }

    // Create a reusable sample patient for testing
    private Patient createPatient() {

        return Patient.builder()
                .name("Test Patient")
                .age(35)
                .gender("Male")
                .disease("Fever")
                .address("Chennai")
                .phoneNumber("9876543210")
                .build();
    }
}

