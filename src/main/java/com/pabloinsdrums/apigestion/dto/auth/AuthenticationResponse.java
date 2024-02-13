package com.pabloinsdrums.apigestion.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationResponse implements Serializable {

    private String jwt;
}
