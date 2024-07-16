package com.project1.hatin.friend.entity

import com.project1.hatin.member.entity.Member
import jakarta.persistence.*
import java.io.Serializable

@Entity
@IdClass(FriendId::class)
data class Friend (
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    val friend: Member,
)

data class FriendId(
    val member: Long = 0,
    val friend: Long = 0
) : Serializable