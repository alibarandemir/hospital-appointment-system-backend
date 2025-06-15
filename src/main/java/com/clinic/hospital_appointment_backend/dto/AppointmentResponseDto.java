package com.clinic.hospital_appointment_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private String doctorName;
    private String doctorSurname;
    private Long doctorId;
    private String patientName;
    private Long patientId;
    private String status;
    private String notes;
    private String specialization;
    private LocalDateTime appointmentDate;



}
