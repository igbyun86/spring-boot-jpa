package com.ig.jpa.entity;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",   //시퀀스 생성기 이름(시퀀스 적용할 컬럼과 매핑되는 이름)
        sequenceName = "BOARD_SEQ",     //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1      // default:50 이며 설정값만큼 시퀀스값을 증가시키고 메모리에 값을 할당하여 사용한다.(시퀀스 한번 호출에 증가하는 수)
)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    private String titie;

    @Lob
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitie() {
        return titie;
    }

    public void setTitie(String titie) {
        this.titie = titie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
