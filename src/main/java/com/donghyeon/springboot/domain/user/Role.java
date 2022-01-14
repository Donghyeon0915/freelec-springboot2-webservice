package com.donghyeon.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor //final이 붙은 필드를 포함한 생성자를 선언

//사용자의 권한을 관리하는 Role 클래스(Enum)
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}