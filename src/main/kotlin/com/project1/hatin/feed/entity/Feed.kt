package com.project1.hatin.feed.entity

import com.project1.hatin.common.entity.BaseEntity
import com.project1.hatin.common.enums.DayOfWeek
import com.project1.hatin.member.entity.Member
import jakarta.persistence.*

@Entity
class Feed(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var likeCount: Int,

    @Column(nullable = false)
    var type: Boolean,

    @Column
    var weekDay: DayOfWeek?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_feed_member_id"))
    val member: Member,

    ) : BaseEntity()