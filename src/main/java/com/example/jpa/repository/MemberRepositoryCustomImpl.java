package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.jpa.domain.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }
}
