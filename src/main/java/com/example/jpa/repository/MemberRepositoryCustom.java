package com.example.jpa.repository;

import java.util.List;

import com.example.jpa.domain.member.Member;

public interface MemberRepositoryCustom {
  List<Member> findMemberCustom();
}
