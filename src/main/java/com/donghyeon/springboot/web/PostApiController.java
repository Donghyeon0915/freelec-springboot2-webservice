package com.donghyeon.springboot.web;

import com.donghyeon.springboot.service.posts.PostsService;
import com.donghyeon.springboot.web.dto.PostsResponseDto;
import com.donghyeon.springboot.web.dto.PostsSaveRequestDto;
import com.donghyeon.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
//Controller : 클라이언트로 부터 요청을 받는 클래스
public class PostApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts") //@GetMapping과 다르게 url에 데이터 노출 x, Body로 데이터를 받음
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        //@RequestBody : Post Api에선 Body 형식으로 데이터를 주고 받으므로 받아야하는 데이터를 나타냄(@RequestParam과 비슷)

        //클라이언트에게서 요청을 받은 Controller 클래스가 Service 클래스에게 데이터 가공을 요청
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
