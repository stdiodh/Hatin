package com.project1.hatin.common.config.member.repository

import com.project1.hatin.common.config.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>{
    fun findByuserId(userId: String) : Member?
}