package com.example.jpa.demojpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  EntityManager em;

  @Test
  public void testMember() {

    Member member = new Member("member A", 10);
    Member savedMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(savedMember.getId()).get();

    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    Assertions.assertThat(findMember).isEqualTo(member);

    Member findMember2 = memberRepository.findListByName(savedMember.getName()).get(0);
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

  @Test
  public void findByNames() {
    Member member1 = new Member("member1", 10);
    Member member2 = new Member("member2", 20);
    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> findMembers = memberRepository.findByNames(Arrays.asList("member1", "member2"));

    for (Member m : findMembers) {
      System.out.println("member = " + m);
    }
  }

  @Test
  public void sortAndPaging() {
    Member member1 = new Member("member1", 10);
    Member member2 = new Member("member2", 10);
    Member member3 = new Member("member3", 10);
    Member member4 = new Member("member4", 10);
    Member member5 = new Member("member5", 10);
    memberRepository.save(member1);
    memberRepository.save(member2);
    memberRepository.save(member3);
    memberRepository.save(member4);
    memberRepository.save(member5);

    Pageable paging = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "name", "age"));
    Page<Member> page = memberRepository.findSortAndPage(10, paging);

    List<Member> content = page.getContent();

    for (Member m : content) {
      System.out.println("member = " + m);
    }

    Page<MemberDto> pageDto = page.map(member -> new MemberDto(member.getId(), member.getName(), null));
    for (MemberDto m : pageDto.getContent()) {
      System.out.println("memberDto = " + m);
    }

    assertEquals(3, content.size()); // 조회 개수
    assertEquals(5, page.getTotalElements()); // 총 개수
    assertEquals(0, page.getNumber()); // 페이징 번호
    assertEquals(2, page.getTotalPages()); // 총 페이지 수
    assertEquals(false, page.hasPrevious()); // 이전 페이지가 있는가
    assertEquals(true, page.hasNext()); // 다음 페이지가 있는가
    assertEquals(true, page.isFirst()); // 첫 페이지인가
    assertEquals(false, page.isLast()); // 마지막 페이지인가
    assertEquals(false, page.isEmpty()); // 비어있는가

    assertEquals(3, page.getSize());
  }

  @Test
  public void bulkUpdate() {
    Member member1 = new Member("member1", 10);
    Member member2 = new Member("member2", 19);
    Member member3 = new Member("member3", 20);
    Member member4 = new Member("member4", 21);
    Member member5 = new Member("member5", 40);
    memberRepository.save(member1);
    memberRepository.save(member2);
    memberRepository.save(member3);
    memberRepository.save(member4);
    memberRepository.save(member5);

    int resultCount = memberRepository.bulkAgePlus(20);

    Member result = memberRepository.findOneByName("member5");
    System.out.println("member5 = " + result);

    assertEquals(3, resultCount);
  }

  @Test
  public void findMemberlazy() {
    Team teamA = new Team("Team A");
    Team teamB = new Team("Team B");
    teamRepository.save(teamA);
    teamRepository.save(teamB);

    Member member1 = new Member("member1", 10, teamA);
    Member member3 = new Member("member3", 30, teamB);
    memberRepository.save(member1);
    memberRepository.save(member3);

    em.flush();
    em.clear();

    List<Member> findMembers = memberRepository.findMemberFetchJoin();

    for (Member m : findMembers) {
      System.out.println("member = " + m);
      System.out.println("-> member.team = " + m.getTeam());
    }
  }

  @Test
  public void queryHint() {
    Member member1 = new Member("member1", 10);
    memberRepository.save(member1);
    em.flush();
    em.clear();

    Member findMember = memberRepository.findReadOnlyByName(member1.getName());
    findMember.setName("member2"); // 변경해도 변경감지되지 않는다. 스냅샷이 생기지 않는다.

    em.flush();
  }

  @Test
  public void queryLock() {
    Member member1 = new Member("member1", 10);
    memberRepository.save(member1);
    em.flush();
    em.clear();

    List<Member> findMembers = memberRepository.findLockByName(member1.getName());

    for (Member m : findMembers) {
      System.out.println("member = " + m);
    }
    em.flush();
  }

  @Test
  public void findCustom() {
    Member member1 = new Member("member1", 10);
    Member member2 = new Member("member2", 20);
    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> findMembers = memberRepository.findMemberCustom();

    for (Member m : findMembers) {
      System.out.println("member = " + m);
    }
  }
}
