package com.ilustris.doitch.binder

import android.app.Activity
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ilustris.doitch.R
import com.ilustris.doitch.adapter.GroupAdapter
import com.ilustris.doitch.base.models.CREATE_NEW_GROUP_ID
import com.ilustris.doitch.base.models.Group
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
        val array = ArrayList(list)
        array.add(Group(id = CREATE_NEW_GROUP_ID))
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
        }
        delayedFunction {
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