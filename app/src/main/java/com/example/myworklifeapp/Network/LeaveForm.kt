package com.example.myworklifeapp.Network

import java.util.*

data class LeaveForm (

    val username : String,
    val startDate : String,
    val endDate : String,
    val isStartHalfDay : Boolean,
    val leaveType : String

    )