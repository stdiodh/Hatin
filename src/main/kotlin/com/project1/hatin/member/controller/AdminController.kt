package com.project1.hatin.member.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.service.AdminService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "관리자 Api 컨트롤러", description = "관리자의 회원관리 Api 명세서입니다.")
@RestController
@RequestMapping("/api/member/admin")
class AdminController(
    private val adminService: AdminService
) {
    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입 API 입니다.")
    @PostMapping("/create")
    fun createAdmin(@Valid @RequestBody adminRequestDto: MemberRequestDto): ResponseEntity<BaseResponse<String>> {
        val result = adminService.createAdmin(adminRequestDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    @Operation(summary = "관리자, 유저 삭제", description = "관리자의 유저 삭제 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    fun deleteUser(@Parameter(description = "사용자 고유 아이디") @PathVariable id: Long): ResponseEntity<BaseResponse<String>> {
        val deleteNickName = adminService.deleteUserById(id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = "해당 $deleteNickName 유저가 삭제되었습니다."))
    }
}