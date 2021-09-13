package com.ilustris.doitch.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilustris.doitch.R
import com.ilustris.doitch.databinding.BottomsheetInputBinding

class NewGroupBottomSheet(val context: Context, val sendGroup: (String) -> Unit) {

    val dialog = BottomSheetDialog(context)

    val view: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.bottomsheet_input, null, false).rootView
    }

    fun buildDialog() {
        dialog.apply {
            setContentView(view)
            val newGroupLayoutBinding = DataBindingUtil.bind<BottomsheetInputBinding>(view)?.run {
                groupName.addTextChangedListener {
                    saveButton.isEnabled = it!!.isNotEmpty()
                }

                saveButton.setOnClickListener {
                    sendGroup.invoke(groupName.text.toString())
                    //dialog.dismiss()
                }
            }
        }.show()
    }
}