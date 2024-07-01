package com.project1.hatin.routine.service

import com.project1.hatin.common.exception.PostException
import com.project1.hatin.routine.dto.RoutineRequestDTO.PatchRequestDTO
import com.project1.hatin.routine.dto.RoutineRequestDTO.CreateRequestDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.PatchResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.ShowResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.CreateResponseDTO
import com.project1.hatin.routine.entity.Routine
import com.project1.hatin.routine.repository.RoutineRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class RoutineService{

    @Autowired
    private lateinit var routineRepository: RoutineRepository

//    fun showRoutineList(userInfo : CustomUserDetails): List<ShowResponseDTO> {
//
//    }

    fun createRoutine(createRequestDTO: CreateRequestDTO): CreateResponseDTO {

        val routine = Routine(
            startAt = createRequestDTO.startAt,
            finishAt = createRequestDTO.finishAt,
            name = createRequestDTO.name,
            weekDay = createRequestDTO.weekDay,
            memo = createRequestDTO.memo,
            isFinish = false
        )

        val result = routineRepository.save(routine)

        return CreateResponseDTO(
            id =  result.id,
            startAt =  result.startAt,
            finishAt =  result.finishAt,
            name =  result.name,
            weekDay =  result.weekDay,
            memo = result.memo,
            isFinish = result.isFinish
            )
    }

    fun patchRoutine(id: Long,patchRequestDTO: PatchRequestDTO): PatchResponseDTO {

        var target : Routine = routineRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 루틴 ID입니다.")

        target.name = patchRequestDTO.name
        target.startAt = patchRequestDTO.startAt
        target.finishAt = patchRequestDTO.finishAt
        target.weekDay = patchRequestDTO.weekDay
        target.memo = patchRequestDTO.memo
        target.isFinish = patchRequestDTO.isFinish

        target = routineRepository.save(target)

        return PatchResponseDTO(
            id =  target.id,
            startAt =  target.startAt,
            finishAt =  target.finishAt,
            name =  target.name,
            weekDay =  target.weekDay,
            memo = target.memo,
            isFinish = target.isFinish
        )
    }

    fun deleteRoutine(id: Long){
        var target : Routine = routineRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 루틴 ID입니다.")

        routineRepository.deleteById(id)
    }

}