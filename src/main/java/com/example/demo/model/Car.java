package com.example.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    private String licencePlate;

    private LocalTime inTime;

    private LocalTime outTime;

    @JsonSerialize
    public int getSpeed(int distance) {
        long seconds = Duration.between(inTime, outTime).getSeconds();
        long speed = (distance * 3600L / seconds);
        return (int) speed;
    }

}
