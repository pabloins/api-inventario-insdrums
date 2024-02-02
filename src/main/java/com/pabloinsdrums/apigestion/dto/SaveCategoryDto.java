package com.pabloinsdrums.apigestion.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SaveCategoryDto implements Serializable {
    @NotBlank
    private String name;
}
