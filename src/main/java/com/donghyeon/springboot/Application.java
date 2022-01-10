package com.donghyeon.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 활성화
/*
 * 테스트는 가장 기본이 되는 XXXApplication 클래스가 항상 로드되는데, @EnableJpaAuditing이 Application 클래스에
 * 등록되어 있어서 모든 테스트들이 JPA 관련 Bean을 필요로 하는 상태여서 테스트 오류가 남
 * 해결 방법 : 테스트 클래스에 @MockBean 추가
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
