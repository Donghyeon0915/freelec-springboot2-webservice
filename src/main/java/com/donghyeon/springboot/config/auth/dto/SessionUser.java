package com.donghyeon.springboot.config.auth.dto;

import com.donghyeon.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
//Serializable(직렬화) : 클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내고 받을 수 있게하려면 Serializable 인터페이스를 구현해야함
//직렬화는 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부에서도 사용할 수 있도록 바이트 형태로 바꿔주는 것
//바이트 형태의 데이터를 다시 객체로 변환하는 것은 역직렬화라고 부름
//Dto 클래스
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
