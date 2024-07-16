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

        if (friendRepository.findByMemberAndFriend(targetUser, friend) != null ||
            friendRepository.findByMemberAndFriend(friend, targetUser) != null) {
            throw PostException("이미 친구로 추가된 사용자입니다!")
        }


        val friendRelationship = Friend(member = targetUser, friend = friend)
        friendRepository.save(friendRelationship)

        return "${friendNickname}님을 친구로 추가하였습니다."
    }

    fun getFriendList(userInfo: CustomUser) : List<String> {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?:throw PostException("사용자를 찾을 수 없습니다!")

        //내 목록
        val friends = friendRepository.findAllByMember(targetUser).map { it.friend }
        //나를 친구로 추가한 목록
        val reverseFriends = friendRepository.findAllByFriend(targetUser).map { it.member }
        //중복 제거
        val mutualFriend = (friends + reverseFriends).distinct()

        return mutualFriend.map { it.nickName }
    }

    fun deleteFriend(userInfo: CustomUser, friendNickname: String): String {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?:throw PostException("사용자를 찾을 수 없습니다!")

        val friend = memberRepository.findBynickName(friendNickname)
            ?:throw PostException("친구를 찾을 수 없습니다!")

        if (targetUser.userId == friend.userId) {
            throw PostException("자신을 친구 목록에서 삭제할 수 없습니다!")
        }

        val friendRelationship = friendRepository.findByMemberAndFriend(targetUser, friend)
            ?: friendRepository.findByMemberAndFriend(friend, targetUser)
            ?: throw PostException("{$friend.nickName}님은 친구 목록에 없습니다!")

        friendRepository.delete(friendRelationship)

        return "${friendNickname}님을 친구 목록에서 삭제하였습니다!"
    }

}