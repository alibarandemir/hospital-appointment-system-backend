package com.clinic.hospital_appointment_backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

// Tüm entity sınıfları için temel özellikleri içeren sınıf
@Data
@MappedSuperclass
public class BaseEntity {
    // Veritabanında otomatik artan benzersiz id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kaydın oluşturulma tarihi (otomatik set edilir)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Kaydın son güncellenme tarihi (otomatik set edilir)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
