package com.project1.hatin.feed.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.feed.service.FeedService
import io.swagger.v3.oas.annotations.tags.Tag
import com.project1.hatin.feed.dto.FeedRequestDTO.FeedPatchRequestDTO
import com.project1.hatin.feed.dto.FeedRequestDTO.FeedCreateRequestDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedCreateResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedPatchResponseDTO
import com.project1.hatin.feed.dto.FeedResponseDTO.FeedShowResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@Tag(name = "게시글 Api 컨트롤러", description = "게시글 생성, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/feed")
class FeedController(
    private val feedService: FeedService
) {
    @Operation(summary = "게시글 전체 조회", description = "게시글 전체 조회 API 입니다.")
    @GetMapping("/list/{type}")
    private fun showAllFeed(@Parameter(required = true,description = "게시글 타입") @PathVariable(name = "type") type : String)
    : ResponseEntity<BaseResponse<List<FeedShowResponseDTO>>> {
        val result = feedService.showAllFeed(type)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "게시글 조회", description = "게시글 조회 API 입니다.")
    @GetMapping("/{id}")
    private fun showFeed(@Parameter(required = true,description = "게시글 ID") @PathVariable(name = "id") id : Long)
    : ResponseEntity<BaseResponse<FeedShowResponseDTO>> {
        val result = feedService.showFeed(id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

//    @Operation(summary = "게시글 검색", description = "게시글 검색 API 입니다.")
//    @GetMapping
//    private fun searchFeed() : ResponseEntity<BaseResponse<SearchResponseDTO>> {
//        val result = feedService.searchFeed()
//        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
//    }

    @Operation(summary = "게시글 생성", description = "게시글 생성 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    private fun createFeed(@Parameter(description = "사용자가 생성하는 게시글 데이터") @Valid @RequestBody feedCreateRequestDTO : FeedCreateRequestDTO,
                           @Parameter(description = "헤더에 담긴 유저정보") @AuthenticationPrincipal userInfo: CustomUser
    )
    : ResponseEntity<BaseResponse<FeedCreateResponseDTO>> {
        val result = feedService.createFeed(feedCreateRequestDTO,userInfo)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("{id}")
    private fun patchFeed(@Parameter(required = true,description = "게시글 ID") @PathVariable(name = "id") id : Long,
                          @Parameter(description = "헤더에 담긴 유저정보") @AuthenticationPrincipal userInfo: CustomUser,
                          @Parameter(description = "사용자가 수정하는 게시글 데이터") @Valid @RequestBody feedPatchRequestDTO: FeedPatchRequestDTO)
    : ResponseEntity<BaseResponse<FeedPatchResponseDTO>> {
        val result = feedService.patchFeed(id,feedPatchRequestDTO,userInfo)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("{id}")
    private fun deleteFeed(@Parameter(required = true,description = "게시글 ID") @PathVariable(name = "id") id : Long)
            : ResponseEntity<BaseResponse<Any>> {
        val result = feedService.deleteFeed(id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = "게시글이 삭제되었습니다."))
    }


}