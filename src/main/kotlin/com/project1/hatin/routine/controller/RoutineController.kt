package com.project1.hatin.routine.controller

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.routine.dto.RoutineRequestDTO.CreateRequestDTO
import com.project1.hatin.routine.dto.RoutineRequestDTO.PatchRequestDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.CreateResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.PatchResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.ShowResponseDTO
import com.project1.hatin.routine.service.RoutineService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/routine")
class RoutineController {

    @Autowired
    private lateinit var routineService: RoutineService

//    @Operation(summary = "사용자 루틴 전체 조회", description = "사용자 루틴 전체 조회")
//    @GetMapping
//    private fun showAllRoutine(@Parameter(description = "헤더에 담긴 유저정보") @AuthenticationPrincipal userInfo: CustomUserDetails)
//    : ResponseEntity<BaseResponse<List<ShowResponseDTO>>>{
//        val result = routineService.showList(userInfo)
//        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
//    }

    @Operation(summary = "사용자 루틴 생성", description = "사용자 루틴 생성")
    @PostMapping
    private fun postRoutine(@Parameter(description = "사용자가 생성하는 루틴 데이터") @RequestBody createRequestDTO: CreateRequestDTO)
     : ResponseEntity<BaseResponse<CreateResponseDTO>> {
        val result = routineService.create(createRequestDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    @Operation(summary = "사용자 루틴 수정", description = "사용자 루틴 수정")
    @PatchMapping("/{id}")
    private fun patchRoutine(@Parameter(required = true,description = "루틴 ID") @PathVariable(name = "id") id : Long,
                             @Parameter(description = "사용자가 수정하는 루틴 데이터") @RequestBody patchRequestDTO: PatchRequestDTO )
    : ResponseEntity<BaseResponse<PatchResponseDTO>> {
        val result = routineService.patch(id,patchRequestDTO)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }


}