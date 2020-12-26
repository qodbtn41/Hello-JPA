package com.example.jpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.example.jpa.domain.member.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
  @Autowired
  EntityManagerFactory emf;

  public List<Member> getMember() {
    List<Member> result = null;
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      result = em.createQuery("select m from Member as m", Member.class).getResultList();
      for (Member member : result) {
        System.out.println("name : " + member.getName());
        member.setName("변경 테스트");
      }
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    return result;
  }

}
