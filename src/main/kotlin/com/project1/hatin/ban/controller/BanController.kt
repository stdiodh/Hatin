package com.project1.hatin.ban.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ban")
class BanController {
    @Operation(summary = "스웨거 테스트 API", description = "스웨거 테스트 API 입니다.")
    @GetMapping("/test")
    private fun join() :
            ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CREATED).body("테스트 합니다.")
    }
}