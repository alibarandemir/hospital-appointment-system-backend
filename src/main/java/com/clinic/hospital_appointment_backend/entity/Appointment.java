package com.clinic.hospital_appointment_backend.entity;

import java.time.LocalDateTime;

import com.clinic.hospital_appointment_backend.enums.AppointmentStatus;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

// Randevu bilgilerini tutan entity sınıfı
@Data
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    // Randevuyu alan hasta (çoka-bir ilişki)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Randevunun atandığı doktor (çoka-bir ilişki)
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Randevu tarihi ve saati
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    // Randevu durumu (WAITING, COMPLETED, CANCELLED)
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.WAITING;

    // Doktorun randevu notları (maksimum 1000 karakter)
    @Column(name = "doctor_notes", length = 1000)
    private String doctorNotes;
}


