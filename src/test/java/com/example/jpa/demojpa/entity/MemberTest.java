package com.example.jpa.demojpa.entity;

import javax.persistence.EntityManager;

import com.example.jpa.domain.member.Member;
import com.example.jpa.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  EntityManager em;

  @Test
  public void JpaEventBaseEntity() throws Exception {
    Member member = new Member("member1");
    memberRepository.save(member);

    Thread.sleep(100);
    member.setName("member2");

    em.flush();
    em.clear();

    Member findMember = memberRepository.findById(member.getId()).get();

    System.out.println("findMember.craetedTime = " + findMember.getCreatedTime());
    System.out.println("findMember.lastModifiedTime = " + findMember.getLastModifiedTime());
    System.out.println("findMember.createdBy = " + findMember.getLastModifiedBy());
    System.out.println("findMember.lastModifiedBy = " + findMember.getLastModifiedBy());
  }
}
