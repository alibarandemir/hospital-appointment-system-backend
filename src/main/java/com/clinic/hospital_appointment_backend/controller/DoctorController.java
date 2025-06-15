package com.clinic.hospital_appointment_backend.controller;

import com.clinic.hospital_appointment_backend.dto.DoctorResponseDto;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.entity.Doctor;
import com.clinic.hospital_appointment_backend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    @GetMapping
    public ResponseEntity<ResponseDto<List<DoctorResponseDto>>> getAllDoctors(Authentication authentication){
        return ResponseEntity.ok(doctorService.getAllDoctors());


    }
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto<DoctorDto>> getDoctor(Authentication authentication){
        return ResponseEntity.ok(doctorService.getDoctor(authentication));
    }





    public record DoctorDto(Long id,String name,String surname,String email,String specialization){};




}
