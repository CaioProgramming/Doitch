package com.ilustris.doitch.base.models

import com.silent.ilustriscore.core.bean.BaseBean

const val NEWTASKID = "NEWTASK"
data class Item(val id: String = "", val name: String = "", val checked: Boolean = false) {
}