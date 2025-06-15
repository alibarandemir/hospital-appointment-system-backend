package com.clinic.hospital_appointment_backend.controller;

import com.clinic.hospital_appointment_backend.dto.PatientResponseDto;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
public class PatientController {


    @Autowired
    private PatientService patientService;
    @GetMapping
    public ResponseEntity<ResponseDto<PatientResponseDto>> getPatient(Authentication authentication){
        return ResponseEntity.ok(patientService.getPatient(authentication));
    }

}
