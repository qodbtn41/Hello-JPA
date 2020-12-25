package com.example.jpa.type;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Embeddable
public class Address {
  private String city;
  private String street;
  private String zipcode;
}
