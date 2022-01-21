package com.donghyeon.springboot.config.auth;

import com.donghyeon.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정 활성화
//시큐리티 관련 설정(Configure) 클래스
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                .authorizeRequests()//URL별 관리를 설정하는 옵션의 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                /*
                 * andMatcher : 권한 관리 대상 지정 옵션
                 * URL, HTTP 메소드별로 관리가 가능
                 * "/"등 지정된 URL(기본적인 URL)들은 permitAll()을 통해 전체 열람 권한 부여
                 * "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능
                 */
                .anyRequest() //설정된 URL을 제외한 나머지 URL
                .authenticated() //나머지 URL 들은 모두 인증된 사용자(로그인한 사용자)들에게만 허용
                .and()
                .logout()//로그아웃에 대한 여러 설정의 진입점
                .logoutSuccessUrl("/")//로그아웃 성공 시 "/" 주소로 이동
                .and()
                .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당
                .userService(customAuth2UserService); //소설 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
        // 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있음
    }
}
