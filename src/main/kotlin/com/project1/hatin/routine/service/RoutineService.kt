package com.project1.hatin.routine.service

import com.project1.hatin.common.dto.CustomUser
import com.project1.hatin.common.exception.PostException
import com.project1.hatin.common.config.member.repository.MemberRepository
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
class RoutineService(
    private val routineRepository: RoutineRepository,
    private val memberRepository: MemberRepository
){
    fun showRoutineList(userInfo : CustomUser): List<ShowResponseDTO> {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val targetRoutine = targetUser.routineList
            ?: throw PostException(msg = "사용자가 작성한 루틴이 존재하지 않습니다.")

        // 결과 리스트 생성 및 루틴 변환
        val result = mutableListOf<ShowResponseDTO>()

        for (routine in targetRoutine) {
            val dto = ShowResponseDTO(
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

    fun createRoutine(createRequestDTO: CreateRequestDTO, userInfo: CustomUser): CreateResponseDTO {
        val targetUser = memberRepository.findByIdOrNull(userInfo.id)
            ?: throw PostException(msg = "존재하지 않는 사용자입니다.")

        val routine = Routine(
            startAt = createRequestDTO.startAt,
            finishAt = createRequestDTO.finishAt,
            name = createRequestDTO.name,
            weekDay = createRequestDTO.weekDay,
            memo = createRequestDTO.memo,
            isFinish = false
        )

        val result = routineRepository.save(routine)

        targetUser.routineList.add(result)
        memberRepository.save(targetUser)

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

    fun createRoutineList(createRequestDTOList: List<CreateRequestDTO>): MutableList<Routine> {

        var createRoutineList = mutableListOf<Routine>()

        for (dto in createRequestDTOList) {
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