package com.project1.hatin.routine.entity

import com.project1.hatin.common.entity.BaseEntity
import com.project1.hatin.common.config.member.entity.Member
import com.project1.hatin.routine.enums.DayOfWeek
import jakarta.persistence.*

@Entity
class Routine(
    @Column(nullable = false)
    var startAt: String,

    @Column(nullable = false)
    var finishAt: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var weekDay: DayOfWeek,

    @Column(nullable = false)
    var isFinish: Boolean,

    @Column
    var memo: String,

    // 단방향 관계이기 때문에 루틴을 저장한 후 회원정보에 저장한 루틴을 설정하고 저장해야한다.

) : BaseEntity()