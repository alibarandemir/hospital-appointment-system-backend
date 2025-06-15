package com.clinic.hospital_appointment_backend.service;

import com.clinic.hospital_appointment_backend.controller.DoctorController;
import com.clinic.hospital_appointment_backend.dto.DoctorResponseDto;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.entity.Doctor;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DoctorService {

    ResponseDto<List<DoctorResponseDto>> getAllDoctors();
    ResponseDto<DoctorController.DoctorDto> getDoctor(Authentication authentication);



    
} 
