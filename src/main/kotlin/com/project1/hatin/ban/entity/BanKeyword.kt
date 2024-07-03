package com.project1.hatin.ban.entity

import com.project1.hatin.common.config.member.entity.Member
import com.project1.hatin.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
class BanKeyword(
    @Column(nullable = false)
    var keyword: String,

) : BaseEntity()