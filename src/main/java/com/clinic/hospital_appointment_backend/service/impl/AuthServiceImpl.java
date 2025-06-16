package com.clinic.hospital_appointment_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.clinic.hospital_appointment_backend.dto.LoginRequest;
import com.clinic.hospital_appointment_backend.dto.RegisterRequest;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.entity.Doctor;
import com.clinic.hospital_appointment_backend.entity.Patient;
import com.clinic.hospital_appointment_backend.enums.Role;
import com.clinic.hospital_appointment_backend.repository.DoctorRepository;
import com.clinic.hospital_appointment_backend.repository.PatientRepository;
import com.clinic.hospital_appointment_backend.service.AuthService;
import com.clinic.hospital_appointment_backend.util.JwtUtil;

// Kimlik doğrulama ve kullanıcı yönetimi işlemlerini gerçekleştiren servis sınıfı
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Yeni kullanıcı kaydı oluşturma metodu
    @Override
    public ResponseDto<AuthResponseDto> register(RegisterRequest request) {
        // Email kontrolü - aynı email ile kayıt var mı?
        if (doctorRepository.findByEmail(request.getEmail()) != null || patientRepository.findByEmail(request.getEmail()) != null) {
            return new ResponseDto<AuthResponseDto>().FailResponse("Bu e-posta adresi ile zaten bir kullanıcı kayıtlı.");
        }

        String name = null;
        String surname = null;
        String role = null;

        // Kullanıcı tipine göre kayıt işlemi (Doktor/Hasta)
        if ("DOCTOR".equalsIgnoreCase(request.getRole().toString())) {
            Doctor doctor = new Doctor();
            doctor.setName(request.getName());
            doctor.setSurname(request.getSurname());
            doctor.setEmail(request.getEmail());
            doctor.setPassword(passwordEncoder.encode(request.getPassword()));
            doctor.setSpecialization(request.getSpecialization());
            doctor.setRole(Role.DOCTOR);
            doctorRepository.save(doctor);
            name = doctor.getName();
            surname = doctor.getSurname();
            role = "DOCTOR";
        } else {
            Patient patient = new Patient();
            patient.setName(request.getName());
            patient.setSurname(request.getSurname());
            patient.setEmail(request.getEmail());
            patient.setPassword(passwordEncoder.encode(request.getPassword()));
            patient.setRole(Role.PATIENT);
            patientRepository.save(patient);

            name = patient.getName();
            surname = patient.getSurname();
            role = "PATIENT";
        }

        // Kayıt sonrası otomatik giriş yapma
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication, role);
        AuthResponseDto authResponse = new AuthResponseDto(jwt, role, name, surname);
        return new ResponseDto<AuthResponseDto>().SuccessResponse("Kayıt başarılı", authResponse);
    }

    // Kullanıcı girişi yapma metodu
    @Override
    public ResponseDto<AuthResponseDto> login(LoginRequest request) {
        // Kullanıcı bilgilerini doğrulama
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Kullanıcı tipini belirleme (Doktor/Hasta)
        Doctor doctor = doctorRepository.findByEmail(request.getEmail());
        Patient patient = patientRepository.findByEmail(request.getEmail());
        String role, name, surname;
        if (doctor != null) {
            role = "DOCTOR";
            name = doctor.getName();
            surname = doctor.getSurname();
        } else if (patient != null) {
            role = "PATIENT";
            name = patient.getName();
            surname = patient.getSurname();
        } else {
            return new ResponseDto<AuthResponseDto>().FailResponse("Kullanıcı bulunamadı.");
        }

        // JWT token oluşturma ve yanıt dönme
        String jwt = jwtUtil.generateToken(authentication, role);
        AuthResponseDto authResponse = new AuthResponseDto(jwt, role, name, surname);
        return new ResponseDto<AuthResponseDto>().SuccessResponse("Giriş başarılı", authResponse);
    }

    // Kimlik doğrulama yanıtı için kullanılan record sınıfı
    public record AuthResponseDto(String token, String role, String name, String surname) {
    }
}
