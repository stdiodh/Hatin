package com.project1.hatin.ban.controller

import com.project1.hatin.ban.dto.BanKeywordRequestDTO.CreateKeywordRequestDTO
import com.project1.hatin.ban.dto.BanKeywordRequestDTO.PatchKeywordRequestDTO
import com.project1.hatin.ban.dto.BanKeywordResponseDTO.CreateKeywordResponseDTO
import com.project1.hatin.ban.dto.BanKeywordResponseDTO.ShowKeywordResponseDTO
import com.project1.hatin.ban.dto.BanKeywordResponseDTO.PatchKeywordResponseDTO
import com.project1.hatin.ban.service.BanService
import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "벤 Api 컨트롤러", description = "벤 목록 생성, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/ban")
class BanController(
    private val banService: BanService
) {
    @Operation(summary = "금지 키워드 리스트 조회", description = "금지 키워드 리스트 조회 API 입니다.")
    @GetMapping("/keyword")
    private fun showKeywordList() : ResponseEntity<BaseResponse<List<ShowKeywordResponseDTO>>> {
        val result = banService.showKeywordList()
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "금지 키워드 생성", description = "금지 키워드 생성 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/keyword")
    private fun createKeyword(@Parameter(description = "사용자가 생성하는 금지 키워드 데이터") @Valid @RequestBody createKeywordRequestDTO: CreateKeywordRequestDTO,
                              @Parameter(description = "헤더에 담긴 유저정보") @AuthenticationPrincipal userInfo: CustomUser
    ) : ResponseEntity<BaseResponse<CreateKeywordResponseDTO>> {
        val result = banService.createKeyword(createKeywordRequestDTO,userInfo)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    @Operation(summary = "금지 키워드 수정", description = "금지 키워드 수정 API 입니다.")
    @PatchMapping("/keyword/{id}")
    private fun patchKeyword(@Parameter(required = true,description = "키워드 ID") @PathVariable(name = "id") id:Long,
                             @Parameter(description = "사용자가 수정하는 금지 키워드 데이터") @Valid @RequestBody patchKeywordRequestDTO: PatchKeywordRequestDTO
    ) : ResponseEntity<BaseResponse<PatchKeywordResponseDTO>> {
        val result = banService.patchKeyword(id,patchKeywordRequestDTO)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "금지 키워드 삭제", description = "금지 키워드 삭제 API 입니다.")
    @DeleteMapping("/keyword/{id}")
    private fun deleteKeyword(@Parameter(required = true,description = "키워드 ID") @PathVariable(name = "id") id:Long
    ) : ResponseEntity<BaseResponse<String>> {
        val result = banService.deleteKeyword(id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = "금지 키워드가 삭제되었습니다."))
    }



}