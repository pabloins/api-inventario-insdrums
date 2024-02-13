package com.pabloinsdrums.apigestion.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SaveUser implements Serializable {
    @Size(min = 4)
    private String name;
    @Size(min = 4)
    private String username;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String repeatedPassword;
}
