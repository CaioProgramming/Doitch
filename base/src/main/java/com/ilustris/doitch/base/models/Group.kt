package com.ilustris.doitch.base.models

import com.silent.ilustriscore.core.bean.BaseBean
import java.io.Serializable

const val CREATE_NEW_GROUP_ID = "NEWGROUP"
const val FOLLOW_ON_TWITCH = "FOLLOWONTWITCHGROUP"
const val FOLLOW_ON_INSTAGRAM = "FOLLOWONINSTA"
const val FOLLOW_ON_GITHUB = "FOLLOWONGIT"
const val CHECK_ON_PLAYSTORE = "CHECKONPLAYSTORE"
const val ABOUT_HEADER = "GROUPABOUTHEADER"

data class Group(
    override var id: String = "",
    val userID: String = "",
    var title: String = "",
    var tasks: List<Task> = emptyList()
) : BaseBean(id) {

    fun isASocialCard(): Boolean {
        return id == FOLLOW_ON_TWITCH || id == FOLLOW_ON_INSTAGRAM || id == FOLLOW_ON_GITHUB || id == CHECK_ON_PLAYSTORE
    }

    companion object {
        fun extraGroups(): List<Group> {
            return listOf<Group>(
                Group(id = CREATE_NEW_GROUP_ID),
                Group(id = ABOUT_HEADER),
                Group(id = FOLLOW_ON_TWITCH),
                Group(id = FOLLOW_ON_INSTAGRAM),
                Group(id = FOLLOW_ON_GITHUB),
                Group(id = CHECK_ON_PLAYSTORE)
            )
        }
    }

}