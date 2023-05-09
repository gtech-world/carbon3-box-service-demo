package com.gtech.carbon3boxservicedemo.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TestRequest {
    @NotBlank(message = "name not blank")
    private String name;
}