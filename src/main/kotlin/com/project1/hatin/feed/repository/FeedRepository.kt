package com.project1.hatin.feed.repository

import com.project1.hatin.common.enums.DayOfWeek
import com.project1.hatin.feed.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<Feed,Long> {
    fun findAllByType(type: Boolean): List<Feed>
    fun findByTitleContainingIgnoreCaseAndType(keyword: String, type: Boolean): List<Feed>
    fun findByTitleContainingIgnoreCaseAndWeekDayAndType(keyword: String, weekDay: DayOfWeek, type: Boolean): List<Feed>
    fun findAllByWeekDayAndType(weekDay: DayOfWeek, type: Boolean): List<Feed>

    fun findTop10ByOrderByLikeCountDesc(): List<Feed>
}