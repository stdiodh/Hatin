package com.project1.hatin.feed.service

import com.project1.hatin.feed.repository.FeedRepository
import org.springframework.stereotype.Service

@Service
class FeedService(
    private val feedRepository: FeedRepository
) {
}