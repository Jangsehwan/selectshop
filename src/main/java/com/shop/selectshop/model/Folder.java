package com.shop.selectshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할
public class Folder {

    // ID가 자동으로 생성 및 증가
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;


    // 관심 상품 생성 시 이용합니다.
    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }

}