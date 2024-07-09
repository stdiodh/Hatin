package com.project1.hatin.member.repository

import com.project1.hatin.member.entity.PasswordResetCode
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordResetCodeRepository : JpaRepository<PasswordResetCode, Long> {
    fun findByCodeAndUserId(code: String, userId: String): PasswordResetCode?
}