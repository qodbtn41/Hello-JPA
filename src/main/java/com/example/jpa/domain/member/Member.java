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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.example.jpa.domain.BaseEntity;
import com.example.jpa.domain.order.Order;
import com.example.jpa.type.Address;
import com.example.jpa.type.Period;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "MEMBER_ID")
  private Long id;

  private String name;

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
}
