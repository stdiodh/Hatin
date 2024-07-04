package com.project1.hatin.ban.service

import com.project1.hatin.ban.repository.BanFeedRepository
import com.project1.hatin.ban.repository.BanKeywordRepository
import com.project1.hatin.ban.dto.BanKeywordRequestDTO.CreateKeywordRequestDTO
import com.project1.hatin.ban.dto.BanKeywordRequestDTO.PatchKeywordRequestDTO
import com.project1.hatin.ban.dto.BanKeywordResponseDTO.CreateKeywordResponseDTO
import com.project1.hatin.ban.dto.BanKeywordResponseDTO.ShowKeywordResponseDTO
import com.project1.hatin.ban.dto.BanKeywordResponseDTO.PatchKeywordResponseDTO
import com.project1.hatin.ban.entity.BanKeyword
import com.project1.hatin.common.config.member.repository.MemberRepository
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.exception.PostException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class BanService(
    private val banFeedRepository: BanFeedRepository,
    private val banKeywordRepository: BanKeywordRepository,
    private val memberRepository: MemberRepository
) {
    fun showKeywordList() : List<ShowKeywordResponseDTO> {
        val keywordList = banKeywordRepository.findAll()

        val result = mutableListOf<ShowKeywordResponseDTO>()

        for (keyword in keywordList) {
            val dto = ShowKeywordResponseDTO(
                id = keyword.id,
                keyword = keyword.keyword
            )
            result.add(dto)
        }

        return result
    }

    fun createKeyword(createKeywordRequestDTO: CreateKeywordRequestDTO, userInfo: CustomUser) : CreateKeywordResponseDTO {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val banKeyword = BanKeyword(
            keyword = createKeywordRequestDTO.keyword
        )

        val result =  banKeywordRepository.save(banKeyword)

        targetUser.banKeywordList.add(result)
        memberRepository.save(targetUser)

        return CreateKeywordResponseDTO(
            id = result.id,
            keyword = result.keyword
        )
    }

    fun patchKeyword(id : Long, patchKeywordRequestDTO: PatchKeywordRequestDTO) : PatchKeywordResponseDTO {
        val targetKeyword = banKeywordRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 키워드입니다.")

        targetKeyword.keyword = patchKeywordRequestDTO.keyword

        val result = banKeywordRepository.save(targetKeyword)

        return PatchKeywordResponseDTO(
            id = result.id,
            keyword = result.keyword
        )
    }

    fun deleteKeyword(id : Long) {
        banKeywordRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 키워드입니다.")

        banKeywordRepository.deleteById(id)
    }

}