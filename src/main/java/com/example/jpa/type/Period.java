package com.example.jpa.type;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
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

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Period)) {
      return false;
    }
    Period period = (Period) o;
    return Objects.equals(startDate, period.getStartDate()) && Objects.equals(endDate, period.getEndDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(startDate, endDate);
  }

}
