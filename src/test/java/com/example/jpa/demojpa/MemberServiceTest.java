package com.example.jpa.demojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.example.jpa.domain.member.Member;
import com.example.jpa.repository.MemberRepository;
import com.example.jpa.service.MemberService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class MemberServiceTest {
  @Autowired
  MemberService memberService;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  EntityManager em;

  @Test
  public void join() throws Exception {
    // given
    Member member = new Member();
    member.setName("kim");
    // when
    Long savedId = memberService.join(member);
    em.flush();
    // then
    assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test()
  public void getMembers() throws Exception {
    //given
    Member member1 = new Member();
    member1.setName("kim");
    Member member2 = new Member();
    member2.setName("kim");

    //when
    memberService.join(member1);

    //then
    IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
  }
}
