package com.pabloinsdrums.apigestion.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LogoutResponse implements Serializable {
    private String message;
}
