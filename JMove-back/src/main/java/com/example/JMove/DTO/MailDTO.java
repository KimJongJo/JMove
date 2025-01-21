package com.example.JMove.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MailDTO {
    private String address; // 메일 주소
    private String title; // 메일 제목
    private String message; // 메일 메시지
}
