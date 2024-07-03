package com.project1.hatin.ban.controller

import com.project1.hatin.ban.service.BanService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "벤 Api 컨트롤러", description = "벤 목록 생성, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/ban")
class BanController(
    private val banService: BanService
) {
    @Operation(summary = "금지 키워드 생성", description = "금지 키워드 생성 API 입니다.")
    @PostMapping("/keyword")
    private fun createKeyword() : ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CREATED).body("테스트 합니다.")
    }
}