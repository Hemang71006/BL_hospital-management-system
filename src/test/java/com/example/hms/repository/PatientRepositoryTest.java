
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

    // Test saving a patient
    @Test
    void shouldSavePatient() {

        Patient patient = createPatient();

        Patient savedPatient = patientRepository.save(patient);

        assertThat(savedPatient.getId()).isNotNull();
        assertThat(savedPatient.getName()).isEqualTo("Test Patient");
        assertThat(savedPatient.getAge()).isEqualTo(35);
    }

    // Test retrieving a patient using ID
    @Test
    void shouldFindPatientById() {

        Patient patient = patientRepository.saveAndFlush(createPatient());

        Long patientId = patient.getId();

        // Clear persistence context to retrieve from database
        entityManager.clear();

        Optional<Patient> result = patientRepository.findById(patientId);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Patient");
        assertThat(result.get().getDisease()).isEqualTo("Fever");
    }

    // Test updating patient details
    @Test
    void shouldUpdatePatient() {

        Patient patient = patientRepository.saveAndFlush(createPatient());

        Long patientId = patient.getId();

        entityManager.clear();

        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow();

        existingPatient.setName("Updated Patient");
        existingPatient.setAge(40);

        patientRepository.saveAndFlush(existingPatient);

        entityManager.clear();

        Patient updatedPatient = patientRepository.findById(patientId)
                .orElseThrow();

        assertThat(updatedPatient.getName()).isEqualTo("Updated Patient");
        assertThat(updatedPatient.getAge()).isEqualTo(40);
    }

    // Test deleting a patient
    @Test
    void shouldDeletePatient() {

        Patient patient = patientRepository.saveAndFlush(createPatient());

        Long patientId = patient.getId();

        patientRepository.deleteById(patientId);
        patientRepository.flush();

        Optional<Patient> result = patientRepository.findById(patientId);

        assertThat(result).isEmpty();
    }

    // Create sample patient data for testing
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
