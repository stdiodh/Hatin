package com.project1.hatin.member.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.TokenInfo
import com.project1.hatin.member.dto.LoginDto
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
@Tag(name = "회원 Api 컨트롤러", description = "회원 가입, 로그인, Api 명세서 입니다.")

@RestController
@RequestMapping("/api/member")
class MemberController (
    private val memberService: MemberService
){
    //회원가입 api
    @Operation(summary = "사용자 회원가입", description = "사용자 회원가입")
    @PostMapping("/join")
    private fun signUp(@Valid @RequestBody memberRequestDto: MemberRequestDto)
    : ResponseEntity<BaseResponse<String>>{
        val result = memberService.signUp(memberRequestDto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse(data = result))
    }

    //로그인 api
    @Operation(summary = "사용자 로그인", description = "사용자 로그인")
    @PostMapping("/login")
    private fun login(@Valid @RequestBody loginDto: LoginDto)
    : ResponseEntity<BaseResponse<TokenInfo>>{
        val tokenInfo = memberService.login(loginDto)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = tokenInfo))
    }
}