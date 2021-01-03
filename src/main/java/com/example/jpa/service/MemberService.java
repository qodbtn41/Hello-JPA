package com.example.jpa.service;

import java.util.List;

import com.example.jpa.domain.member.Member;
import com.example.jpa.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  /**
   * 회원 가입
   * 
   * @param member
   * @return
   */
  @Transactional
  public Long join(Member member) {
    validateDuplicatedMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  private void validateDuplicatedMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  /**
   * 전체 회원 조회
   * 
   * @return
   */
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  /**
   * 특정 회원 조회
   * 
   * @param memberId
   * @return
   */
  public Member findMember(Long memberId) {
    return memberRepository.findOne(memberId);
  }

  @Transactional
  public void update(Long id, String name) {
    Member member = memberRepository.findOne(id);
    member.setName(name);
  }
}
