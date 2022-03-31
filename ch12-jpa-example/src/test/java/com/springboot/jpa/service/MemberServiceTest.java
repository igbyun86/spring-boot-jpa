package com.springboot.jpa.service;

import com.springboot.jpa.domain.*;
import com.springboot.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional      //테스트를 실행할 때마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백한다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //Given
        Member member = new Member();
        member.setName("Kim");

        //When
        Long saveId = memberService.join(member);

        //Then
        Assertions.assertEquals(member, memberRepository.findById(saveId).get());
    }

    @Test
    void 중복_회원_예외() {
        //Given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //Then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            //When
            memberService.join(member1);
            memberService.join(member2); //예외가 발생해야 한다.
        }, "예외가 발생해야 한다.");
    }
}
