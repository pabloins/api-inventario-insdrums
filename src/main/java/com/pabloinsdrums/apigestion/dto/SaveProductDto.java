package com.pabloinsdrums.apigestion.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SaveProductDto implements Serializable {
    @NotBlank
    private String name;

    @DecimalMin(value= "0.01", message = "price must be greater than zero")
    private BigDecimal price;

    @Min(value = 1)
    private Long categoryId;
}
