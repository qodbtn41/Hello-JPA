package com.example.jpa.dto;

import com.example.jpa.domain.member.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
  private Long id;
  private String name;
  private String teamName;

  public MemberDto(Member member) {
    this(member.getId(), member.getName(), member.getTeam() != null ? member.getTeam().getName() : null);
  }
}
