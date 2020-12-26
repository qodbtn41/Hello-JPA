package com.example.jpa.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.jpa.domain.member.Member;
import com.example.jpa.service.MemberService;
import com.example.jpa.type.Address;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("/new")
  public String create(@Valid MemberForm form, BindingResult result) {
    if (result.hasErrors()) {
      return "members/createMemberForm";
    }
    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

    Member member = new Member();
    member.setName(form.getName());
    member.setHomeAddress(address);

    memberService.join(member);
    return "redirect:/";
  }

  @GetMapping
  public String list(Model model) {
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return "members/memberList";
  }
}
