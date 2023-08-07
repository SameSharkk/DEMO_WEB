package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "inter_work_schedule")
public class InterWorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inter_id")
    private Inter inter;

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public InterWorkSchedule(Inter inter, LocalDate workDate, LocalTime startTime, LocalTime endTime) {
        this.inter = inter;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}