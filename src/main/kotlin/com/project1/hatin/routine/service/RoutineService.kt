package com.project1.hatin.routine.service

import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.member.repository.MemberRepository
import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutinePatchRequestDTO
import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutineDeleteRequestDTO
import com.project1.hatin.routine.dto.RoutineRequestDTO.RoutineCreateRequestDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.RoutinePatchResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.RoutineShowResponseDTO
import com.project1.hatin.routine.dto.RoutineResponseDTO.RoutineCreateResponseDTO
import com.project1.hatin.routine.entity.Routine
import com.project1.hatin.routine.repository.RoutineRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class RoutineService(
    private val routineRepository: RoutineRepository,
    private val memberRepository: MemberRepository
){
    fun showRoutineList(userInfo : CustomUser): List<RoutineShowResponseDTO> {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val targetRoutine = targetUser.routineList

        val result = mutableListOf<RoutineShowResponseDTO>()

        for (routine in targetRoutine) {
            val dto = RoutineShowResponseDTO(
                id = routine.id!!,
                startAt = routine.startAt,
                finishAt = routine.finishAt,
                name = routine.name,
                weekDay = routine.weekDay,
                isFinish = routine.isFinish,
                memo = routine.memo
            )
            result.add(dto)
        }
        return result
    }

    fun createRoutine(routineCreateRequestDTO: RoutineCreateRequestDTO, userInfo: CustomUser): RoutineCreateResponseDTO {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val routine = Routine(
            startAt = routineCreateRequestDTO.startAt,
            finishAt = routineCreateRequestDTO.finishAt,
            name = routineCreateRequestDTO.name,
            weekDay = routineCreateRequestDTO.weekDay,
            memo = routineCreateRequestDTO.memo,
            isFinish = false
        )

        val result = routineRepository.save(routine)

        targetUser.routineList.add(result)
        memberRepository.save(targetUser)

        return RoutineCreateResponseDTO(
            id =  result.id,
            startAt =  result.startAt,
            finishAt =  result.finishAt,
            name =  result.name,
            weekDay =  result.weekDay,
            memo = result.memo,
            isFinish = result.isFinish
            )
    }

    fun createRoutineList(routineCreateRequestDTOList: List<RoutineCreateRequestDTO>): MutableList<Routine> {

        var createRoutineList = mutableListOf<Routine>()

        for (dto in routineCreateRequestDTOList) {
            val routine = Routine(
                startAt = dto.startAt,
                finishAt = dto.finishAt,
                name = dto.name,
                weekDay = dto.weekDay,
                memo = dto.memo,
                isFinish = false
            )
            createRoutineList.add(routine)
        }
        createRoutineList = routineRepository.saveAll(createRoutineList)

        return createRoutineList
    }

    fun patchRoutine(id: Long, routinePatchRequestDTO: RoutinePatchRequestDTO): RoutinePatchResponseDTO {

        var target : Routine = routineRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 루틴 ID 입니다.")

        target.name = routinePatchRequestDTO.name
        target.startAt = routinePatchRequestDTO.startAt
        target.finishAt = routinePatchRequestDTO.finishAt
        target.weekDay = routinePatchRequestDTO.weekDay
        target.memo = routinePatchRequestDTO.memo
        target.isFinish = routinePatchRequestDTO.isFinish

        target = routineRepository.save(target)

        return RoutinePatchResponseDTO(
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
        routineRepository.findByIdOrNull(id)
            ?: throw PostException(msg = "존재하지 않는 루틴 ID 입니다.")

        routineRepository.deleteById(id)
    }

    fun deleteRoutineList(routineDeleteRequestDTOList: List<RoutineDeleteRequestDTO>) {

        val deleteRoutineList = mutableListOf<Routine?>()

        for (dto in routineDeleteRequestDTOList) {
            val target = routineRepository.findByIdOrNull(dto.id)
                ?: throw PostException(msg = "존재하지 않는 루틴 ID 입니다.")
            deleteRoutineList.add(target)
        }
        routineRepository.deleteAll(deleteRoutineList)
    }
}