package com.pabloinsdrums.apigestion.dto.permission;


import lombok.Data;

import java.io.Serializable;

@Data
public class ShowPermission implements Serializable {
    private Long id;
    private String operation;
    private String httpMethod;
    private String module;
    private String role;
}
