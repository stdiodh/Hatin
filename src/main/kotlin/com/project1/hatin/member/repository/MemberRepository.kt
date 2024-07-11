package com.project1.hatin.member.repository

import com.project1.hatin.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>{
    fun findByuserId(userId: String) : Member?
    fun existsBynickName(nickName: String): Boolean
    fun existsByphoneNumber(phoneNumber: String): Boolean
    fun findBynickNameAndPhoneNumber(nickName: String, phoneNumber: String): Member?
}