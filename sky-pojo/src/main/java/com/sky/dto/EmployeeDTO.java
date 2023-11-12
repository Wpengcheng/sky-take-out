package com.sky.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Valid
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String sex;

    @NotBlank(message = "身份证号不能为空")
    private String idNumber;

}
