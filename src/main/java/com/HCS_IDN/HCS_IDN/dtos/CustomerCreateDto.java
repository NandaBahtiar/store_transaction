package com.HCS_IDN.HCS_IDN.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerCreateDto {
    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthdate;

    @NotBlank
    private String birthplace;

    @NotNull
    private Long createdBy;
}
