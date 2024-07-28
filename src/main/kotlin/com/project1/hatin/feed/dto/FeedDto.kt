package com.project1.hatin.feed.dto

import com.project1.hatin.comment.dto.CommentResponseDTO

class FeedDto {
    data class FeedWithCommentsResponseDTO(
        val feed: FeedResponseDTO.FeedShowResponseDTO,
        val comments: List<CommentResponseDTO>
    )
}