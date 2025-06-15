package com.clinic.hospital_appointment_backend.service.impl;

import com.clinic.hospital_appointment_backend.controller.DoctorController;
import com.clinic.hospital_appointment_backend.dto.DoctorResponseDto;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.entity.Doctor;
import com.clinic.hospital_appointment_backend.repository.DoctorRepository;
import com.clinic.hospital_appointment_backend.service.DoctorService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public ResponseDto<List<DoctorResponseDto>> getAllDoctors(){
        List<Doctor> doctors=doctorRepository.findAll();
        List<DoctorResponseDto> dtos= doctors.stream().map(doctor -> {
            DoctorResponseDto doctorResponseDto= new DoctorResponseDto();
            doctorResponseDto.setId(doctor.getId());
            doctorResponseDto.setDoctorName(doctor.getName());
            doctorResponseDto.setDoctorSurname(doctor.getSurname());
            doctorResponseDto.setSpecialization(doctor.getSpecialization());
            return doctorResponseDto;

        }).collect(Collectors.toList());
        return new  ResponseDto<List<DoctorResponseDto>>().SuccessResponse("Doktorlar getirildi",dtos);
    }
    @Override
    public ResponseDto<DoctorController.DoctorDto> getDoctor(Authentication authentication){
        String doctorEmail= authentication.getName();
        Doctor doctor= doctorRepository.findByEmail(doctorEmail);
        DoctorController.DoctorDto dto= new DoctorController.DoctorDto(doctor.getId(),doctor.getName(),doctor.getSurname(),doctor.getEmail(),doctor.getSpecialization());
        return  new ResponseDto< DoctorController.DoctorDto >().SuccessResponse("",dto);
    }



}
