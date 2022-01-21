package com.donghyeon.springboot.web;

import com.donghyeon.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //SpringBoot 테스트와 JUnit(자바 테스트 도구) 사이의 연결자 역할
@WebMvcTest(controllers = HelloController.class, //Web에 집중할 수 있는 어노테이션(controllers에 HelloController 클래스를 연결)
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
//@MockBean(JpaMetamodelMappingContext.class) //JpaAuditing으로 인해 Jpa Bean이 필요

public class HelloControllerTest {
    @Autowired //스프링이 관리하는 빈(Bean)을 주입 받음
    private MockMvc mvc; //웹 API 테스트할 때 사용, MVC 테스트의 시작점, API 테스트 가능

    @WithMockUser(roles = "USER")
    @Test
    public void returnHello() throws Exception {
        String hello = "hello";

        //MockMvc를 통해 /hello 주소로 HTTP GET 요청
        //체이닝이 지원되어 여러 검증 기능을 이어서 선언
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())  //상태를 검증(200, 404, 500 등)
                .andExpect(content().string(hello)); //응답 본문의 내용을 검증, controllers에서 "hello"를 리턴하기 떄문에 이 값이 맞는지 검증   
        //테스트하려는 클래스(HelloController)의 메소드에서 hello를 리턴함. 실제로 웹페이지를 열어보면 hello가 출력됨
    }

    @WithMockUser(roles = "USER")
    @Test
    public void returnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                        get("/hello/Dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
