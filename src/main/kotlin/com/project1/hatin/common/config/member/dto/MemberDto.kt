package com.project1.hatin.common.config.member.dto

import com.project1.hatin.routine.dto.RoutineRequestDTO.CreateRequestDTO
import jakarta.validation.Valid

class MemberDto {
    data class SignUpRoutineRequest(
        @field:Valid
        val memberRequestDto: MemberRequestDto,
        @field:Valid
        val createRequestDTOList: List<CreateRequestDTO>
    )
}