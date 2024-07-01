package com.project1.hatin.common.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val id : Long,
    email : String,
    password : String,
    authorities : Collection<GrantedAuthority>
) : User(email, password, authorities)