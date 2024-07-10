package com.project1.hatin.feed.repository

import com.project1.hatin.feed.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<Feed,Long> {
}