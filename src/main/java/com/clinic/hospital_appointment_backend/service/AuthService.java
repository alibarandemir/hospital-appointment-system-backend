package com.clinic.hospital_appointment_backend.service;

import com.clinic.hospital_appointment_backend.dto.LoginRequest;
import com.clinic.hospital_appointment_backend.dto.RegisterRequest;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;


public interface AuthService {
    ResponseDto register(RegisterRequest request);
    ResponseDto login(LoginRequest request);
}
