package com.ilustris.doitch.binder

import android.app.Activity
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ilustris.doitch.R
import com.ilustris.doitch.adapter.GroupAdapter
import com.ilustris.doitch.base.models.*
import com.ilustris.doitch.databinding.ActivityMainBinding
import com.ilustris.doitch.dialog.NewGroupBottomSheet
import com.silent.ilustriscore.core.model.DTOMessage
import com.silent.ilustriscore.core.utilities.OperationType
import com.silent.ilustriscore.core.utilities.delayedFunction
import com.silent.ilustriscore.core.utilities.showSnackBar
import com.silent.ilustriscore.core.view.BaseView
import presenter.GroupPresenter

class MainActBinder(override val viewBind: ActivityMainBinding): BaseView<Group>() {
    override val presenter = GroupPresenter(this)

    override fun initView() {
        viewBind.loadingAnimation.playAnimation()
        presenter.user?.let {
            presenter.queryData(field = "userID", value = it.uid)
        }
    }

    override fun showListData(list: List<Group>) {
        super.showListData(list)
        val array = ArrayList(list.sortedByDescending { it.tasks.size })
        array.addAll(Group.extraGroups())
        viewBind.groupRecycler.apply {
            adapter = GroupAdapter(array, {
                NewGroupBottomSheet(context) { groupName ->
                    presenter.user?.let {
                        presenter.saveData(Group(userID = it.uid, title = groupName))
                    }
                }.buildDialog()
            }) { group ->
                MaterialAlertDialogBuilder(context)
                    .setTitle("Tem certeza?")
                    .setMessage("Você está prestes a excluir ${group.title}, para prosseguir confirme.")
                    .setPositiveButton("Ok")
                    { dialog, which ->
                        presenter.deleteData(group)
                    }
                    .setNegativeButton("Cancelar")
                    { dialog, which -> dialog.dismiss() }
                    .show()
            }
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when {
                            array[position].id == ABOUT_HEADER -> {
                                2
                            }
                            array[position].id == CREATE_NEW_GROUP_ID -> {
                                2
                            }
                            array[position].isASocialCard() -> {
                                1
                            }
                            else -> {
                                1
                            }
                        }
                    }
                }
            }
        }
        delayedFunction(2000) {
            viewBind.doitchAppbar.setExpanded(false)
        }
    }

    override fun getCallBack(dtoMessage: DTOMessage) {
        super.getCallBack(dtoMessage)
        if (dtoMessage.operationType == OperationType.DATA_SAVED) {
            viewBind.root.showSnackBar(
                "Grupo salvo com sucesso",
                ContextCompat.getColor(context, R.color.colorAccent)
            )
        }
    }
}