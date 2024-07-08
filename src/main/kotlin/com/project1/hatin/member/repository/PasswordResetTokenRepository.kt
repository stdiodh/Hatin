package com.project1.hatin.member.repository

import com.project1.hatin.member.entity.PasswordResetToken
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordResetTokenRepository : JpaRepository<PasswordResetToken, Long> {
    fun findByToken(token: String): PasswordResetToken?
}