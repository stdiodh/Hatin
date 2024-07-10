package com.project1.hatin.member.dto

import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutineCreateRequestDTO
import jakarta.validation.Valid

class MemberDto {
    data class SignUpRoutineRequest(
        @field:Valid
        val memberRequestDto: MemberRequestDto,
        @field:Valid
        val routineCreateRequestDTOList: List<RoutineCreateRequestDTO>
    )
}