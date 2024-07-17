package com.project1.hatin.common.config

import com.project1.hatin.common.authority.JwtAuthenticationFilter
import com.project1.hatin.common.authority.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    fun PasswordEncoder() : PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        http
            .csrf {it.disable()}
            .cors {it.disable()}
            .httpBasic {it.disable()}
            .sessionManagement {
                it.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests{
                it.requestMatchers("/api/member/join", "/api/member/login").anonymous()
                    .requestMatchers("/api/member/info", "api/friend/**", "api/feed/**").hasRole("MEMBER")
                    .anyRequest().permitAll()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }


}