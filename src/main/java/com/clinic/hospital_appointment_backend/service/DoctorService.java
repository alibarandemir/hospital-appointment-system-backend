package com.clinic.hospital_appointment_backend.service;

import com.clinic.hospital_appointment_backend.dto.ResponseDto;

public interface DoctorService {

    ResponseDto<String> getAllDoctors();

    ResponseDto<String> getDoctorById(Long id);

    ResponseDto<String> getDoctorAppointments();
    
} 
