package com.ilustris.doitch.base.models

import com.silent.ilustriscore.core.bean.BaseBean

const val CREATE_NEW_GROUP_ID = "NEWGROUP"
data class Group(override val id: String = "",
                 val userID: String = "",
                 val title: String = "",
                 val items: List<Item> = emptyList()) : BaseBean(id)