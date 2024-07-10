package com.project1.hatin.member.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.dto.TokenInfo
import com.project1.hatin.member.dto.LoginDto
import com.project1.hatin.member.dto.MemberDto.SignUpRoutineRequest
<<<<<<< HEAD
=======
import com.project1.hatin.member.dto.MemberResponseDto
>>>>>>> ad973bd27dde7ad35248e94be9daf776ddbea8b0
import com.project1.hatin.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 Api 컨트롤러", description = "회원생성, 로그인, 전체 조회 Api 명세서입니다.")
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
        val result = memberService.signUp(signUpRoutineRequest.memberRequestDto,signUpRoutineRequest.routineCreateRequestDTOList)
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

    @Operation(summary = "사용자 비밀번호 변경 요청", description = "사용자 비밀번호에 대한 코드를 만드는 API 입니다.")
    @PostMapping("/request-password-reset")
    fun requestPasswordReset(@Parameter(required = true,description = "사용자 이메일") @RequestParam userId: String, model: Model): ResponseEntity<BaseResponse<Model>> {
        val user = memberService.findByUserId(userId)
        if (user == null) {
            val result = model.addAttribute("오류 발생", "이메일이 정확하지 않습니다!")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse(data = result))
        }

        memberService.sendPasswordResetCode(userId)
        val result = model.addAttribute("인증 확인", "비밀번호 인증코드를 보냈습니다!")
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "사용자 비밀번호 변경", description = "사용자 비밀번호 초기화 API 입니다.")
    @PostMapping("/reset-password")
    fun handlePasswordReset(
        @Parameter(required = true,description = "유저 이메일")
        @RequestParam userId: String,
        @Parameter(required = true,description = "인증 코드")
        @RequestParam code: String,
        @Parameter(required = true,description = "새로운 비밀번호")
        @RequestParam newPassword: String,
        model: Model
    ): ResponseEntity<BaseResponse<Model>>  {
        if (!memberService.validateResetCode(userId, code)) {
            val result = model.addAttribute("오류 발생", "인증코드가 정확하지 않습니다!")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse(data = result))
        }

        val user = memberService.findByUserId(userId)
        user!!.password = newPassword
        memberService.passwordSave(user)

        val result = model.addAttribute("변경 완료", "비밀번호가 변경되었습니다.")

        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = result))
    }
}