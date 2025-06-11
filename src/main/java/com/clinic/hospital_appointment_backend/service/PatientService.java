package com.clinic.hospital_appointment_backend.service;

import com.clinic.hospital_appointment_backend.dto.CreateAppointmentRequest;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;

public interface PatientService {

    ResponseDto<String> createAppointment(CreateAppointmentRequest request);

    ResponseDto<String> getAppointments();
    
}
