package com.ilustris.doitch.binder

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.View
import android.widget.PopupMenu
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ilustris.animations.fadeIn
import com.ilustris.doitch.R
import com.ilustris.doitch.adapter.TaskAdapter
import com.ilustris.doitch.base.models.Group
import com.ilustris.doitch.base.models.NEWTASKID
import com.ilustris.doitch.base.models.Task
import com.ilustris.doitch.databinding.ActivityGroupBinding
import com.ilustris.doitch.dialog.NewGroupBottomSheet
import com.silent.ilustriscore.core.view.BaseView
import presenter.GroupPresenter

class GroupActBinder(override val viewBind: ActivityGroupBinding, val groupID: String) :
    BaseView<Group>() {
    override val presenter = GroupPresenter(this)

    override fun initView() {
        presenter.loadSingleData(groupID)
    }

    override fun showData(data: Group) {
        super.showData(data)
        setupGroup(data)
    }

    private fun setupGroup(group: Group) {
        viewBind.run {
            groupTitle.text = group.title
            groupProgress.max = group.tasks.size
            groupProgress.setProgress(group.tasks.filter { it.checked }.size, true)
            groupTitle.setOnLongClickListener {
                PopupMenu(context, it).apply {
                    menuInflater.inflate(R.menu.group_menu, menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.group_edit -> NewGroupBottomSheet(context) {
                                group.title = it
                                presenter.updateData(group)
                            }.buildDialog()
                            R.id.group_delete -> {
                                MaterialAlertDialogBuilder(context)
                                    .setTitle("Tem certeza?")
                                    .setMessage("Você está prestes a excluir ${group.title}, para prosseguir confirme.")
                                    .setPositiveButton("Ok")
                                    { dialog, which ->
                                        presenter.deleteData(group)
                                        (context as Activity).finish()
                                    }
                                    .setNegativeButton("Cancelar")
                                    { dialog, which -> dismiss() }
                                    .show()
                            }
                        }
                        true
                    }
                }.show()
                false
            }
            groupProgressText.text = "${getGroupProgress(group)}%"
            val taskViews = ArrayList(group.tasks.sortedBy { it.checked })
            taskViews.add(Task(id = NEWTASKID))
            tasksRecyclerview.adapter = TaskAdapter(taskViews,
                newTaskListener = { newTask ->
                    val tasksArray = ArrayList(taskViews)
                    tasksArray.add(newTask)
                    group.tasks = tasksArray.filter { task ->
                        task.id != NEWTASKID
                    }
                    presenter.updateData(group)
                },
                taskChangedListener = { it, index ->
                    val tasksArray = ArrayList(taskViews)
                    tasksArray[index] = it
                    group.tasks = tasksArray.filter { it.id != NEWTASKID }
                    presenter.updateData(group)
                },
                taskDeleteListener = { position ->
                    val tasksArray = ArrayList(taskViews)
                    tasksArray.removeAt(position)
                    group.tasks = tasksArray.filter { it.id != NEWTASKID }
                    presenter.updateData(group)
                })
            if (tasksRecyclerview.visibility == View.GONE) {
                tasksRecyclerview.fadeIn()
            }
        }
    }

    private fun getGroupProgress(group: Group): Long {
        return try {
            val percentage: Long = 100L * group.tasks.filter { it.checked }.size / group.tasks.size

            val total = group.tasks.size.toDouble()
            val checkedItem = group.tasks.filter { it.checked }.size
            return percentage
        } catch (e: Exception) {
            0L
        }
    }

}