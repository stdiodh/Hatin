package com.project1.hatin.ban.repository

import com.project1.hatin.ban.entity.BanFeed
import org.springframework.data.jpa.repository.JpaRepository

interface BanFeedRepository : JpaRepository<BanFeed,Long> {
}