package com.example.jpa.api;

import javax.validation.Valid;

import com.example.jpa.domain.member.Member;
import com.example.jpa.service.MemberService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
  private final MemberService memberService;

  @PostMapping("api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @Data
  static class CreateMemberResponse {
    public CreateMemberResponse(Long id) {
      this.id = id;
    }

    private Long id;
  }
}