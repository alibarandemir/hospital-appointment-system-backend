package com.clinic.hospital_appointment_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto {
    private Long id;
    private String doctorName;
    private String doctorSurname;
    private String specialization;



}
