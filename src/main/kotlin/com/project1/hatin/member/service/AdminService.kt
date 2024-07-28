package com.project1.hatin.member.service

import com.project1.hatin.common.enums.Role
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.common.exception.member.InvaliduserIdException
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.entity.Member
import com.project1.hatin.member.entity.MemberRole
import com.project1.hatin.member.repository.MemberRepository
import com.project1.hatin.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val memberRoleRepository: MemberRoleRepository
) {

    fun createAdmin(adminRequestDto: MemberRequestDto): String {
        var member: Member? = memberRepository.findByuserId(adminRequestDto.userId)

        if (member != null) {
            throw InvaliduserIdException(fieldName = "userId", massage = "이미 가입한 사용자 아이디입니다!")
        }

        if (memberRepository.existsBynickName(adminRequestDto.nickName)) {
            throw InvaliduserIdException(fieldName = "nickName", massage = "이미 사용중인 닉네임입니다!")
        }

        if (memberRepository.existsByphoneNumber(adminRequestDto.phoneNumber)) {
            throw InvaliduserIdException(fieldName = "phoneNumber", massage = "이미 사용중인 핸드폰 번호입니다!")
        }

        member = Member(
            userId = adminRequestDto.userId,
            password = passwordEncoder.encode(adminRequestDto.password),
            nickName = adminRequestDto.nickName,
            birthday = adminRequestDto.birthday,
            phoneNumber = adminRequestDto.phoneNumber,
            address = adminRequestDto.address,
            gender = adminRequestDto.gender
        )

        memberRepository.save(member)

        val adminRole = MemberRole(
            role = Role.ADMIN,
            member = member,
        )

        memberRoleRepository.save(adminRole)

        return "관리자 계정이 생성되었습니다."
    }

    @Transactional
    fun deleteUserById(userId: Long): String {
        val user = memberRepository.findByIdOrNull(userId)
            ?: throw PostException("존재하지 않는 사용자입니다.")

        val nickName = user.nickName
        memberRepository.delete(user)
        return nickName
    }
}