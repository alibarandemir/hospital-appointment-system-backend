package com.clinic.hospital_appointment_backend.controller;

import com.clinic.hospital_appointment_backend.dto.CreateAppointmentRequest;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.service.AppointmentService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    
    private final AppointmentService appointmentService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public ResponseEntity<ResponseDto<String>> createAppointment(@RequestBody CreateAppointmentRequest request, Authentication authentication) {
        return ResponseEntity.ok(appointmentService.createAppointment(request, authentication));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/{appointmentId}/approve")
    public ResponseEntity<ResponseDto<String>> approveAppointment(@PathVariable Long appointmentId, Authentication authentication) {
        return ResponseEntity.ok(appointmentService.approveAppointment(appointmentId, authentication));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/{appointmentId}/reject")
    public ResponseEntity<ResponseDto<String>> rejectAppointment(@PathVariable Long appointmentId, Authentication authentication) {
        return ResponseEntity.ok(appointmentService.rejectAppointment(appointmentId, authentication));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/{appointmentId}/note")
    public ResponseEntity<ResponseDto<String>> addDoctorNote(@PathVariable Long appointmentId, @RequestBody String note, Authentication authentication) {
        return ResponseEntity.ok(appointmentService.addDoctorNote(appointmentId, note, authentication));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient")
    public ResponseEntity<ResponseDto<String>> getPatientAppointments(Authentication authentication) {
        return ResponseEntity.ok(appointmentService.getPatientAppointments(authentication));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor")
    public ResponseEntity<ResponseDto<String>> getDoctorAppointments(Authentication authentication) {
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(authentication));
    }
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/availability")
    public ResponseEntity<ResponseDto<AppointmentService.AvailabilityResponse>> getAvailability(@RequestParam Long doctorId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.getAvailability(doctorId, date));
    }

    public record AvailabilityResponse(
        LocalDate date,
        List<LocalTime> availableTimes
    ) {
    }
} 