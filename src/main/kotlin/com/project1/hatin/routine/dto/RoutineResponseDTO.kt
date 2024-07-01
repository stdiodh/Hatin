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

        var memo: String,

        var isFinish: Boolean,
    )

    data class PatchResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,

        var memo: String,

        var isFinish: Boolean,
    )

    data class ShowResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,

        var memo: String,

        var isFinish: Boolean,
    )
}