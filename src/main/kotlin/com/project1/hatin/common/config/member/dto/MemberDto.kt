package com.project1.hatin.common.config.member.dto

import com.project1.hatin.routine.dto.RoutineRequestDTO.CreateRequestDTO

class MemberDto {
    data class SignUpRoutineRequest(
        val memberRequestDto: MemberRequestDto,
        val createRequestDTOList: List<CreateRequestDTO>
    )
}