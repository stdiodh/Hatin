package com.project1.hatin.ban.entity

import com.project1.hatin.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.springframework.data.jpa.repository.JpaRepository

@Entity
class BanKeyword (
    @Column(nullable = false)
    var keyword: String
) : BaseEntity()