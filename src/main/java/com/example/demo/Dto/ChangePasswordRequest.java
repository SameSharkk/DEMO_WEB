package com.example.demo.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ChangePasswordRequest {

    @NotBlank(message = "Mật khẩu cũ trống")
    @JsonProperty("old_password")
    private String oldPassword;

    @NotBlank(message = "Mật khẩu mới trống")
    @JsonProperty("new_password")
    private String newPassword;
}