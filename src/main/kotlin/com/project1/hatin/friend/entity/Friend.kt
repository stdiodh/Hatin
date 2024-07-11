package com.project1.hatin.friend.entity

import com.project1.hatin.member.entity.Member
import jakarta.persistence.*

@Entity
@Table
data class Friend (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    val friend: Member,
)