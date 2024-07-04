package com.project1.hatin.ban.dto

class BanKeywordResponseDTO {
    data class CreateKeywordResponseDTO(
        var id: Long?,
        var keyword: String
    )

    data class ShowKeywordResponseDTO(
        var id: Long?,
        var keyword: String
    )

    data class PatchKeywordResponseDTO(
        var id: Long?,
        var keyword: String
    )
}