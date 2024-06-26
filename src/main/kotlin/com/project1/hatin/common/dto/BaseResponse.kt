package com.project1.hatin.common.dto

import com.project1.hatin.common.enums.ResultStatus

data class BaseResponse<T>(
    var status : String = ResultStatus.SUCCESS.name,
    var data : T? = null,
    var resultMsg : String = ResultStatus.SUCCESS.msg,
)
