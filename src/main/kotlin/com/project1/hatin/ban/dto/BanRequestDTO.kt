package com.project1.hatin.ban.dto

import com.project1.hatin.common.annotation.ValidEnum
import com.project1.hatin.routine.enums.DayOfWeek
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

class BanRequestDTO {
    data class CreateRequestDTO(
        @field:NotBlank(message = "시작 시간을 선택하세요!")
        val startAt: String,

    )
}