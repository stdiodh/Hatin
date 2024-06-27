package com.project1.hatin.common.exception

class PostException(
    val msg : String = "에러가 발생했습니다!"
) : RuntimeException()