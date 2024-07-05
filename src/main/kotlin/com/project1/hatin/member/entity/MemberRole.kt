package com.project1.hatin.member.entity

import com.project1.hatin.common.entity.BaseEntity
import com.project1.hatin.common.enums.Role
import jakarta.persistence.Entity
import jakarta.persistence.*

@Entity
class MemberRole(

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role : Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member: Member,

    ) : BaseEntity()