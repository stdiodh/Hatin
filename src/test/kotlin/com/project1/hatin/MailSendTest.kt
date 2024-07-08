package com.project1.hatin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class MailTestRunner(
    @Autowired private val mailSender: JavaMailSender
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val message = SimpleMailMessage()
        message.setTo("playlistdh@gmail.com") // 테스트용 수신 이메일
        message.subject = "Test Email"
        message.text = "This is a test email from Spring Boot application."

        try {
            mailSender.send(message)
            println("Test email sent successfully.")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Failed to send test email.")
        }
    }
}