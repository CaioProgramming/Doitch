package com.ilustris.doitch.adapter

import android.app.DatePickerDialog
import android.graphics.Paint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.animations.fadeIn
import com.ilustris.animations.fadeOut
import com.ilustris.doitch.R
import com.ilustris.doitch.base.models.Task
import com.ilustris.doitch.base.models.NEWTASKID
import com.ilustris.doitch.databinding.ListItemLayoutBinding
import com.ilustris.doitch.databinding.NewTaskLayoutBinding
import com.silent.ilustriscore.core.utilities.DateFormats
import com.silent.ilustriscore.core.utilities.formatDate
import com.silent.ilustriscore.core.utilities.gone
import com.silent.ilustriscore.core.utilities.visible
import java.util.*

private const val NEWTASK = 0
private const val TASK = 1
class TaskAdapter(
    val tasks: List<Task>,
    val newTaskListener: (Task) -> Unit,
    val taskChangedListener: (Task, Int) -> Unit,
    val taskDeleteListener: (Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(private val itemListItemBinding: ListItemLayoutBinding): RecyclerView.ViewHolder(itemListItemBinding.root) {
        fun bind() {
            tasks[adapterPosition].run {
                itemListItemBinding.item = this
                itemListItemBinding.itemName.paintFlags = if (checked) Paint.STRIKE_THRU_TEXT_FLAG else Paint.LINEAR_TEXT_FLAG
                itemListItemBinding.taskCard.isChecked = checked
                itemListItemBinding.taskCard.setOnClickListener {
                    this.checked = !checked
                    taskChangedListener.invoke(this, adapterPosition)
                }
                itemListItemBinding.taskCard.setOnLongClickListener {
                    taskDeleteListener.invoke(adapterPosition)
                    false
                }
                itemListItemBinding.itemDate.text =
                    date.formatDate(this.date, "dd 'de' MMMM 'de' yyyy")
            }
        }
    }

    inner class CreateTaskViewHolder(private val newTaskLayoutBinding: NewTaskLayoutBinding): RecyclerView.ViewHolder(newTaskLayoutBinding.root) {

        fun bind() {
            newTaskLayoutBinding.run {
                val task = Task()
                saveButton.gone()
                newTask.addTextChangedListener {
                    if (!it!!.isNotEmpty()) saveButton.fadeOut() else {
                        if (saveButton.visibility == View.GONE) {
                            saveButton.fadeIn()
                        }
                    }
                }
                saveButton.setOnClickListener {
                    task.name = newTask.text.toString()
                    newTaskListener.invoke(task)
                }
                calendarButton.setOnClickListener {
                    val datenow = Calendar.getInstance().time
                    val calendar = GregorianCalendar()
                    calendar.time = datenow
                    DatePickerDialog(
                        itemView.context,
                        { view, year, month, dayOfMonth ->
                            run {
                                task.date = GregorianCalendar(year, month, dayOfMonth).time
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (tasks[position].id == NEWTASKID) {
            NEWTASK
        } else TASK
    }

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NEWTASK) CreateTaskViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.new_task_layout, parent, false)) else ItemViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CreateTaskViewHolder) {
            holder.bind()
        } else {
            (holder as ItemViewHolder).bind()
        }
    }

}