package com.example.jpa.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.example.jpa.entity.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistService {

  @Autowired
  EntityManagerFactory emf;

  public void persistTest() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      // 비영속
      Member member = new Member();
      member.setId(100L);
      member.setName("name2");

      // !영속
      // *BEFORE 과 AFTER 사이에 INSERT 쿼리가 실행되지 않는다.
      // *tx.commit() 호출 시에 쿼리가 날라간다.
      // *그 사이에는 영속성 컨텍스트 안에 존재한다.
      System.out.println("BEFORE");
      em.persist(member);
      System.out.println("AFTER");

      // !준영속
      // *영속성 컨텍스트에서 제거한다.
      // *뭐하러? 추가를 취소하면?
      // *기존 데이터면?
      em.detach(member);

      // !삭제
      // *삭제 쿼리
      em.remove(member);

      // !애플리케이션과 DB 사이에 중간 단계가 있다.
      // !1차 캐시
      // *캐싱되어 find를 날려도 Select 쿼리가 1번만 날아간다.
      Member findMember = em.find(Member.class, 1L);
      Member findMember2 = em.find(Member.class, 1L);
      System.out.println("findMember.id = " + findMember.getId());
      System.out.println("findMember.name = " + findMember.getName());

      // !동일성 보장
      // *1차 캐시로 인해 얻을 수 있는 이점
      if (findMember == findMember2) {
        System.out.println("둘은 같은 객체다. 같은 메모리 데이터이다.");
      }

      // !트랜잭션을 지원하는 쓰기 지연
      // *커밋할때까지 보내지않는다.
      // * 쓰기지연 SQL 저장소가 있음 persist된 엔티티가 거기 들어갈지는 JPA 알아서 결정한다. (어떻게??)
      // * 커밋하면 일괄 보내는걸 flush라고 하고 그다음 commit한다.
      // *알겠는데 그 이점은 무엇인가?
      // * 버퍼링이란 기능을 쓸 수 있다.
      // * 바로바로 보내면 최적화 할 수 있는 여지가 없다!
      // * JDBC Batch로 한방에 보낼 수 있다.
      // * spring.jpa.properties.hibernate.jdbc.batch_size=10

      // !변경 감지
      // * dirty checking
      // * 영속성 컨텍스트에 등록된 데이터가 변경된 경우
      // * 변경을 감지하여 UPDATE 날림
      // * member.setName("이름 변경");
      // * 커밋하면 변경 사항을 그냥 날림
      // * persist 할 필요가 없다. 알려주는 의미가 아님
      // * persist의 의미는 영속성 컨텍스트에 등록한다. 관리한다. 모니터링 한다.

      // ? 커밋 했을 때 동작
      // * 1. flush() - 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
      // * 2. Entity와 스냅샷(최초 들어왔을 때) 비교
      // * 3. UPDATE SQL 생성
      // * 4. db flush(쿼리 쭉 날림)
      // * 5. commit
      em.flush();
      // !지연 로딩
      // *

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
  }
}
