package com.project1.hatin.ban.dto

import jakarta.validation.constraints.NotBlank

class BanKeywordRequestDTO {
    data class CreateKeywordRequestDTO(
        @field:NotBlank(message = "금지할 키워드를 입력해주세요!")
        val keyword: String
    )

    data class PatchKeywordRequestDTO(
        @field:NotBlank(message = "금지할 키워드를 입력해주세요!")
        val keyword: String
    )
}