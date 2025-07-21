package com.HCS_IDN.HCS_IDN.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerUpdateDto {
    private String name;
    private LocalDate birthdate;
    private String birthplace;
    private Long updatedBy;
}
