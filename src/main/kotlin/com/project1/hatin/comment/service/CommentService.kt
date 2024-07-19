package com.project1.hatin.comment.service

import com.project1.hatin.comment.dto.CommentRequestDTO
import com.project1.hatin.comment.dto.CommentResponseDTO
import com.project1.hatin.comment.entity.CommentEntity
import com.project1.hatin.comment.repository.CommentRepository
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.feed.repository.FeedRepository
import com.project1.hatin.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun addParentCommentToFeed(feedId: Long, commentRequestDTO: CommentRequestDTO, userInfo: CustomUser): CommentResponseDTO {
        val feed = feedRepository.findByIdOrNull(feedId)
            ?: throw PostException("존재하지 않는 게시글입니다.")
        val author = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException("존재하지 않는 사용자입니다.")

        val comment = CommentEntity(
            content = commentRequestDTO.content,
            feed = feed,
            author = author,
            parentComment = null // 부모 댓글이므로 null 설정
        )

        val savedComment = commentRepository.save(comment)

        return CommentResponseDTO(
            id = savedComment.id!!,
            content = savedComment.content,
            createdAt = savedComment.createdAt!!,
            authorId = savedComment.author.id!!,
            authorNickname = savedComment.author.nickName,
            parentCommentId = savedComment.parentComment?.id
        )
    }

    @Transactional
    fun addChildCommentToFeed(feedId: Long, commentRequestDTO: CommentRequestDTO, userInfo: CustomUser): CommentResponseDTO {
        val feed = feedRepository.findByIdOrNull(feedId)
            ?: throw PostException("존재하지 않는 게시글입니다.")
        val author = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException("존재하지 않는 사용자입니다.")

        val parentComment = commentRequestDTO.parentCommentId?.let {
            commentRepository.findByIdOrNull(it)
                ?: throw PostException("존재하지 않는 댓글입니다.")
        } ?: throw PostException("부모 댓글 ID가 필요합니다.")

        val comment = CommentEntity(
            content = commentRequestDTO.content,
            feed = feed,
            author = author,
            parentComment = parentComment // 부모 댓글 설정
        )

        val savedComment = commentRepository.save(comment)

        return CommentResponseDTO(
            id = savedComment.id!!,
            content = savedComment.content,
            createdAt = savedComment.createdAt!!,
            authorId = savedComment.author.id!!,
            authorNickname = savedComment.author.nickName,
            parentCommentId = savedComment.parentComment?.id
        )
    }


    @Transactional
    fun patchComment(commentId: Long, commentRequestDTO: CommentRequestDTO, userInfo: CustomUser): CommentResponseDTO {
        val author = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException("존재하지 않는 사용자입니다.")

        val targetComment = commentRepository.findByIdOrNull(commentId)
            ?: throw PostException("존재하지 않는 댓글입니다.")

        if (targetComment.author.id != author.id) {
            throw PostException("사용자가 작성한 댓글 ID가 아닙니다.")
        }

        targetComment.content = commentRequestDTO.content

        val updatedComment = commentRepository.save(targetComment)

        return CommentResponseDTO(
            id = updatedComment.id!!,
            content = updatedComment.content,
            createdAt = updatedComment.createdAt!!,
            authorId = updatedComment.author.id!!,
            authorNickname = updatedComment.author.nickName,
            parentCommentId = updatedComment.parentComment?.id
        )
    }

    @Transactional
    fun deleteComment(commentId: Long, userInfo: CustomUser) {
        val author = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException("존재하지 않는 사용자입니다.")

        val targetComment = commentRepository.findByIdOrNull(commentId)
            ?: throw PostException("존재하지 않는 댓글입니다.")

        if (targetComment.author.id != author.id) {
            throw PostException("사용자가 작성한 댓글 ID가 아닙니다.")
        }

        commentRepository.deleteById(commentId)
    }

    @Transactional
    fun getCommentsForFeed(feedId: Long): List<CommentResponseDTO> {
        val feed = feedRepository.findByIdOrNull(feedId)
            ?: throw PostException("존재하지 않는 게시글입니다.")

        return commentRepository.findByFeed(feed).map { comment ->
            CommentResponseDTO(
                id = comment.id!!,
                content = comment.content,
                createdAt = comment.createdAt!!,
                authorId = comment.author.id!!,
                authorNickname = comment.author.nickName,
                parentCommentId = comment.parentComment?.id
            )
        }
    }
}