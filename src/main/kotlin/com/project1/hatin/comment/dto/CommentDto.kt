package com.project1.hatin.comment.dto

import java.time.LocalDateTime

data class CommentRequestDTO(
    val content: String,
    val parentCommentId: Long? = null
)

data class CommentResponseDTO(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val authorId: Long,
    val parentCommentId: Long?
)