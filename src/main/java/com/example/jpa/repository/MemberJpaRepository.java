package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import com.example.jpa.domain.member.Member;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {
  private final EntityManager em;

  public Long save(Member member) {
    em.persist(member);
    return member.getId();
  }

  public void delete(Member member) {
    em.remove(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class).setParameter("name", name)
        .getResultList();
  }

  public Optional<Member> findById(Long id) {
    Member member = em.find(Member.class, id);
    return Optional.ofNullable(member);
  }

  public long count() {
    return em.createQuery("select count(m) from Member m", Long.class).getSingleResult();
  }
}
