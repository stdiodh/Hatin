package com.project1.hatin.comment.repository

import com.project1.hatin.comment.entity.CommentEntity
import com.project1.hatin.feed.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long> {
    fun findByFeed(feed: Feed): List<CommentEntity>
}