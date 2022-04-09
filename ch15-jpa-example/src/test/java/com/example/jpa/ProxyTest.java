package com.example.jpa;


import com.example.jpa.entity.Member;
import com.example.jpa.entity.OrderItem;
import com.example.jpa.entity.item.Book;
import com.example.jpa.entity.item.Item;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProxyTest {


    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void insertMember() {

    }


    @Test
    void 영속성컨텍스트와_프록시() {
        Member member = new Member("member1", "회원1");
        em.persist(member);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, "member1");
        Member findMember = em.find(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());
        /*
        refMember Type = class com.example.jpa.entity.Member$HibernateProxy$IpK4Sjz8
        findMember Type = class com.example.jpa.entity.Member$HibernateProxy$IpK4Sjz8
        */

        // 영속성 컨텍스트와 프록시 동일성 비교
        assertTrue(refMember == findMember); //true
    }


    @Test
    @DisplayName("원본 먼저 조회하고 나서 프록시로 조회하기")
    void 영속성컨텍스트와_프록시2() {
        Member newMember = new Member("member1", "회원1");
        em.persist(newMember);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, "member1");
        Member refMember = em.getReference(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());
        /*
        refMember Type = class com.example.jpa.entity.Member
        findMember Type = class com.example.jpa.entity.Member
        */

        // 영속성 컨텍스트와 프록시 동일성 비교
        assertTrue(refMember == findMember); //true
    }

    @Test
    void 프록시_타입비교() {
        Member newMember = new Member("member1", "회원1");
        em.persist(newMember);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        //refMember Type = class com.example.jpa.entity.Member$HibernateProxy$pg6fQIiU

        assertFalse(Member.class == refMember.getClass());      //false(부무클래스와 자식 클래스를 ==비교 했으므로 false)
        assertTrue(refMember instanceof Member);                        //true(프록시는 원본 엔티티의 자식 타입이므로 true)
    }


    @Test
    @DisplayName("name을 기준으로 비교")
    void 프록시와_동등성비교() {
        Member saveMember = new Member("member1", "회원1");
        em.persist(saveMember);
        em.flush();
        em.clear();

        Member newMember = new Member("member1", "회원1");
        Member refMember = em.getReference(Member.class, "member1");

        assertTrue(refMember instanceof Member);
        assertTrue(newMember.equals(refMember));
    }


    @Test
    void 부모타입으로_프록시조회()  {
        Book saveBook = new Book();
        saveBook.setName("jpabook");
        saveBook.setAuthor("ig");
        em.persist(saveBook);

        em.flush();
        em.clear();

        //테스트 시작
        Item proxyItem = em.getReference(Item.class, saveBook.getId());
        System.out.println("proxyItem = " + proxyItem.getClass());
        //proxyItem = class com.example.jpa.entity.item.Item$HibernateProxy$II2fqExK

        if (proxyItem instanceof Book) {
            System.out.println("proxyItem instanceof Book");
            Book book = (Book) proxyItem;
            System.out.println("책 저자 = " + book.getAuthor());
        }

        assertFalse(proxyItem.getClass() == Book.class);
        assertFalse(proxyItem instanceof Book);
        assertTrue(proxyItem instanceof Item);
    }

    @Test
    void 상속관계와_프록시_도메인모델() {
        //테스트 데이터 준비
        Book book = new Book();
        book.setName("jpabook");
        book.setAuthor("ig");
        em.persist(book);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(book);
        em.persist(saveOrderItem);

        em.flush();
        em.clear();

        //테스트 시작
        OrderItem orderItem = em.find(OrderItem.class, saveOrderItem.getId());
        Item item = orderItem.getItem();

        System.out.println("item = " + item.getClass());
        //item = class com.example.jpa.entity.item.Item$HibernateProxy$JKb7J11y

        //결과검증
        assertFalse(item.getClass() == Book.class); //지연로딩으로 Book class의 정보를 가져올 수 없다.
        assertFalse(item instanceof Book);
        assertTrue(item instanceof Item);
    }

    @Test
    @DisplayName("프록시해결1-프록시 벗기기")
    void proxySolutionTest1() {
        //테스트 데이터 준비
        Book saveBook = new Book();
        saveBook.setName("jpabook");
        saveBook.setAuthor("ig");
        em.persist(saveBook);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(saveBook);
        em.persist(saveOrderItem);

        em.flush();
        em.clear();

        //테스트 시작
        OrderItem orderItem = em.find(OrderItem.class, saveOrderItem.getId());
        Item item = orderItem.getItem();

        Item unProxyItem = unProxy(item);

        System.out.println("item = " + item.getClass());
        //item = class com.example.jpa.entity.item.Item$HibernateProxy$JKb7J11y

        System.out.println("item = " + unProxyItem.getClass());
        //item = class com.example.jpa.entity.item.Book

        if (unProxyItem instanceof Book) {
            System.out.println("proxyItem instanceof Book");
            Book book = (Book) unProxyItem;
            System.out.println("책 저자 = " + book.getAuthor());
        }

        //결과검증
        assertFalse(item.getClass() == Book.class); //지연로딩으로 Book class의 정보를 가져올 수 없다.
        assertFalse(item instanceof Book);
        assertTrue(item instanceof Item);

        assertTrue(item != unProxyItem);
    }

    /**
     * 하이버네이트가 제공하는 프록시에서 원본 엔티티를 찾는 기능을 사용하는 메서드
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T unProxy(Object entity) {
        if (entity instanceof HibernateProxy) {
            entity = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();

        }

        return (T) entity;
    }


    @Test
    @DisplayName("프록시해결2-프록시 인터페이스 제공")
    void proxySolutionTest2() {
        //테스트 데이터 준비
        Book saveBook = new Book();
        saveBook.setName("jpabook");
        saveBook.setAuthor("ig");
        em.persist(saveBook);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(saveBook);
        em.persist(saveOrderItem);

        em.flush();
        em.clear();

        //테스트 시작
        OrderItem orderItem = em.find(OrderItem.class, saveOrderItem.getId());
        orderItem.printItem();  //특정 기능을 제공하는 인터페이스 사용
        //TITLE=[제목:]jpabook 저자:ig]
    }

    @Test
    @DisplayName("Visitor 패턴 해결")
    void 상속관계와_프록시_VsitorPattern() {
        //테스트 데이터 준비
        Book saveBook = new Book();
        saveBook.setName("jpabook");
        saveBook.setAuthor("ig");
        em.persist(saveBook);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(saveBook);
        em.persist(saveOrderItem);

        em.flush();
        em.clear();

        OrderItem orderItem = em.find(OrderItem.class, saveOrderItem.getId());
        Item item = orderItem.getItem();

        //PrintVisitor
        item.accept(new PrintVisitor());

    }


}
