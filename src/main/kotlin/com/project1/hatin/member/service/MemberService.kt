package com.project1.hatin.member.service

import com.project1.hatin.common.exception.InvaliduserIdException
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.entity.Member
import com.project1.hatin.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService (
    private val memberRepository : MemberRepository
){
    fun signUp(memberRequestDto : MemberRequestDto) : String {
        val member: Member? = memberRepository.findByuserId(memberRequestDto.userId)
        if (member != null){
            throw InvaliduserIdException(fieldName = "email", massage = "이미 가입한 이메일 입니다!")
        }
        memberRepository.save(memberRequestDto.toEntity())
        return "회원가입이 완료되었습니다."
    }
}