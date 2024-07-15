package com.movie.rock.member.data;

import lombok.Data;

// 김승준 - 회원
@Data
public class MemberInfoDTO {

    private String memEmail;

    private String memName;

    private String memGender;

    private RoleEnum memRole;
}
