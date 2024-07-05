package com.movie.rock.config;

import lombok.Data;

// 김승준 - 회원
@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
