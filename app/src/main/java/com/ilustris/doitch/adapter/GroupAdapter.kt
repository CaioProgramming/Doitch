package com.ilustris.doitch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.animations.popIn
import com.ilustris.doitch.GroupActivity
import com.ilustris.doitch.R
import com.ilustris.doitch.base.models.CREATE_NEW_GROUP_ID
import com.ilustris.doitch.base.models.Group
import com.ilustris.doitch.base.models.Task
import com.ilustris.doitch.base.models.NEWTASKID
import com.ilustris.doitch.databinding.CreateGroupCardBinding
import com.ilustris.doitch.databinding.GroupListItemBinding
import com.ilustris.doitch.dialog.NewGroupBottomSheet
import com.silent.ilustriscore.core.utilities.delayedFunction

private const val NEWGROUP = 0
private const val GROUP = 1

class GroupAdapter(val groupList: List<Group>,val createGroup: () -> Unit, val updateGroup: (Group) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CreateGroupViewHolder(private val createGroupCardBinding: CreateGroupCardBinding): RecyclerView.ViewHolder(createGroupCardBinding.root) {

        fun bind() {
            createGroupCardBinding.createGroupButton.setOnClickListener {
                createGroup.invoke()
            }
        }

    }

    inner class GroupViewHolder(private val groupViewBinding: GroupListItemBinding) : RecyclerView.ViewHolder(groupViewBinding.root) {

        fun bind() {
            groupList[adapterPosition].run {
                groupViewBinding.groupTitle.text = title
                groupViewBinding.taskCount.text = "${tasks.size} tarefas"
                groupViewBinding.groupCard.setOnClickListener {
                    GroupActivity.launchIntent(
                        itemView.context, this,
                        groupViewBinding.groupTitle
                    )
                }
                groupViewBinding.groupProgress.max = tasks.size
                delayedFunction {
                    groupViewBinding.groupProgress.setProgress(
                        tasks.filter { it.checked }.size,
                        true
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (groupList[position].id == CREATE_NEW_GROUP_ID) {
            NEWGROUP
        } else {
            GROUP
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            NEWGROUP -> CreateGroupViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.create_group_card, parent, false))
            else -> GroupViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.group_list_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CreateGroupViewHolder) {
            holder.bind()
        } else {
            (holder as GroupViewHolder).bind()
        }
    }

}