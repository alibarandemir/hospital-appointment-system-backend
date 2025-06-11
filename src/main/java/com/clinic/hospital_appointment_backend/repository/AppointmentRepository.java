package com.clinic.hospital_appointment_backend.repository;

import com.clinic.hospital_appointment_backend.entity.Appointment;
import com.clinic.hospital_appointment_backend.entity.Doctor;
import com.clinic.hospital_appointment_backend.entity.Patient;
import com.clinic.hospital_appointment_backend.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDateTime appointmentDate);
    List<Appointment> findByDoctorAndAppointmentDateAndStatus(Doctor doctor, LocalDateTime appointmentDate, AppointmentStatus status);
    List<Appointment> findByPatientAndStatus(Patient patient, AppointmentStatus status);
} 