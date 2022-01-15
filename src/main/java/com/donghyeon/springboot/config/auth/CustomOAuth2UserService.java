package com.donghyeon.springboot.config.auth;

import com.donghyeon.springboot.config.auth.dto.OAuthAttributes;
import com.donghyeon.springboot.config.auth.dto.SessionUser;
import com.donghyeon.springboot.domain.user.User;
import com.donghyeon.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
/*
 * Service 클래스는 Controller 클래스에서 요청을 받은 데이터를 가공하여 다시 Controller에게 넘김
 */
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //현재 로그인 중인 서비스(구글,네이버,카카오 등)를 구분
        String userNameAttributeName /*유저 속성*/ = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        //OAuth2 로그인 시 키가 되는 필드 값(PrimaryKey와 같음) : 네이버와 구글 로그인을 동시 지원할 때 사용

        
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담는 클래스(이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용)
        
        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));
        //SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        //로그인 성공 시 SessionUser 타입으로 httpSession에 저장됨
        //다른 곳에서 세션을 받아오려면 httpSession.getAttribute()로 받아옴

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    //사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영됨
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
