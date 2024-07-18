package com.project1.hatin.comment.dto

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class CommentRequestDTO(
    @field:NotBlank(message = "댓글을 입력해 주세요!")
    val content: String,
    val parentCommentId: Long? = null
)

data class CommentResponseDTO(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val authorId: Long,
    val authorNickname: String,
    val parentCommentId: Long?
)