package com.project1.hatin.routine.dto

import com.project1.hatin.common.annotation.ValidEnum
import com.project1.hatin.common.enums.DayOfWeek
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

class RoutineRequestDTO {
    data class RoutineCreateRequestDTO(
        @field:NotBlank(message = "시작 시간을 선택하세요!")
        @field:Pattern(
            regexp = "^(?:[01][0-9]|2[0-3]):(?:00|10|20|30|40|50)\$",
            message = "올바르지 못한 시간 형식입니다!"
        )
        val startAt: String,
        @field:NotBlank(message = "종료 시간을 선택하세요!")
        @field:Pattern(
            regexp = "^(?:[01][0-9]|2[0-3]):(?:00|10|20|30|40|50)\$",
            message = "올바르지 못한 시간 형식입니다!"
        )
        val finishAt: String,
        @field:NotBlank(message = "루틴의 이름을 입력하세요!")
        val name: String,
        @field:ValidEnum(enumClass = DayOfWeek::class, message = "올바른 요일을 선택하세요!")
        val weekDay: DayOfWeek,

        val memo: String,
    )

    data class RoutinePatchRequestDTO(
        @field:NotBlank(message = "시작 시간을 선택하세요!")
        @field:Pattern(
            regexp = "^(?:[01][0-9]|2[0-3]):(?:00|10|20|30|40|50)\$",
            message = "올바르지 못한 시간 형식입니다!"
        )
        val startAt: String,
        @field:NotBlank(message = "종료 시간을 선택하세요!")
        @field:Pattern(
            regexp = "^(?:[01][0-9]|2[0-3]):(?:00|10|20|30|40|50)\$",
            message = "올바르지 못한 시간 형식입니다!"
        )
        val finishAt: String,
        @field:NotBlank(message = "루틴의 이름을 입력하세요!")
        val name: String,
        @field:ValidEnum(enumClass = DayOfWeek::class, message = "올바른 요일을 선택하세요!")
        val weekDay: DayOfWeek,

        val memo: String,

        val isFinish: Boolean,
    )

    data class RoutineDeleteRequestDTO(
        val id: Long
    )
}