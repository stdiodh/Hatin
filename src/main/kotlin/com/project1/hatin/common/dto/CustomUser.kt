package com.project1.hatin.common.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser (
    val id : Long,
    userId : String,
    password : String,
    authority : Collection<GrantedAuthority>,
) : User(userId, password, authority)