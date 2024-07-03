package com.project1.hatin.ban.dto

import jakarta.validation.constraints.NotBlank

class BanResponseDTO {
    data class CreateResponseDTO(
        val startAt: String
    )
}