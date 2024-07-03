package com.project1.hatin.ban.service

import com.project1.hatin.ban.repository.BanFeedRepository
import com.project1.hatin.ban.repository.BanKeywordRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class BanService(
    private val banFeedRepository: BanFeedRepository,
    private val banKeywordRepository: BanKeywordRepository
) {

}