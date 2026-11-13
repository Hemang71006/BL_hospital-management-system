
package com.example.hms.service;

import com.example.hms.dto.PatientDto;
import com.example.hms.entity.Patient;
import com.example.hms.repository.PatientRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Project Name : Hospital Management System
 * Module       : Patient Service Testing
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Tests the business logic provided by PatientService.
 * Verifies adding, retrieving, updating and deleting
 * patient records using mocked repository operations.
 */

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    // Create a mock repository instead of connecting to MySQL
    @Mock
    private PatientRepository patientRepository;

    // Inject the mock repository into PatientService
    @InjectMocks
    private PatientService patientService;

    // Test adding a new patient
    @Test
    void shouldAddPatient() {

        PatientDto patientDto = createPatientDto();

        Patient savedPatient = createPatient(1L);

        when(patientRepository.save(any(Patient.class)))
                .thenReturn(savedPatient);

        PatientDto result = patientService.addPatient(patientDto);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Patient");
        assertThat(result.age()).isEqualTo(35);

        verify(patientRepository, times(1))
                .save(any(Patient.class));
    }

    // Test retrieving all patients
    @Test
    void shouldGetAllPatients() {

        Patient firstPatient = createPatient(1L);
        Patient secondPatient = Patient.builder()
                .id(2L)
                .name("Second Patient")
                .age(40)
                .gender("Female")
                .disease("Cold")
                .address("Bangalore")
                .phoneNumber("9876543211")
                .build();

        when(patientRepository.findAll())
                .thenReturn(List.of(firstPatient, secondPatient));

        List<PatientDto> result = patientService.getAllPatients();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Test Patient");
        assertThat(result.get(1).name()).isEqualTo("Second Patient");

        verify(patientRepository, times(1)).findAll();
    }

    // Test retrieving a patient using ID
    @Test
    void shouldGetPatientById() {

        Patient patient = createPatient(1L);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        PatientDto result = patientService.getPatientById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Patient");
        assertThat(result.disease()).isEqualTo("Fever");

        verify(patientRepository, times(1)).findById(1L);
    }

    // Test updating an existing patient
    @Test
    void shouldUpdatePatient() {

        Patient existingPatient = createPatient(1L);

        PatientDto updatedDto = new PatientDto(
                1L,
                "Updated Patient",
                40,
                "Female",
                "Cold",
                "Mumbai",
                "9876543212"
        );

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(existingPatient));

        when(patientRepository.save(any(Patient.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PatientDto result =
                patientService.updatePatient(1L, updatedDto);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Updated Patient");
        assertThat(result.age()).isEqualTo(40);
        assertThat(result.gender()).isEqualTo("Female");
        assertThat(result.disease()).isEqualTo("Cold");
        assertThat(result.address()).isEqualTo("Mumbai");
        assertThat(result.phoneNumber()).isEqualTo("9876543212");

        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    // Test deleting an existing patient
    @Test
    void shouldDeletePatient() {

        Patient patient = createPatient(1L);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).delete(patient);
    }

    // Test retrieving a patient that does not exist
    @Test
    void shouldThrowExceptionWhenPatientDoesNotExist() {

        when(patientRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> patientService.getPatientById(99L)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Patient not found with ID: 99");
    }

    // Test updating a patient that does not exist
    @Test
    void shouldThrowExceptionWhenUpdatingMissingPatient() {

        when(patientRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> patientService.updatePatient(99L, createPatientDto())
        );

        verify(patientRepository, never()).save(any(Patient.class));
    }

    // Test deleting a patient that does not exist
    @Test
    void shouldThrowExceptionWhenDeletingMissingPatient() {

        when(patientRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> patientService.deletePatient(99L)
        );

        verify(patientRepository, never()).delete(any(Patient.class));
    }

    // Create sample patient DTO for testing
    private PatientDto createPatientDto() {

        return new PatientDto(
                null,
                "Test Patient",
                35,
                "Male",
                "Fever",
                "Chennai",
                "9876543210"
        );
    }

    // Create sample patient entity for testing
    private Patient createPatient(Long id) {

        return Patient.builder()
                .id(id)
                .name("Test Patient")
                .age(35)
                .gender("Male")
                .disease("Fever")
                .address("Chennai")
                .phoneNumber("9876543210")
                .build();
    }
}

