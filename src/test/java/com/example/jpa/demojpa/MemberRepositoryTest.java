package com.example.jpa.demojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.jpa.entity.Member;
import com.example.jpa.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberRepositoryTest {
  @Autowired
  MemberRepository memberRepository;

  @Test
  @Transactional
  // @Rollback(false) // @Test가 있으면 자동롤백. 적접 보고싶을때 쓰면 된다.
  public void testMember() throws Exception {
    Member member = new Member();
    member.setName("memberA");

    Long savedId = memberRepository.save(member);
    Member findMember = memberRepository.find(savedId);

    assertEquals(findMember.getId(), member.getId());
    assertEquals(findMember.getName(), member.getName());
    assertEquals(findMember, member);
  }
}
