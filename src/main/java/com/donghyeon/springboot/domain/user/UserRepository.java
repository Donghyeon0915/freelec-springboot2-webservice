package com.donghyeon.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //소셜 로그인으로 반환되는 값 중 email을 통해 이미 가입 된 사용자인지 아닌지 판별하는 메소드
    Optional<User> findByEmail(String email);
}
