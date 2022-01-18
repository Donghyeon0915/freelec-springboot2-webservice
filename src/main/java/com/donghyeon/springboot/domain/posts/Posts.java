package com.donghyeon.springboot.domain.posts;

import com.donghyeon.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
/*
 * Post 클래스는 DB의 테이블과 매칭 됨 (Entity 클래스라고 함)
 * JPA를 사용하면 쿼리를 날리는게 아니라 이 클래스를 수정하여 작업
 */
public class Posts extends BaseTimeEntity {
    @Id //PK 필드임을 나타냄 (id 변수가 PRIMARY KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //GenerationType.IDENTITY를 추가해야 autoincrement(자동으로 값 증가)가 됨
    private Long id;

    @Column(length = 500, nullable = false) //테이블의 칼럼을 나타냄, 선언하지 않아도 Entity 클래스의 필드는 모두 칼럼이되지만 칼럼의 옵션을 변경할 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String author;

    @Builder //빌더 패턴 클래스를 생성. 생성자 상단에 선언하면 생성자에 포함된 필드만 포함함
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
