package com.project1.hatin.friend.repository

import com.project1.hatin.friend.entity.Friend
import com.project1.hatin.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRepository : JpaRepository<Friend, Long>{
    fun findByMemberAndFriend(member: Member, friend: Member): Friend?
}