package com.project1.hatin.feed.entity

import com.project1.hatin.common.entity.BaseEntity
import com.project1.hatin.routine.enums.DayOfWeek
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Feed(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @Column
    var like: Int,

    @Column(nullable = false)
    var type: Boolean,

) : BaseEntity()