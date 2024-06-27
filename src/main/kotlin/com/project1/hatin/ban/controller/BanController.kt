package com.project1.hatin.ban.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ban")
class BanController {
    @Operation(summary = "Get Test API", description = "This is GET API")
    @GetMapping("/test")
    private fun join() :
            ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CREATED).body("테스트 합니다.")
    }
}