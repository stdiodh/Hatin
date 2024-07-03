package com.project1.hatin.common.config.member.repository

import com.project1.hatin.common.config.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long?>