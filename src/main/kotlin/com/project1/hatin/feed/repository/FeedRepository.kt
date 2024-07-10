package com.project1.hatin.feed.repository

import com.project1.hatin.feed.entity.FeedEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<FeedEntity,Long> {
    fun findAllByType(type: Boolean) : List<FeedEntity>
}