package com.example.jpa.type;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Period {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public boolean isInProgress(LocalDateTime dateTime) {
    if (dateTime.isAfter(startDate) && dateTime.isBefore(endDate))
      return true;
    else
      return false;
  }
}
