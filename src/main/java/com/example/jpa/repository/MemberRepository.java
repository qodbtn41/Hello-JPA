package com.example.jpa.repository;

import java.util.List;

import com.example.jpa.domain.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByName(String name);

  List<Member> findByNameAndAgeGreaterThan(String name, int age);

  // @Query(name = "Member.findByAge") // NamedQuery의 이름. 생략됨
  List<Member> findByAge(int age);
}
