
package com.example.hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project Name : Hospital Management System
 * Module       : Entity
 * Author       : Hemang
 * Date         : 09-11-2026
 *
 * Description:
 * Represents a patient record in the hospital management system.
 * Stores personal information and basic medical details.
 */

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    // Generate a unique primary key for every patient
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store the patient's name
    @Column(nullable = false, length = 100)
    private String name;

    // Store the patient's age
    @Column(nullable = false)
    private Integer age;

    // Store the patient's gender
    @Column(nullable = false, length = 20)
    private String gender;

    // Store the patient's disease or medical condition
    @Column(nullable = false, length = 255)
    private String disease;

    // Store the patient's residential address
    @Column(nullable = false, length = 255)
    private String address;

    // Store the patient's contact number
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
}

