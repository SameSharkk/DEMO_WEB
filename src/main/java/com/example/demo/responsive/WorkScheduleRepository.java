package com.example.demo.responsive;

import com.example.demo.entity.InterWorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleRepository extends JpaRepository<InterWorkSchedule, Long> {
}
