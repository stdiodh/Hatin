package com.project1.hatin.routine.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.project1.hatin.routine.enums.DayOfWeek

class RoutineResponseDTO {
    data class CreateResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,
    )

    data class PatchResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,
    )

    data class ShowResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,
    )
}