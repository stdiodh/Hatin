package com.project1.hatin.common.authority

import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.dto.TokenInfo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

const val EXPIRATION_MILLISECONDS = 1000 * 60 * 30L

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey : String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))}

    //토큰 생성
    fun createAccessToken(authentication : Authentication) : String {
        val authorities : String = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()

        //토큰 유효시간 (30분)
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        val userId = (authentication.principal as CustomUser).id

        return Jwts
            .builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(accessExpiration)
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }

    //토큰 추출
    fun getAuthentication(token : String) : Authentication {
        val claims : Claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("유효하지 않은 토큰입니다!")
        val userId = claims["userId"] ?: throw RuntimeException("유효하지 않은 토큰입니다!")

        val authorities : Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal : UserDetails = CustomUser(userId.toString().toLong(),Claims.SUBJECT, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    //토큰 검증
    fun validateToken (token: String) : Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception){
            println(e.message)
        }
        return false
    }

    //Claims 추출
    private fun getClaims (token: String) : Claims =
        Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}