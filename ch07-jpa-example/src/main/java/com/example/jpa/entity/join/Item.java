package com.example.jpa.entity.join;

import javax.persistence.*;


/**
 * @Inheritance
 * :상속 매핑은 부모 클래스에 @Inheritance를 사용해야 한다. 그리고 매핑 전략을 지정해야 하는데 여기서는 조인전략을 사용하므로 InheritanceType.JOINED를 사용했다.
 * @DiscriminatorColumn(name = "DTYPE")
 * :부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 저장된 자식 테이블을 구분할 수 있다.
 * 기본값이 DTYPE이므로 @DiscriminatorColumn으로 줄여 사용해도 된다.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;

    private int price;
}
