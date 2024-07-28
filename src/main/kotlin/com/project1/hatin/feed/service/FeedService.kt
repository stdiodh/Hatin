package com.project1.hatin.feed.service

import com.project1.hatin.comment.dto.CommentResponseDTO
import com.project1.hatin.comment.repository.CommentRepository
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.enums.DayOfWeek
import com.project1.hatin.common.enums.FeedType
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.feed.dto.FeedDto
import com.project1.hatin.feed.dto.FeedRequestDTO.FeedPatchRequestDTO
import com.project1.hatin.feed.repository.FeedRepository
import org.springframework.stereotype.Service
import com.project1.hatin.feed.dto.FeedRequestDTO.FeedCreateRequestDTO
import com.project1.hatin.feed.dto.FeedResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedSearchResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedPatchResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedCreateResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedShowResponseDTO
import com.project1.hatin.feed.entity.Feed
import com.project1.hatin.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull


@Service
@Transactional
class FeedService(
    private val feedRepository: FeedRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository
) {
    fun showAllFeed(type: FeedType):List<FeedShowResponseDTO> {

        val feedType: Boolean = when (type) {
            FeedType.NORMAL -> true
            FeedType.ROUTINE -> false
        }

        val targetList = feedRepository.findAllByType(feedType)

        val result = mutableListOf<FeedShowResponseDTO>()

        for (target in targetList) {
            val dto = FeedShowResponseDTO(
                id = target.id,
                title = target.title,
                content = target.content,
                type = when (target.type) {
                    true -> FeedType.NORMAL
                    false -> FeedType.ROUTINE
                },
                weekDay = target.weekDay,
                createAt = target.createdAt,
                updateAt = target.updatedAt,
                nickName = target.member.nickName,
                like = target.likeCount,
            )
            result.add(dto)
        }
        return result
    }

    fun showFeedWithComments(id: Long): FeedDto.FeedWithCommentsResponseDTO {
        val feed = feedRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 게시글입니다.")

        val comments = commentRepository.findByFeed(feed).map { comment ->
            CommentResponseDTO(
                id = comment.id!!,
                content = comment.content,
                createdAt = comment.createdAt!!,
                authorId = comment.author.id!!,
                authorNickname = comment.author.nickName,
                parentCommentId = comment.parentComment?.id
            )
        }

        val feedResponse = FeedShowResponseDTO(
            id = feed.id,
            title = feed.title,
            content = feed.content,
            type = if (feed.type) FeedType.NORMAL else FeedType.ROUTINE,
            weekDay = feed.weekDay,
            createAt = feed.createdAt,
            updateAt = feed.updatedAt,
            nickName = feed.member.nickName,
            like = feed.likeCount
        )

        return FeedDto.FeedWithCommentsResponseDTO(
            feed = feedResponse,
            comments = comments
        )
    }

    fun searchFeed(
        type: FeedType,
        keyword: String?,
        weekDay: DayOfWeek?,
        nameDirection: String,
        dateDirection: String
    ): List<FeedSearchResponseDTO> {

        val feedType: Boolean = when (type) {
            FeedType.NORMAL -> true
            FeedType.ROUTINE -> false
        }

        val feeds = if (keyword == null) {
            if(weekDay != null){
                feedRepository.findAllByWeekDayAndType(weekDay,feedType)
            }
            else{
                feedRepository.findAllByType(feedType)
            }
        } else if (weekDay != null) {
            feedRepository.findByTitleContainingIgnoreCaseAndWeekDayAndType(keyword, weekDay, feedType)
        } else {
            feedRepository.findByTitleContainingIgnoreCaseAndType(keyword, feedType)
        }

        val nameComparator = Comparator<Feed> { f1, f2 ->
            when {
                nameDirection.equals("asc", true) -> f1.title.compareTo(f2.title)
                nameDirection.equals("desc", true) -> f2.title.compareTo(f1.title)
                else -> 0
            }
        }

        val dateComparator = Comparator<Feed> { f1, f2 ->
            when {
                dateDirection.equals("asc", true) -> f1.createdAt?.compareTo(f2.createdAt) ?: 0
                dateDirection.equals("desc", true) -> f2.createdAt?.compareTo(f1.createdAt) ?: 0
                else -> 0
            }
        }

        val sortedFeeds = feeds.sortedWith(nameComparator.thenComparing(dateComparator))

        return sortedFeeds.map { feed ->
            FeedSearchResponseDTO(
                id = feed.id,
                title = feed.title,
                content = feed.content,
                type = when (feed.type) {
                    true -> FeedType.NORMAL
                    false -> FeedType.ROUTINE
                },
                weekDay = feed.weekDay,
                createAt = feed.createdAt,
                updateAt = feed.updatedAt,
                like = feed.likeCount
            )
        }
    }

    fun createFeed(feedCreateRequestDTO: FeedCreateRequestDTO, userInfo: CustomUser) : FeedCreateResponseDTO {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val feedType: Boolean = when (feedCreateRequestDTO.type) {
            FeedType.NORMAL -> true
            FeedType.ROUTINE -> false
        }

        val feed = Feed(
            title = feedCreateRequestDTO.title,
            content = feedCreateRequestDTO.content,
            likeCount = 0,
            type = feedType,
            weekDay = if (feedType) {
                null
            } else {
                feedCreateRequestDTO.weekDay
            },
            member = targetUser
        )

        val result = feedRepository.save(feed)

        targetUser.feedList?.add(result)
        memberRepository.save(targetUser)

        return FeedCreateResponseDTO(
            id = result.id,
            title = result.title,
            content = result.content,
            type = when (result.type) {
                true -> FeedType.NORMAL
                false -> FeedType.ROUTINE
            },
            weekDay = result.weekDay,
            createAt = result.createdAt,
            updateAt = result.updatedAt,
            like = result.likeCount
        )
    }

    fun patchFeed(id: Long, feedPatchRequestDTO: FeedPatchRequestDTO, userInfo: CustomUser):FeedPatchResponseDTO {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자 입니다.")

        var target = targetUser.feedList?.find { it.id == id }

        if (target == null) throw PostException(msg = "사용자가 작성한 게시글 ID가 아닙니다.")

        val feedType: Boolean = when (feedPatchRequestDTO.type) {
            FeedType.NORMAL -> true
            FeedType.ROUTINE -> false
        }

        target.title = feedPatchRequestDTO.title
        target.content = feedPatchRequestDTO.content
        target.type = feedType
        target.weekDay = feedPatchRequestDTO.weekDay
        target.likeCount = feedPatchRequestDTO.like


        target = feedRepository.save(target)

        return FeedPatchResponseDTO(
            id = target.id,
            title = target.title,
            content = target.content,
            type = when (target.type) {
                true -> FeedType.NORMAL
                false -> FeedType.ROUTINE
            },
            weekDay = target.weekDay,
            createAt = target.createdAt,
            updateAt = target.updatedAt,
            like = target.likeCount
        )
    }

    fun deleteFeed(id: Long, userInfo: CustomUser){
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자 입니다.")

        val foundFeed = targetUser.feedList?.find { it.id == id }

        if (foundFeed == null) throw PostException(msg = "사용자가 작성한 게시글 ID가 아닙니다.")

        feedRepository.deleteById(id)
    }

    fun getRecommendedFeeds(): List<FeedShowResponseDTO> {
        val feeds = feedRepository.findTop10ByOrderByLikeCountDesc()
        return feeds.map { feed ->
            FeedShowResponseDTO(
                id = feed.id!!,
                title = feed.title,
                content = feed.content,
                type = if (feed.type) FeedType.NORMAL else FeedType.ROUTINE,
                weekDay = feed.weekDay,
                createAt = feed.createdAt,
                updateAt = feed.updatedAt,
                nickName = feed.member.nickName,
                like = feed.likeCount
            )
        }
    }
}