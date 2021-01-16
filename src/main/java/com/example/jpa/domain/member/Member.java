package com.example.jpa.domain.member;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.example.jpa.domain.BaseEntity;
import com.example.jpa.domain.order.Order;
import com.example.jpa.type.Address;
import com.example.jpa.type.Period;

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
@NamedQuery(name = "Member.findByAge", query = "select m from Member m where m.age = :age")
public class Member extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  private String name;

  private int age;

  @Embedded
  private Period period;

  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "HOME_CITY")),
      @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "HOME_ZIPCODE")) })
  private Address homeAddress;

  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
      @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE")) })
  private Address workAddress;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  @OneToMany(mappedBy = "member")
  private List<Order> orders;

  public Member(String name) {
    this.name = name;
  }

  public Member(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public Member(String name, int age, Team team) {
    this.name = name;
    this.age = age;
    changeTeam(team);
  }

  public void changeTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
  }

}
