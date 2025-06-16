package com.clinic.hospital_appointment_backend.entity;

import java.util.List;

import com.clinic.hospital_appointment_backend.enums.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

// Hasta bilgilerini tutan entity sınıfı
@Data
@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {
    
   
    @Column(name = "name", nullable = false)
    private String name;

   
    @Column(name = "surname", nullable = false)
    private String surname;

  
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Hastanın şifresi (JSON dönüşümlerinde gizlenir)
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    // Kullanıcı rolü (PATIENT olarak atanır)
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Hastanın randevuları (bire-çok ilişki)
    @JsonManagedReference
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
