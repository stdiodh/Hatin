package com.project1.hatin.routine.repository

import com.project1.hatin.routine.entity.Routine
import org.springframework.data.jpa.repository.JpaRepository

interface RoutineRepository : JpaRepository<Routine, Long> {


}