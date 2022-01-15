package com.donghyeon.springboot.web;

import com.donghyeon.springboot.config.auth.LoginUser;
import com.donghyeon.springboot.config.auth.dto.SessionUser;
import com.donghyeon.springboot.service.posts.PostsService;
import com.donghyeon.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        //<Key, Value> 타입으로 index.mustache로 전달 됨
        //Value에 findAllDesc() 메소드에 의해 List 타입이 들어가고 index.mustache에선 {{#posts}}로 매핑되어 for문으로 순회됨
        model.addAttribute("posts", postsService.findAllDesc());

        //httpSession에 <"name", new SessionUser(user)> 타입으로 저장되어있음
        if(user != null) model.addAttribute("userName", user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id); //수정 할 포스트를 가져옴
        model.addAttribute("post", dto); //서버 템플릿 엔진(머스테치)에서 쓸 수 있도록 객체를 저장

        return "posts-update";  //posts-update.mustache로 전달
    }
}
