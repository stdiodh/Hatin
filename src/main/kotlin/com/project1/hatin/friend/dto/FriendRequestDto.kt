package com.project1.hatin.friend.dto

import jakarta.validation.constraints.NotBlank

data class FriendRequestDto (
    @field:NotBlank(message = "친구의 닉네임을 입력해 주세요!")
    val friendnickName: String,
)