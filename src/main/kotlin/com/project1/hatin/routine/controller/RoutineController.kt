package com.project1.hatin.routine.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutineCreateRequestDTO
import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutineDeleteRequestDTO
import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutinePatchRequestDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.RoutineCreateResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.RoutinePatchResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.RoutineShowResponseDTO
import com.project1.hatin.routine.service.RoutineService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@Tag(name = "루틴 Api 컨트롤러", description = "루틴 생성, 조회, 수정, 삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/routine")
class RoutineController(
     private val routineService: RoutineService
){
    @Operation(summary = "사용자 루틴 전체 조회", description = "사용자 루틴 전체 조회 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    private fun showAllRoutine(@Parameter(description = "헤더에 담긴 유저정보") @AuthenticationPrincipal userInfo: CustomUser)
            : ResponseEntity<BaseResponse<List<RoutineShowResponseDTO>>>{
        val result = routineService.showRoutineList(userInfo)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "사용자 루틴 생성", description = "사용자 루틴 생성 API 입니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    private fun postRoutine(@Parameter(description = "사용자가 생성하는 루틴 데이터") @Valid @RequestBody routineCreateRequestDTO: RoutineCreateRequestDTO,
                            @Parameter(description = "헤더에 담긴 유저정보") @AuthenticationPrincipal userInfo: CustomUser)
     : ResponseEntity<BaseResponse<RoutineCreateResponseDTO>> {
        val result = routineService.createRoutine(routineCreateRequestDTO,userInfo)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    @Operation(summary = "사용자 루틴 수정", description = "사용자 루틴 수정 API 입니다.")
    @PatchMapping("/{id}")
    private fun patchRoutine(@Parameter(required = true,description = "루틴 ID") @PathVariable(name = "id") id : Long,
                             @Parameter(description = "사용자가 수정하는 루틴 데이터") @Valid @RequestBody routinePatchRequestDTO: RoutinePatchRequestDTO )
    : ResponseEntity<BaseResponse<RoutinePatchResponseDTO>> {
        val result = routineService.patchRoutine(id,routinePatchRequestDTO)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    @Operation(summary = "사용자 루틴 삭제", description = "사용자 루틴 삭제 API 입니다.")
    @DeleteMapping("/{id}")
    private fun deleteRoutine(@Parameter(required = true,description = "루틴 ID") @PathVariable(name = "id") id : Long)
            : ResponseEntity<BaseResponse<String>> {
        routineService.deleteRoutine(id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = "루틴이 삭제되었습니다."))
    }

    @Operation(summary = "사용자 루틴 목록 삭제", description = "사용자 루틴 목록 삭제 API 입니다.")
    @DeleteMapping
    private fun deleteRoutineList(@Parameter(description = "사용자가 삭제하는 루틴 목록 데이터") @RequestBody routineDeleteRequestDTOList: List<RoutineDeleteRequestDTO>)
            : ResponseEntity<BaseResponse<String>> {
        routineService.deleteRoutineList(routineDeleteRequestDTOList)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = "루틴 목록이 삭제되었습니다."))
    }

}