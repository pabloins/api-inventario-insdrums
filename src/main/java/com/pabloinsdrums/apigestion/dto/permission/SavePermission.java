package com.pabloinsdrums.apigestion.dto.permission;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SavePermission implements Serializable {
    @NotBlank
    private String role;

    @NotBlank
    private String operation;
}
