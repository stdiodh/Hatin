package com.project1.hatin.common.exception.member

class InvaliduserIdException (
    val fieldName : String = "",
    massage : String = "에러가 발생했습니다."
) : RuntimeException(massage)