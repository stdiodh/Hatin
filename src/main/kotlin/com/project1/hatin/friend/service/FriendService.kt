package com.project1.hatin.friend.service

import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.friend.entity.Friend
import com.project1.hatin.friend.repository.FriendRepository
import com.project1.hatin.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FriendService (
    private val memberRepository: MemberRepository,
    private val friendRepository: FriendRepository
) {
    fun addFriend(userInfo: CustomUser, friendNickname: String): String {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val friend = memberRepository.findBynickName(friendNickname)
            ?:throw PostException("친구를 찾을 수 없습니다!")

        if (targetUser.userId == friend.userId) {
            throw PostException("자신을 친구로 추가할 수 없습니다!")
        }

        if (friendRepository.findByMemberAndFriend(targetUser, friend) != null) {
            throw PostException("이미 친구로 추가된 사용자입니다!")
        }

        val friendRelationship = Friend(member = targetUser, friend = friend)
        friendRepository.save(friendRelationship)

        return "${friendNickname}님이 ${targetUser.nickName}님의 친구로 추가되었습니다."
    }
}