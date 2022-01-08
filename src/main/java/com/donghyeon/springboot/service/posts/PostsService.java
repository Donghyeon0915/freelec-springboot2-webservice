package com.donghyeon.springboot.service.posts;

import com.donghyeon.springboot.domain.posts.Posts;
import com.donghyeon.springboot.domain.posts.PostsRepository;
import com.donghyeon.springboot.web.dto.PostsResponseDto;
import com.donghyeon.springboot.web.dto.PostsSaveRequestDto;
import com.donghyeon.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
/*
 * Service 클래스는 Controller 클래스에서 요청을 받은 데이터를 가공하여 다시 Controller에게 넘김
 */
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        //도메인 모델을 이용하여 postsRepository 클래스 내에서 작업이 처리되도록 구성
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}
