package com.project1.hatin.routine.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.project1.hatin.routine.enums.DayOfWeek

class RoutineRequestDTO {
    data class CreateRequestDTO(
        val startAt: String,
        val finishAt: String,
        val name: String,
        val weekDay: DayOfWeek,
    )

    data class PatchRequestDTO(
        val startAt: String,
        val finishAt: String,
        val name: String,
        val weekDay: DayOfWeek,
    )
}