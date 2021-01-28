package com.example.jpa.demojpa.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.member.QMember;
import com.example.jpa.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
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

  JPAQueryFactory queryFactory;

  @BeforeEach
  public void before() {
    queryFactory = new JPAQueryFactory(em);
  }

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

  @Test
  public void startJPQL() {
    Member findByJPQL = em.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name", "userA").getSingleResult();

    assertEquals("userA", findByJPQL.getName());
  }

  @Test
  public void startQuerydsl() {
    Member findMember = queryFactory.select(QMember.member).from(QMember.member).where(QMember.member.name.eq("userA"))
        .fetchOne();

    assertEquals("userA", findMember.getName());
  }
}
