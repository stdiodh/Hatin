package com.project1.hatin.member.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.dto.TokenInfo
import com.project1.hatin.member.dto.LoginDto
import com.project1.hatin.member.dto.MemberDto.SignUpRoutineRequest
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.dto.MemberResponseDto
import com.project1.hatin.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 Api 컨트롤러", description = "회원생성, 로그인, 조회 Api 명세서입니다.")
@RestController
@RequestMapping("/api/member")
class MemberController (
    private val memberService: MemberService
){
    @Operation(summary = "사용자 정보 회원가입", description = "사용자 회원가입 API 입니다.")
    @PostMapping("/join")
    private fun signUpRoutine(@Parameter(description = "사용자가 회원가입 정보 데이터") @Valid @RequestBody signUpRoutineRequest: SignUpRoutineRequest
    )
            : ResponseEntity<BaseResponse<String>>{
        val result = memberService.signUp(signUpRoutineRequest.memberRequestDto,signUpRoutineRequest.createRequestDTOList)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse(data = result))
    }

    @Operation(summary = "사용자 로그인", description = "사용자 로그인 API 입니다.")
    @PostMapping("/login")
    private fun login(@Valid @RequestBody loginDto: LoginDto)
    : ResponseEntity<BaseResponse<TokenInfo>>{
        val tokenInfo = memberService.login(loginDto)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = tokenInfo))
    }

    @Operation(summary = "사용자 정보 전체 조회", description = "사용자 정보 전체 조회 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/info")
    private fun searchMyInfo()
    : ResponseEntity<BaseResponse<MemberResponseDto>> {
        val id = (SecurityContextHolder.getContext().authentication.principal as CustomUser).id
        val result = memberService.searchMyInfo(id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

}