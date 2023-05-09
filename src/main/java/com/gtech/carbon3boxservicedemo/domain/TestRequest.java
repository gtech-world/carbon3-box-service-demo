package com.gtech.carbon3boxservicedemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class TestRequest {
    @NotBlank(message = "name not blank")
    private String name;
}