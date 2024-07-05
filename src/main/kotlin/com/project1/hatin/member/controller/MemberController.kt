package com.project1.hatin.member.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.TokenInfo
import com.project1.hatin.member.dto.LoginDto
import com.project1.hatin.member.dto.MemberDto.SignUpRoutineRequest
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.service.MemberService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 Api 컨트롤러", description = "회원생성, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/member")
class MemberController (
    private val memberService: MemberService
){
    //회원가입 - 루틴 저장 api
    @PostMapping("/join")
    private fun signUpRoutine(@Parameter(description = "사용자가 회원가입 정보 데이터") @Valid @RequestBody signUpRoutineRequest: SignUpRoutineRequest
    )
            : ResponseEntity<BaseResponse<String>>{
        val result = memberService.signUp(signUpRoutineRequest.memberRequestDto,signUpRoutineRequest.createRequestDTOList)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse(data = result))
    }

    //로그인 api
    @PostMapping("/login")
    private fun login(@Valid @RequestBody loginDto: LoginDto)
    : ResponseEntity<BaseResponse<TokenInfo>>{
        val tokenInfo = memberService.login(loginDto)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = tokenInfo))
    }
}