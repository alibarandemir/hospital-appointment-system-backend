package com.clinic.hospital_appointment_backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    
    private Long doctorId;
    private LocalDateTime appointmentDate;
    
}
