package com.example.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.example.jpa.domain.member.Member;
import com.example.jpa.dto.MemberDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findListByName(String name);

  Member findOneByName(String name);

  Optional<Member> findOptionalByName(String name);

  List<Member> findByNameAndAgeGreaterThan(String name, int age);

  // @Query(name = "Member.findByAge") // NamedQuery의 이름. 생략됨
  List<Member> findByAge(int age);

  @Query("select m from Member m where m.name = :name and m.age = :age")
  List<Member> findMember(String name, int age);

  @Query("select m.name from Member m")
  List<String> findUsernameList();

  @Query("select new com.example.jpa.dto.MemberDto(m.id, m.name, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();

  @Query("select m from Member m where m.name = :name")
  List<Member> find___ByName(@Param("name") String username);

  @Query("select m from Member m where m.name in :names")
  List<Member> findByNames(@Param("names") Collection<String> names);

  @Query(value = "select m from Member m left join m.team t where m.age = :age", countQuery = "select count(m) from Member m where m.age = :age")
  Page<Member> findSortAndPage(@Param("age") int age, Pageable paging);

  List<Member> findTop3By();

  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);

  @Override
  @EntityGraph(attributePaths = { "team" })
  List<Member> findAll();

  @Query("select m from Member m fetch join m.team")
  List<Member> findMemberFetchJoin();

  @EntityGraph(attributePaths = { "team" })
  @Query("select m from Member m")
  List<Member> findMemberFetchJoinByEntityGraph();

  @EntityGraph("Member.all")
  @Query("select m from Member m")
  List<Member> findMemberFetchJoinByNamedEntityGraph();
}