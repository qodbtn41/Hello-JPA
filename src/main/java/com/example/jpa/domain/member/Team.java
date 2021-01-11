package com.example.jpa.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.jpa.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = { "id", "name" })
public class Team extends BaseEntity {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true) // 멤버쪽에 있는 변수명
  private List<Member> members = new ArrayList<Member>();

  public Team(String name) {
    this.name = name;
  }
}
