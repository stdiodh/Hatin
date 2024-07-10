package com.project1.hatin.member.service

import com.project1.hatin.common.authority.JwtTokenProvider
import com.project1.hatin.common.dto.TokenInfo
import com.project1.hatin.common.enums.Role
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.common.exception.member.InvaliduserIdException
import com.project1.hatin.member.dto.LoginDto
import com.project1.hatin.member.dto.MemberRequestDto
import com.project1.hatin.member.dto.MemberResponseDto
import com.project1.hatin.member.entity.Member
import com.project1.hatin.member.entity.MemberRole
import com.project1.hatin.member.entity.PasswordResetCode
import com.project1.hatin.member.repository.MemberRepository
import com.project1.hatin.member.repository.MemberRoleRepository

import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutineCreateRequestDTO
import com.project1.hatin.routine.service.RoutineService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import com.project1.hatin.member.repository.PasswordResetCodeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Tag(name = "회원 Api 컨트롤러", description = "회원 가입, 로그인, Api 명세서 입니다.")
@Transactional
@Service
class MemberService (
    private val memberRepository : MemberRepository,
    private val routineService: RoutineService,
    private val memberRoleRepository: MemberRoleRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder,
    private val mailSender: JavaMailSender,
    private val codeRepository: PasswordResetCodeRepository

){
    fun signUp(memberRequestDto : MemberRequestDto, routineCreateRequestDTOList: List<RoutineCreateRequestDTO>) : String {
        var member: Member? = memberRepository.findByuserId(memberRequestDto.userId)

        if (member != null){
            throw InvaliduserIdException(fieldName = "userId", massage = "이미 가입한 사용자 아이디입니다!")
        }

        val savedRoutine = routineService.createRoutineList(routineCreateRequestDTOList)

        member = Member (
            userId = memberRequestDto.userId,
            password = passwordEncoder.encode(memberRequestDto.password),
            nickName = memberRequestDto.nickName,
            birthday = memberRequestDto.birthday,
            phoneNumber = memberRequestDto.phoneNumber,
            address = memberRequestDto.address,
            gender = memberRequestDto.gender
        )

        member.routineList = savedRoutine

        memberRepository.save(member)

        val memberRole = MemberRole(
            role = Role.MEMBER,
            member = member,
        )

        memberRoleRepository.save(memberRole)

        return "회원가입이 완료됐습니다."
    }

    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.userId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val accessToken = jwtTokenProvider.createAccessToken(authentication)
        return TokenInfo(grantType = "Bearer", accessToken = accessToken)
    }

    fun searchMyInfo(id: Long): MemberResponseDto {
        val targetUser = memberRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 사용자 입니다!")
        return targetUser.toResponse()
    }

    fun findByUserId(userId: String): Member? {
        return memberRepository.findByuserId(userId)
    }

    fun passwordSave(member: Member) {
        member.password = passwordEncoder.encode(member.password)
        memberRepository.save(member)
    }

    fun sendPasswordResetCode(userId: String){
        val code = (100000..999999).random().toString()
        val expiryDate = LocalDateTime.now().plusMinutes(15)

        val passwordResetCode = PasswordResetCode(
            code = code,
            userId = userId,
            expiryData = expiryDate
        )
        codeRepository.save(passwordResetCode)

        val message = SimpleMailMessage()
        message.setTo(userId)
        message.subject = "비밀번호 변경 요청"
        message.text = "당신의 비밀번호 인증코드는 : $code 입니다! \n" +
                "15분 안에 회신하셔야 비밀번호를 바꿀 수 있습니다!"
        try {
            mailSender.send(message)
            println("비밀번호 인증코드를 정상적으로 보냈습니다!")
        } catch (e: Exception) {
            e.printStackTrace()
            println("비밀번호 초기화 인증코드를 보내지 못했습니다.")
        }
    }

    fun validateResetCode(userId: String, code: String): Boolean {
        val passwordResetCode = codeRepository.findByCodeAndUserId(code, userId)
        return passwordResetCode != null && passwordResetCode.expiryData.isAfter(LocalDateTime.now())
    }
}
