package com.clinic.hospital_appointment_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinic.hospital_appointment_backend.entity.Doctor;
import com.clinic.hospital_appointment_backend.entity.Patient;
import com.clinic.hospital_appointment_backend.repository.DoctorRepository;
import com.clinic.hospital_appointment_backend.repository.PatientRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(email);
        if (doctor != null) {
            return new User(doctor.getEmail(), doctor.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCTOR")));
        }

        Patient patient = patientRepository.findByEmail(email);
        if (patient != null) {
            return new User(patient.getEmail(), patient.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
} 