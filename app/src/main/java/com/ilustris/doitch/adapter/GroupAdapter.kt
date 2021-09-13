package com.ilustris.doitch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.doitch.R
import com.ilustris.doitch.base.models.CREATE_NEW_GROUP_ID
import com.ilustris.doitch.base.models.Group
import com.ilustris.doitch.base.models.Item
import com.ilustris.doitch.base.models.NEWTASKID
import com.ilustris.doitch.databinding.CreateGroupCardBinding
import com.ilustris.doitch.databinding.GroupListItemBinding

private const val NEWGROUP = 0
private const val GROUP = 1

class GroupAdapter(val groupList: List<Group>, val createTaskListener: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class CreateGroupViewHolder(private val createGroupCardBinding: CreateGroupCardBinding): RecyclerView.ViewHolder(createGroupCardBinding.root) {

        fun bind() {
            createGroupCardBinding.createGroupButton.setOnClickListener {
            }
        }

    }

    inner class GroupViewHolder(private val groupViewBinding: GroupListItemBinding) : RecyclerView.ViewHolder(groupViewBinding.root) {

        fun bind() {
            groupList[adapterPosition].run {
                groupViewBinding.groupTitle.text = title
                val itemsArray = ArrayList(items)
                itemsArray.add(Item(id = NEWTASKID))
                groupViewBinding.itemsRecycler.adapter = ItemAdapter(itemsArray)
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