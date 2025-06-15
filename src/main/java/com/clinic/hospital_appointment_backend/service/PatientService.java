package com.clinic.hospital_appointment_backend.service;

import com.clinic.hospital_appointment_backend.dto.CreateAppointmentRequest;
import com.clinic.hospital_appointment_backend.dto.PatientResponseDto;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import org.springframework.security.core.Authentication;

public interface PatientService {


    ResponseDto<PatientResponseDto> getPatient(Authentication authentication);

    
}
