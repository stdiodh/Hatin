package com.project1.hatin.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.project1.hatin.common.annotation.ValidEnum
import com.project1.hatin.common.enums.Gender
import com.project1.hatin.member.entity.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MemberRequestDto (
    @field:NotBlank(message = "이메일을 입력하세요!")
    @field:Email(message = "올바르지 못한 이메일 형식입니다!")
    @JsonProperty("userId")
    private val _userId : String?,

    @field:NotBlank(message = "비밀번호를 입력하세요!")
    @field:Pattern(regexp ="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()])[a-zA-Z0-9!@#\$%^&*()]{8,20}\$",
        message = "올바르지 못한 비밀번호 형식입니다!")
    @JsonProperty("password")
    private val _password : String?,

    @field:NotBlank(message = "닉네임을 입력하세요!")
    @JsonProperty("nickName")
    private val _nickName : String,

    @field:NotBlank(message = "생년월일을 입력하세요!")
    @field:Pattern(regexp = "^([12]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\$",
        message = "올바르지 못한 날짜 방식입니다!")
    @JsonProperty("birthday")
    private val _birthday : String,

    @field:NotBlank(message = "핸드폰 번호를 입력하세요!")
    @field:Pattern(regexp = "^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$",
        message = "올바르지 못한 핸드폰 번호 입니다!")
    @JsonProperty("phoneNumber")
    private val _phoneNumber : String?,

    @field:NotBlank(message = "주소를 입력하세요!")
    @JsonProperty("address")
    private val _address : String,

    @field:NotBlank(message = "성별을 입력하세요!")
    @field:ValidEnum(enumClass = Gender::class, message = "성별은 MAN 혹은 WOMAN입니다!")
    @JsonProperty("gender")
    private val _gender : String?,
){
    // 비밀번호 encode
    private lateinit var encodePW: String
    val userId : String
        get() = _userId!!
    val password : String
        get() = _password!!
    val nickName : String
        get() = _nickName!!
    val birthday : LocalDate
        get() = _birthday!!.toLocalDate()
    val phoneNumber : String
        get() = _phoneNumber!!
    val address : String
        get() = _address!!
    val gender : Gender
        get() = Gender.valueOf(_gender!!)


    fun encodePW(passwordEncoder: PasswordEncoder) {
        encodePW = passwordEncoder.encode(password)
    }

    private fun String.toLocalDate() : LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

}
