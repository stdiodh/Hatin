package com.project1.hatin.friend.repository

import com.project1.hatin.friend.entity.Friend
import com.project1.hatin.friend.entity.FriendId
import com.project1.hatin.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRepository : JpaRepository<Friend, FriendId>{
    fun findByMemberAndFriend(member: Member, friend: Member): Friend?
    fun findAllByMember(member: Member): List<Friend>
    fun findAllByFriend(friend: Member): List<Friend>
}