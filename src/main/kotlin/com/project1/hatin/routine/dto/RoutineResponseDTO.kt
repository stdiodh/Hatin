package com.project1.hatin.routine.dto

import com.project1.hatin.common.enums.DayOfWeek

class RoutineResponseDTO {
    data class RoutineCreateResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,

        var memo: String,

        var isFinish: Boolean,
    )

    data class RoutinePatchResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,

        var memo: String,

        var isFinish: Boolean,
    )

    data class RoutineShowResponseDTO(
        var id: Long?,

        var startAt: String,

        var finishAt: String,

        var name: String,

        var weekDay: DayOfWeek,

        var memo: String,

        var isFinish: Boolean,
    )
}