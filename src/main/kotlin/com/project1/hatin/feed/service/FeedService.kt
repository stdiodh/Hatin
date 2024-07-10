package com.project1.hatin.feed.service

import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.feed.dto.FeedRequestDTO.FeedPatchRequestDTO
import com.project1.hatin.feed.repository.FeedRepository
import org.springframework.stereotype.Service
import com.project1.hatin.feed.dto.FeedRequestDTO.FeedCreateRequestDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedPatchResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedCreateResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedShowResponseDTO
import com.project1.hatin.feed.entity.FeedEntity
import com.project1.hatin.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull


@Service
@Transactional
class FeedService(
    private val feedRepository: FeedRepository,
    private val memberRepository: MemberRepository
) {
    fun showAllFeed(type: String):List<FeedShowResponseDTO> {
        val findType: Boolean = if (type == "normal") true else false

        val targetList = feedRepository.findAllByType(findType)

        val result = mutableListOf<FeedShowResponseDTO>()

        for (target in targetList) {
            val dto = FeedShowResponseDTO(
                id = target.id,
                title = target.title,
                content = target.content,
                type = target.type,
                weekDay = target.weekDay,
                createAt = target.createdAt,
                updateAt = target.updatedAt,
                nickName = target.member.nickName,
                like = target.likeCount
            )
            result.add(dto)
        }
        return result
    }

    fun showFeed(id:Long): FeedShowResponseDTO {
        val target = feedRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 게시글입니다.")

        val result = FeedShowResponseDTO(
            id = target.id,
            title = target.title,
            content = target.content,
            type = target.type,
            weekDay = target.weekDay,
            createAt = target.createdAt,
            updateAt = target.updatedAt,
            nickName = target.member.nickName,
            like = target.likeCount
        )

        return result
    }

    fun createFeed(feedCreateRequestDTO: FeedCreateRequestDTO, userInfo: CustomUser) : FeedCreateResponseDTO {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val feedEntity = FeedEntity(
            title = feedCreateRequestDTO.title,
            content = feedCreateRequestDTO.content,
            likeCount = 0,
            type = feedCreateRequestDTO.type,
            weekDay = if (feedCreateRequestDTO.type) {
                null
            } else {
                feedCreateRequestDTO.weekDay
            },
            member = targetUser
        )

        val result = feedRepository.save(feedEntity)

        targetUser.feedEntityList?.add(result)
        memberRepository.save(targetUser)

        return FeedCreateResponseDTO(
            id = result.id,
            title = result.title,
            content = result.content,
            type = result.type,
            weekDay = result.weekDay,
            createAt = result.createdAt,
            updateAt = result.updatedAt,
            like = result.likeCount
        )
    }

    fun patchFeed(id: Long, feedPatchRequestDTO: FeedPatchRequestDTO, userInfo: CustomUser):FeedPatchResponseDTO {
        var target : FeedEntity = feedRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 게시글 ID 입니다.")

        target.title = feedPatchRequestDTO.title
        target.content = feedPatchRequestDTO.content
        target.type = feedPatchRequestDTO.type
        target.weekDay = feedPatchRequestDTO.weekDay
        target.likeCount = feedPatchRequestDTO.like


        target = feedRepository.save(target)

        return FeedPatchResponseDTO(
            id = target.id,
            title = target.title,
            content = target.content,
            type = target.type,
            weekDay = target.weekDay,
            createAt = target.createdAt,
            updateAt = target.updatedAt,
            like = target.likeCount
        )
    }

    fun deleteFeed(id: Long){
        feedRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 루틴 ID 입니다.")
        feedRepository.deleteById(id)
    }
}