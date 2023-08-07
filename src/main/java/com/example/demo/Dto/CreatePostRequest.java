package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class CreatePostRequest {
    @NotEmpty(message = "Tiêu đề rỗng")
    private String title;

    @NotEmpty(message = "Nội dung rỗng")
    private String content;
}
