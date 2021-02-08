package com.example.jpa.demojpa.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.member.QMember;
import com.example.jpa.repository.MemberRepository;
import com.querydsl.core.QueryResults;
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
    List<Member> findMember = queryFactory.select(QMember.member).from(QMember.member).fetch();

    assertEquals("userA", findMember.get(0).getName());
  }

  @Test
  public void search() {
    Member findMember = queryFactory.select(QMember.member).from(QMember.member).where(QMember.member.name.eq("userA"))
        .fetchOne();

    assertEquals("userA", findMember.getName());
  }

  @Test
  public void search2() {
    Member findMember = queryFactory.selectFrom(QMember.member)
        .where(QMember.member.name.eq("userA").and(QMember.member.age.eq(10))).fetchOne();

    assertEquals("userA", findMember.getName());
    assertEquals(10, findMember.getAge());
  }

  @Test
  public void searchAndParam() {
    // null을 무시하기 때문에 동적 쿼리할 때 활용 가능하다.
    Member findMember = queryFactory.selectFrom(QMember.member)
        .where(QMember.member.name.eq("userA"), (QMember.member.age.eq(10)), null).fetchOne();

    assertEquals("userA", findMember.getName());
    assertEquals(10, findMember.getAge());
  }

  /**
   * 화원 나이 내림차순(desc) 회원 이름 올림차순(asc) 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
   */
  @Test
  public void sort() {
    em.persist(new Member(null, 100));
    em.persist(new Member("member5", 100));
    em.persist(new Member("member6", 100));

    List<Member> result = queryFactory.selectFrom(QMember.member).where(QMember.member.age.eq(100))
        .orderBy(QMember.member.age.desc(), QMember.member.name.asc().nullsLast()).fetch();

    Member member5 = result.get(0);
    Member member6 = result.get(1);
    Member memberNull = result.get(2);
    assertEquals("member5", member5.getName());
    assertEquals("member6", member6.getName());
    assertNull(memberNull.getName());
  }

  @Test
  public void paging1() {
    QMember member = QMember.member;
    List<Member> result = queryFactory.selectFrom(member).orderBy(member.name.desc()).offset(1).limit(2).fetch();
  }

  @Test
  public void paging2() {
    // 전체 조회수가 필요하면?
    QMember member = QMember.member;
    QueryResults<Member> result = queryFactory.selectFrom(member).orderBy(member.name.desc()).offset(1).limit(2)
        .fetchResults();

    assertEquals(4, result.getTotal());
    assertEquals(2, result.getLimit());
    assertEquals(1, result.getOffset());
    assertEquals(2, result.getResults().size());
  }
}
