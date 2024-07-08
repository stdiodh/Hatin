package com.project1.hatin.common.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//@Configuration
//class JasyptConfig {
//
//    @Value("\${jasypt.encryptor.password}")
//    private lateinit var password: String
//
//    @Bean("jasyptStringEncryptor")
//    fun stringEncryptor(): StringEncryptor {
//        val encryptor = PooledPBEStringEncryptor()
//        val config = SimpleStringPBEConfig().apply {
//            setPassword(password)
//            poolSize = 1
//            algorithm = "PBEWithMD5AndDES"
//            stringOutputType = "base64"
//            keyObtentionIterations = 1000
//            setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
//        }
//        encryptor.setConfig(config)
//        return encryptor
//    }
//}