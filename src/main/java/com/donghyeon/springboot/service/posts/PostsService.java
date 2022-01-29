package com.donghyeon.springboot.service.posts;

import com.donghyeon.springboot.domain.posts.Posts;
import com.donghyeon.springboot.domain.posts.PostsRepository;
import com.donghyeon.springboot.web.dto.PostsListResponseDto;
import com.donghyeon.springboot.web.dto.PostsResponseDto;
import com.donghyeon.springboot.web.dto.PostsSaveRequestDto;
import com.donghyeon.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
/*
 * Service 클래스는 Controller 클래스에서 요청을 받은 데이터를 가공하여 다시 Controller에게 넘김
 */
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional //모든 작업이 성공적으로 완료되어야 결과를 적용함. 오류가 발생하면 원래대로 되돌림 -> DB의 경우 모든 작업들이 성공하면 Db에 반영이 된다
    public Long save(PostsSaveRequestDto requestDto) {
        //도메인 모델을 이용하여 postsRepository 클래스 내에서 작업이 처리되도록 구성
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        //삭제할 포스트를 가져옴(없으면 IllegalArgumentException 발생)
        Posts posts = postsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 없습니다. id"));

        postsRepository.delete(posts);
    }


}
