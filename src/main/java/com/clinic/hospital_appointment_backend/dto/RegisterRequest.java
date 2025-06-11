package com.clinic.hospital_appointment_backend.dto;

import javax.management.relation.Role;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role; // "DOCTOR" or "PATIENT"
    private String specialization; // Only for doctors
} 