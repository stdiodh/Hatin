package com.project1.hatin.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class findUserIdRequest(
    @field:NotBlank(message = "이름은 필수 입력 항목입니다.")
    @JsonProperty("nickName")
    private val _nickName : String,

    @field:NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @field:Pattern(regexp = "^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$",
        message = "올바르지 못한 핸드폰 번호 입니다!")
    @JsonProperty("phoneNumber")
    private val _phoneNumber: String
) {
    val nickName : String
        get() = _nickName
    val phoneNumber : String
        get() = _phoneNumber
}