package com.myprj.subwaycost.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubwayDistanceCost {

    @Id
    @GeneratedValue
    private Long oneWayCost;

    private Long periodicalCost;
}
