package com.project1.hatin.member.entity

import jakarta.persistence.Entity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class PasswordResetCode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long = 0,

    @Column(nullable = false)
    val code: String,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val expiryData: LocalDateTime
)