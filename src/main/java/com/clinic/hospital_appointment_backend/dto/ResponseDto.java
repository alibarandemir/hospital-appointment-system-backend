package com.clinic.hospital_appointment_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private String message;
    private T data;

    public ResponseDto<T> SuccessResponse(String message, T data) {
        return new ResponseDto<T>(true, message, data);
    }

    public ResponseDto<T> FailResponse(String message) {
        return new ResponseDto<T>(false, message, null);
    }

}
