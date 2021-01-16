package com.example.jpa.demojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import com.example.jpa.domain.member.Member;
import com.example.jpa.domain.member.Team;
import com.example.jpa.dto.MemberDto;
import com.example.jpa.repository.MemberRepository;
import com.example.jpa.repository.TeamRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  TeamRepository teamRepository;

  @Test
  public void testMember() {

    Member member = new Member("member A", 10);
    Member savedMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(savedMember.getId()).get();

    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    Assertions.assertThat(findMember).isEqualTo(member);

    Member findMember2 = memberRepository.findByName(savedMember.getName()).get(0);
    Member findMember3 = memberRepository.findByNameAndAgeGreaterThan(savedMember.getName(), 5).get(0);

    assertEquals("member A", findMember2.getName());
    assertEquals(10, findMember3.getAge());

  }

  @Test
  public void findMemberDto() {
    Team teamA = new Team("Team A");
    teamRepository.save(teamA);

    Member member1 = new Member("member1", 10, teamA);
    memberRepository.save(member1);

    List<MemberDto> findMembers = memberRepository.findMemberDto();

    for (MemberDto m : findMembers) {
      System.out.println("member = " + m);
    }
  }
}
