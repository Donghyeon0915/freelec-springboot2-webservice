package com.donghyeon.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JpaRepository를 상속하면 기본적으로 CRUD을 지원
public interface PostsRepository extends JpaRepository<Posts, Long> {
    
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") //JpaRepository를 상속하면 기본적으로 CRUD 기능을 지원하지만 직접 Query로 기능(메소드)을 추가 할 수 있음
    List<Posts> findAllDesc(); //위의 Query를 통해 게시글 정보를 가져와서 List로 반환
}
