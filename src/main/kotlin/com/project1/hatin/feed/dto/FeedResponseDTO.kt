package com.project1.hatin.feed.dto

import com.project1.hatin.common.enums.DayOfWeek
import com.project1.hatin.common.enums.FeedType
import java.time.LocalDateTime

class FeedResponseDTO {
    data class FeedCreateResponseDTO(
        var id: Long?,
        val title: String,
        val content: String,
        val type : FeedType,
        val weekDay: DayOfWeek?,
        val createAt: LocalDateTime?,
        val updateAt: LocalDateTime?,
        val like: Int

    )

    data class FeedPatchResponseDTO(
        var id: Long?,
        val title : String,
        val content : String,
        val type : FeedType,
        val weekDay: DayOfWeek?,
        val createAt : LocalDateTime?,
        val updateAt : LocalDateTime?,
        val like: Int
    )

    data class FeedShowResponseDTO(
        var id: Long?,
        val title : String,
        val content : String,
        val type : FeedType,
        val weekDay: DayOfWeek?,
        val createAt : LocalDateTime?,
        val updateAt : LocalDateTime?,
        val nickName : String,
        val like: Int,
    )

    data class FeedSearchResponseDTO(
        var id: Long?,
        val title : String,
        val content : String,
        val type : FeedType,
        val weekDay: DayOfWeek?,
        val createAt : LocalDateTime?,
        val updateAt : LocalDateTime?,
        val like: Int
    )
}