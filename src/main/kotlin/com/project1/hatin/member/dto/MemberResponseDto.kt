package com.project1.hatin.member.dto

import com.project1.hatin.common.enums.Gender
import java.time.LocalDate

data class MemberResponseDto(
    val userId : String,
    val password : String,
    val nickName : String,
    val birthday : LocalDate,
    val phoneNumber : String,
    val address : String,
    val gender : Gender
)
