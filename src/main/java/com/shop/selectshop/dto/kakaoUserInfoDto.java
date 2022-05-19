package com.shop.selectshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor // 생성자를 만들어줘
@Getter
public class kakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String email;

}
