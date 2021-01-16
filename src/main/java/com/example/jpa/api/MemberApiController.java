package com.example.jpa.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.example.jpa.domain.member.Member;
import com.example.jpa.dto.MemberDto;
import com.example.jpa.repository.MemberRepository;
import com.example.jpa.service.MemberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
  private final MemberService memberService;
  private final MemberRepository memberRepository;

  @GetMapping("api/v1/members")
  public Result<MemberDto> getMemberV2() {
    List<Member> members = memberService.findMembers();
    List<MemberDto> collect = members.stream().map(member -> new MemberDto(member)
        .collect(Collectors.toList());

    return new Result<MemberDto>(collect);
  }

  @PostMapping("api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PostMapping("api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
    Member member = new Member(request.getName());
    member.setName(request.getName());

    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PutMapping("api/v2/members/{id}")
  public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
      @RequestBody @Valid UpdateMemberRequest request) {

    memberService.update(id, request.getName());
    Member findMember = memberService.findMember(id);
    return new UpdateMemberResponse(findMember.getId(), findMember.getName());
  }

  @Data
  @AllArgsConstructor
  static class Result<T> {
    private List<T> data;
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {
    private Long id;
    private String name;
  }

  @Data
  static class UpdateMemberRequest {
    private String name;
  }

  @Data
  static class CreateMemberRequest {
    @NotEmpty
    private String name;
  }

  @Data
  static class CreateMemberResponse {
    public CreateMemberResponse(Long id) {
      this.id = id;
    }

    private Long id;
  }

  @GetMapping("/api/members/v1/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberService.findMember(id);
    return member.getName();
  }

  @GetMapping("/api/members/v2/{id}")
  public String findMember(@PathVariable("id") Member member) {
    return member.getName();
  }

  @GetMapping("/api/members")
  public Page<MemberDto> list(Pageable pageable) {
    Page<Member> page = memberRepository.findAll(pageable);
    return page.map(MemberDto::new);
  }

  @PostConstruct
  public void init() {
    for (int i = 0; i < 100; i++) {
      memberRepository.save(new Member("member" + (i + 1)));
    }
  }
}
