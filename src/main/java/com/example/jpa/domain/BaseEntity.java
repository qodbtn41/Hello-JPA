package com.example.jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
  private String createdBy;
  private LocalDateTime createdTime;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedTime;
}
