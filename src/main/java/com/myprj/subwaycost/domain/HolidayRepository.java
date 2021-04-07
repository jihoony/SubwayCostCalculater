package com.myprj.subwaycost.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, OffDate> {
    List<Holiday> findAllByOffDateBetween(OffDate startDate, OffDate endDate);
}
