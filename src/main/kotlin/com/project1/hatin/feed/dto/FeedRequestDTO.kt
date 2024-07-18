package com.project1.hatin.feed.dto


import com.project1.hatin.common.annotation.ValidEnum
import com.project1.hatin.common.enums.DayOfWeek
import com.project1.hatin.common.enums.FeedType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


class FeedRequestDTO {
    data class FeedCreateRequestDTO(

        @field:NotBlank(message = "제목을 입력하세요!")
        val title : String,

        @field:NotBlank(message = "내용을 입력하세요!")
        val content : String,

        @field:ValidEnum(enumClass = FeedType::class, message = "올바른 게시글 타입을 선택하세요!")
        val type : FeedType,

        @field:ValidEnum(enumClass = DayOfWeek::class, message = "올바른 요일을 선택하세요!")
        val weekDay: DayOfWeek,
    )

    data class FeedPatchRequestDTO(

        @field:NotBlank(message = "제목을 입력하세요!")
        val title : String,

        @field:NotBlank(message = "내용을 입력하세요!")
        val content : String,

        val like : Int,

        @field:ValidEnum(enumClass = FeedType::class, message = "올바른 게시글 타입을 선택하세요!")
        val type : FeedType,

        @field:ValidEnum(enumClass = DayOfWeek::class, message = "올바른 요일을 선택하세요!")
        val weekDay: DayOfWeek,
    )
}