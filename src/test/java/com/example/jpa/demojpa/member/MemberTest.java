package com.example.jpa.demojpa.member;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.member.Team;
import com.example.jpa.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {
  @Autowired
  EntityManager em;

  @Test
  public void testEntity() {
    Team teamA = new Team("Team A");
    Team teamB = new Team("Team B");
    em.persist(teamA);
    em.persist(teamB);

    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 20, teamA);
    Member member3 = new Member("member3", 30, teamB);
    Member member4 = new Member("member4", 40, teamB);

    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);

    em.flush();
    em.clear();

    List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();

    for (Member m : findMembers) {
      System.out.println("member = " + m);
      System.out.println("-> member.team = " + m.getTeam());
    }
  }

}
