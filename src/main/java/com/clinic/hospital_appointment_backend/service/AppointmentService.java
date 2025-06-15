package com.clinic.hospital_appointment_backend.service;

import com.clinic.hospital_appointment_backend.dto.AppointmentResponseDto;
import com.clinic.hospital_appointment_backend.dto.CreateAppointmentRequest;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;

import java.time.LocalDate;
import java.util.List;

import com.clinic.hospital_appointment_backend.entity.Appointment;
import org.springframework.security.core.Authentication;

public interface AppointmentService {
    ResponseDto<String> createAppointment(CreateAppointmentRequest request, Authentication authentication);
    ResponseDto<String> approveAppointment(Long appointmentId, Authentication authentication);
    ResponseDto<String> rejectAppointment(Long appointmentId, Authentication authentication);
    ResponseDto<String> addDoctorNote(Long appointmentId, String note, Authentication authentication);
    ResponseDto<List<AppointmentResponseDto>> getPatientAppointments(Authentication authentication);
    ResponseDto<List<AppointmentResponseDto>> getDoctorAppointments(Authentication authentication);
    
    ResponseDto<AvailabilityResponse> getAvailability(Long doctorId, LocalDate date);

    class AvailabilityResponse {
        public List<java.time.LocalTime> allSlots;
        public List<java.time.LocalTime> unavailableSlots;
        public AvailabilityResponse(List<java.time.LocalTime> allSlots, List<java.time.LocalTime> unavailableSlots) {
            this.allSlots = allSlots;
            this.unavailableSlots = unavailableSlots;
        }
    }
} 