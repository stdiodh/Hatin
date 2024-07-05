package com.project1.hatin.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginDto (
    @field:NotBlank(message = "아이디를 입력하세요!")
    @field:Email(message = "올바르지 않은 이메일 형식입니다!")
    @JsonProperty("userId")
    private val _userId : String?,

    @field:NotBlank(message = "비밀번호를 입력하세요!")
    @JsonProperty("password")
    private val _password : String?,
){
    val userId : String
        get() = _userId!!
    val password : String
        get() = _password!!
}