package com.project1.hatin.comment.controller

import com.project1.hatin.comment.dto.CommentRequestDTO
import com.project1.hatin.comment.dto.CommentResponseDTO
import com.project1.hatin.comment.service.CommentService
import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "댓글 Api 컨트롤러", description = "댓글 추가, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/feed")
class CommentController(
    private val commentService: CommentService
) {

    @Operation(summary = "부모 댓글 추가", description = "게시글에 부모 댓글을 추가하는 API입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{feedId}/addParentComment")
    fun addParentCommentToFeed(
        @Parameter(required = true,description = "피드 ID")
        @PathVariable feedId: Long,
        @Valid @RequestBody commentRequestDTO: CommentRequestDTO,
        @AuthenticationPrincipal userInfo: CustomUser
    ): ResponseEntity<BaseResponse<CommentResponseDTO>> {
        val result = commentService.addParentCommentToFeed(
            feedId,
            commentRequestDTO,
            userInfo
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    @Operation(summary = "대댓글 추가", description = "부모 댓글에 대댓글을 추가하는 API입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{feedId}/addChildComment")
    fun addChildCommentToFeed(
        @Parameter(required = true,description = "피드 ID")
        @PathVariable feedId: Long,
        @Valid @RequestBody commentRequestDTO: CommentRequestDTO,
        @AuthenticationPrincipal userInfo: CustomUser
    ): ResponseEntity<BaseResponse<CommentResponseDTO>> {
        val result = commentService.addChildCommentToFeed(
            feedId,
            commentRequestDTO,
            userInfo
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정하는 API입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{feedId}/{commentId}")
    fun patchComment(
        @Parameter(required = true,description = "댓글 ID") @PathVariable commentId: Long,
        @Valid @RequestBody commentRequestDTO: CommentRequestDTO,
        @AuthenticationPrincipal userInfo: CustomUser
    ): ResponseEntity<BaseResponse<CommentResponseDTO>> {
        val result = commentService.patchComment(
            commentId,
            commentRequestDTO,
            userInfo
        )
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하는 API입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{feedId}/{commentId}")
    fun deleteComment(
        @Parameter(required = true,description = "댓글 ID") @PathVariable commentId: Long,
        @AuthenticationPrincipal userInfo: CustomUser
    ): ResponseEntity<BaseResponse<Any>> {
        commentService.deleteComment(commentId, userInfo)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = "댓글이 삭제되었습니다."))
    }
}