package com.project1.hatin.ban.repository

import com.project1.hatin.ban.entity.BanKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface BanKeywordRepository : JpaRepository<BanKeyword,Long> {
}