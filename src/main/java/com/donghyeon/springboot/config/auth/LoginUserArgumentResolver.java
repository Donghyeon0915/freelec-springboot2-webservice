package com.donghyeon.springboot.config.auth;

import com.donghyeon.springboot.config.auth.LoginUser;
import com.donghyeon.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //파라미터에 @LoginUser 어노테이션이 있는지 체크
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        //파라미터로 넘어온 @LoginUser 어노테이션이 붙은 클래스가 SessionUser 클래스인지를 체크
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        //파라미터 클래스가 @LoginUser가 붙어있고 SessionUser 클래스면 true
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }

}
