package com.clinic.hospital_appointment_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String email;



}
