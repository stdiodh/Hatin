package com.project1.hatin.member.dto

import com.project1.hatin.common.enums.Gender
import com.project1.hatin.member.entity.Member
import java.time.LocalDate

data class MemberRequestDto (
    val id : Long?,
    val userId : String,
    val password : String,
    val nickName : String,
    val birthday : LocalDate,
    val phoneNumber : String,
    val address : String,
    val gender : Gender,
){
    fun toEntity() = Member (
        id = id,
        userId = userId,
        password = password,
        nickName = nickName,
        birthday = birthday,
        phoneNumber = phoneNumber,
        address = address,
        gender = gender
    )
}
