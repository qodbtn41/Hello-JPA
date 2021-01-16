package com.example.jpa.demojpa.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.jpa.domain.member.Member;
import com.example.jpa.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberJpaTest {
  @Autowired
  MemberRepository repository;

  @Test
  public void basicCRUD() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");
    repository.save(member1);
    repository.save(member2);

    Member findMember1 = repository.findById(member1.getId()).get();
    Member findMember2 = repository.findById(member2.getId()).get();
    assertEquals(member1, findMember1);
    assertEquals(member2, findMember2);

    List<Member> all = repository.findAll();
    assertEquals(2, all.size());

    long count = repository.count();
    assertEquals(2, count);

    repository.delete(member1);
    repository.delete(member2);
    long deleteCount = repository.count();
    assertEquals(0, deleteCount);
  }
}
