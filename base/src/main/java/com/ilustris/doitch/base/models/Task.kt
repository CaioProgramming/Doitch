package com.ilustris.doitch.base.models

import com.silent.ilustriscore.core.bean.BaseBean
import java.io.Serializable
import java.util.*

const val NEWTASKID = "NEWTASK"

data class Task(
    val id: String = "",
    var name: String = "",
    var date: Date = Date(),
    var checked: Boolean = false
) : Serializable