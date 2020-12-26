package com.example.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.jpa.domain.member.Member;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
  @PersistenceContext
  private EntityManager em;

  public Long save(Member member) {
    em.persist(member);
    return member.getId();
  }

  public Member find(Long id) {
    return em.find(Member.class, id);
  }
}
