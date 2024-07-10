package com.project1.hatin.feed.dto


import com.project1.hatin.common.enums.DayOfWeek
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


class FeedRequestDTO {
    data class FeedCreateRequestDTO(

        @field:NotBlank(message = "제목을 입력하세요!")
        val title : String,

        @field:NotBlank(message = "내용을 입력하세요!")
        val content : String,

        @field:NotNull(message = "게시글 타입을 선택하세요!")
        val type : Boolean,

        val weekDay: DayOfWeek,
    )

    data class FeedPatchRequestDTO(

        @field:NotBlank(message = "제목을 입력하세요!")
        val title : String,

        @field:NotBlank(message = "내용을 입력하세요!")
        val content : String,

        @field:NotNull(message = "게시글 타입을 선택하세요!")
        val type : Boolean,

        val like : Int,

        val weekDay: DayOfWeek,
    )
}