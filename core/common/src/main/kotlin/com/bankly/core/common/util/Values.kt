package com.bankly.core.common.util

import java.time.LocalDateTime
import java.time.ZoneOffset

val clientReqId: String
    get() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()