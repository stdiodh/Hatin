package com.project1.hatin.friend.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.friend.dto.FriendRequestDto
import com.project1.hatin.friend.service.FriendService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "친구 추가 Api 컨트롤러", description = "친구 목록 추가, 삭제하는 Api 명세서입니다.")
@Controller
@RequestMapping("/api/friend")
class friendController (
    private val friendService: FriendService
) {
    @Operation(summary = "친구 추가", description = "사용자의 닉네임을 기준으로 친구를 추가하는 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add-friend")
    fun addFriend(
        @Valid @RequestBody friendRequestDto: FriendRequestDto, @AuthenticationPrincipal userInfo: CustomUser
    ): ResponseEntity<BaseResponse<String>> {
        val result = friendService.addFriend(userInfo, friendRequestDto.friendnickName)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = result))
    }

    @Operation(summary = "친구 목록", description = "사용자 친구 목록을 확인하는 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/list")
    fun getFriend(@AuthenticationPrincipal userInfo: CustomUser): ResponseEntity<BaseResponse<List<String>>> {
        val result = friendService.getFriendList(userInfo)

        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = result))
    }





}
