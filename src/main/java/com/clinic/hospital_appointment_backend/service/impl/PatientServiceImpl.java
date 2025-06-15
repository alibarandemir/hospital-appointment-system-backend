package com.clinic.hospital_appointment_backend.service.impl;

import com.clinic.hospital_appointment_backend.dto.PatientResponseDto;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.entity.Patient;
import com.clinic.hospital_appointment_backend.repository.PatientRepository;
import com.clinic.hospital_appointment_backend.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor

public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public ResponseDto<PatientResponseDto> getPatient(Authentication authentication){
        String patientEmail=authentication.getName();
        Patient patient=patientRepository.findByEmail(patientEmail);
        PatientResponseDto dto= new PatientResponseDto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setSurname(patient.getSurname());
        dto.setEmail(patient.getEmail());
        return new ResponseDto<PatientResponseDto>().SuccessResponse("Hasta getirildi",dto);


    }

}
