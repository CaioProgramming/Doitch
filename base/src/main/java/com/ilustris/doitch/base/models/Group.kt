package com.ilustris.doitch.base.models

import com.silent.ilustriscore.core.bean.BaseBean
import java.io.Serializable

const val CREATE_NEW_GROUP_ID = "NEWGROUP"
data class Group(
    override var id: String = "",
    val userID: String = "",
    var title: String = "",
    var tasks: List<Task> = emptyList()
) : BaseBean(id)