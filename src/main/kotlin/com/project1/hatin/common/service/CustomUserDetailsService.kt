package com.project1.hatin.common.service

import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.member.entity.Member
import com.project1.hatin.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return memberRepository.findByuserId(username)
            ?.let { createUserDetails(it) }
            ?:throw UsernameNotFoundException("해당하는 유저는 존재하지 않습니다!")
    }

    private fun createUserDetails(member : Member) : UserDetails {
        return CustomUser(
            member.id!!,
            member.userId,
            passwordEncoder.encode(member.password),
            member.role!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
    }
}