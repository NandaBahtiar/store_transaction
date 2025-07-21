package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String birthplace;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
