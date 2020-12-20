package com.example.jpa.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.jpa.entity.Member;
import com.example.jpa.service.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/member")
public class MemberController {
  private final MemberService service;

  public MemberController(MemberService service) {
    this.service = service;
  }

  @GetMapping
  public List<Member> getMember() {
    return this.service.getMember();
  }

}
