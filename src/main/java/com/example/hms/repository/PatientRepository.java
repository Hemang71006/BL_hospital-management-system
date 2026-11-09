
package com.example.hms.repository;

import com.example.hms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project Name : Hospital Management System
 * Module       : Repository
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Provides database operations for patient records.
 * Supports saving, retrieving, updating and deleting patients.
 */

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Inherit CRUD operations from JpaRepository
}

