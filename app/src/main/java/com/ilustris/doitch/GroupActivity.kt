package com.ilustris.doitch

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ilustris.doitch.base.models.Group
import com.ilustris.doitch.binder.GroupActBinder

class GroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        GroupActBinder(
            DataBindingUtil.setContentView(this, R.layout.activity_group),
            (intent.getSerializableExtra(GROUP) as Group).id
        ).initView()
    }

    companion object {
        private const val GROUP = "SELECTEDGROUP"
        fun launchIntent(context: Context, group: Group, view: View) {
            val p2: Pair<View, String>? = Pair.create(view, "grouptitle")
            val options = ActivityOptions.makeSceneTransitionAnimation((context as Activity), p2)
            context.startActivity(Intent(
                context,
                GroupActivity::class.java
            )
                .apply { putExtra(GROUP, group) }, options.toBundle()
            )
        }
    }

}