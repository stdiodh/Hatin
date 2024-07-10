package com.project1.hatin.feed.controller

import com.project1.hatin.feed.service.FeedService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "게시글 Api 컨트롤러", description = "게시글 생성, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/feed")
class FeedController(
    private val feedService: FeedService
) {
}