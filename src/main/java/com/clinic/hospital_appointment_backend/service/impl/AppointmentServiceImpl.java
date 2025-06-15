package com.clinic.hospital_appointment_backend.service.impl;

import com.clinic.hospital_appointment_backend.dto.AppointmentResponseDto;
import com.clinic.hospital_appointment_backend.dto.CreateAppointmentRequest;
import com.clinic.hospital_appointment_backend.dto.ResponseDto;
import com.clinic.hospital_appointment_backend.entity.Appointment;
import com.clinic.hospital_appointment_backend.entity.Doctor;
import com.clinic.hospital_appointment_backend.entity.Patient;
import com.clinic.hospital_appointment_backend.enums.AppointmentStatus;
import com.clinic.hospital_appointment_backend.repository.AppointmentRepository;
import com.clinic.hospital_appointment_backend.repository.DoctorRepository;
import com.clinic.hospital_appointment_backend.repository.PatientRepository;
import com.clinic.hospital_appointment_backend.service.AppointmentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public ResponseDto<String> createAppointment(CreateAppointmentRequest request, Authentication authentication) {
        // Hasta email ile bulunur
        Patient patient = patientRepository.findByEmail(authentication.getName());
        if (patient == null) {
            return new ResponseDto<String>().FailResponse("Hasta bulunamadı.");
        }
        // Doktor bulunur
        Optional<Doctor> doctorOpt = doctorRepository.findById(request.getDoctorId());
        if (doctorOpt.isEmpty()) {
            return new ResponseDto<String>().FailResponse("Doktor bulunamadı.");
        }
        Doctor doctor = doctorOpt.get();
        // Çakışma kontrolü
        List<Appointment> existing = appointmentRepository.findByDoctorAndAppointmentDateAndStatus(
                doctor, request.getAppointmentDate(), AppointmentStatus.CONFIRMED);
        if (!existing.isEmpty()) {
            return new ResponseDto<String>().FailResponse("Bu saatte doktorun başka bir randevusu var.");
        }
        // Randevu oluştur
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStatus(AppointmentStatus.WAITING);
        appointmentRepository.save(appointment);
        return new ResponseDto<String>().SuccessResponse("Randevu başarıyla oluşturuldu.", null);
    }

    @Override
    public ResponseDto<String> approveAppointment(Long appointmentId, Authentication authentication) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            return new ResponseDto<String>().FailResponse("Randevu bulunamadı.");
        }
        Appointment appointment = appointmentOpt.get();
        Doctor doctor = doctorRepository.findByEmail(authentication.getName());
        if (doctor == null || !appointment.getDoctor().getId().equals(doctor.getId())) {
            return new ResponseDto<String>().FailResponse("Bu randevuyu onaylama yetkiniz yok.");
        }
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
        return new ResponseDto<String>().SuccessResponse("Randevu onaylandı.", null);
    }

    @Override
    public ResponseDto<String> rejectAppointment(Long appointmentId, Authentication authentication) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            return new ResponseDto<String>().FailResponse("Randevu bulunamadı.");
        }
        Appointment appointment = appointmentOpt.get();
        Doctor doctor = doctorRepository.findByEmail(authentication.getName());
        if (doctor == null || !appointment.getDoctor().getId().equals(doctor.getId())) {
            return new ResponseDto<String>().FailResponse("Bu randevuyu reddetme yetkiniz yok.");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
        return new ResponseDto<String>().SuccessResponse("Randevu reddedildi.", null);
    }

    @Override
    public ResponseDto<String> addDoctorNote(Long appointmentId, String note, Authentication authentication) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            return new ResponseDto<String>().FailResponse("Randevu bulunamadı.");
        }
        Appointment appointment = appointmentOpt.get();
        Doctor doctor = doctorRepository.findByEmail(authentication.getName());
        if (doctor == null || !appointment.getDoctor().getId().equals(doctor.getId())) {
            return new ResponseDto<String>().FailResponse("Bu randevuya not ekleme yetkiniz yok.");
        }
        appointment.setDoctorNotes(note);
        appointmentRepository.save(appointment);
        return new ResponseDto<String>().SuccessResponse("Not eklendi.", null);
    }

    @Override
    public ResponseDto<List<AppointmentResponseDto>> getPatientAppointments(Authentication authentication) {
        Patient patient = patientRepository.findByEmail(authentication.getName());
        if (patient == null) {
            return new ResponseDto<List<AppointmentResponseDto>>().FailResponse("Hasta bulunamadı.");
        }
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        List<AppointmentResponseDto> dtos= appointments.stream().map(app->{
            AppointmentResponseDto appointmentResponseDto= new AppointmentResponseDto();
            appointmentResponseDto.setId(app.getId());
            appointmentResponseDto.setDoctorName(app.getDoctor().getName());
            appointmentResponseDto.setDoctorSurname(app.getDoctor().getSurname());
            appointmentResponseDto.setDoctorId(app.getDoctor().getId());
            appointmentResponseDto.setPatientName(app.getPatient().getName());
            appointmentResponseDto.setPatientId(app.getPatient().getId());
            appointmentResponseDto.setStatus(app.getStatus().toString());
            appointmentResponseDto.setNotes(app.getDoctorNotes());
            appointmentResponseDto.setSpecialization(app.getDoctor().getSpecialization());
            appointmentResponseDto.setAppointmentDate(app.getAppointmentDate());
            return appointmentResponseDto;
        }).collect(Collectors.toList());
        return new ResponseDto<List<AppointmentResponseDto>>().SuccessResponse("Randevular getirildi.", dtos);
    }

    @Override
    public ResponseDto<List<AppointmentResponseDto>> getDoctorAppointments(Authentication authentication) {
        Doctor doctor = doctorRepository.findByEmail(authentication.getName());
        if (doctor == null) {
            return new ResponseDto<List<AppointmentResponseDto>>().FailResponse("Doktor bulunamadı.");
        }
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

        List<AppointmentResponseDto> dtos= appointments.stream().map(app->{
            AppointmentResponseDto appointmentResponseDto= new AppointmentResponseDto();
            appointmentResponseDto.setId(app.getId());
            appointmentResponseDto.setDoctorName(app.getDoctor().getName());
            appointmentResponseDto.setDoctorSurname(app.getDoctor().getSurname());
            appointmentResponseDto.setDoctorId(app.getDoctor().getId());
            appointmentResponseDto.setPatientName(app.getPatient().getName());
            appointmentResponseDto.setPatientId(app.getPatient().getId());
            appointmentResponseDto.setStatus(app.getStatus().toString());
            appointmentResponseDto.setNotes(app.getDoctorNotes());
            appointmentResponseDto.setSpecialization(app.getDoctor().getSpecialization());
            appointmentResponseDto.setAppointmentDate(app.getAppointmentDate());
            return appointmentResponseDto;
        }).collect(Collectors.toList());
        return new ResponseDto<List<AppointmentResponseDto>>().SuccessResponse("Randevular getirildi.", dtos);
    }

    

    @Override
    public ResponseDto<AvailabilityResponse> getAvailability(Long doctorId, LocalDate date) {
        // 09:00 - 13:00 arası, her 30 dakikada bir slot
        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(13, 0);
        for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(30)) {
            allSlots.add(time);
        }
        // Doktorun o gün için tüm randevularını bul
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return new ResponseDto<AvailabilityResponse>().FailResponse("Doktor bulunamadı.");
        }
        Doctor doctor = doctorOpt.get();
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        Set<LocalTime> unavailable = new HashSet<>();
        for (Appointment app : appointments) {
            if ((app.getStatus() == AppointmentStatus.CONFIRMED || app.getStatus() == AppointmentStatus.WAITING)
                && app.getAppointmentDate().toLocalDate().equals(date)) {
                unavailable.add(app.getAppointmentDate().toLocalTime().withSecond(0).withNano(0));
            }
        }
        List<LocalTime> unavailableSlots = new ArrayList<>(unavailable);
        unavailableSlots.sort(LocalTime::compareTo);
        AvailabilityResponse response = new AvailabilityResponse(allSlots, unavailableSlots);
        return new ResponseDto<AvailabilityResponse>().SuccessResponse("Saatler getirildi", response);
    }
} 