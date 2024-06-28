package com.project1.hatin.common.exception.member

import com.project1.hatin.common.dto.BaseResponse
import com.project1.hatin.common.enums.ResultStatus
import com.project1.hatin.common.exception.InvaliduserIdException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(value = 1)
@RestControllerAdvice
class MemberExceptionHanderler {
    @ExceptionHandler(InvaliduserIdException::class)
    protected fun invalidEmailExceptionHandler(exception: InvaliduserIdException)
            : ResponseEntity<BaseResponse<Map<String, String>>>{
        val error = mapOf(exception.fieldName to (exception.message ?: "Not Exception Massage"))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                BaseResponse(
                    status = ResultStatus.ERROR.name,
                    data = error,
                    resultMsg = ResultStatus.ERROR.msg,
                )
            )
    }
}
