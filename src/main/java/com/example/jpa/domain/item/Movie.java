package com.example.jpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Movie")
public class Movie extends Item {
  private String director;
  private String actor;
}
