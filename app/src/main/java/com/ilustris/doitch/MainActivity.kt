package com.ilustris.doitch

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.creat.motiv.utilities.RC_SIGN_IN
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ilustris.doitch.binder.MainActBinder
import com.ilustris.doitch.databinding.ActivityMainBinding
import com.silent.ilustriscore.core.utilities.showSnackBar

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var mainBinding: MainActBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).run {
           mainBinding = MainActBinder(this)
        }
        checkUser()
    }

    private fun checkUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            signIn()
        } else {
            mainBinding.initView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
          mainBinding.initView()
        } else {
            showSnackBar(this,"Ocorreu um erro ao fazer login", Color.RED, Color.WHITE, mainBinding.viewBind.root)
        }
    }

    private fun signIn() {
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build())
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
            .setLogo(R.mipmap.ic_launcher)
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_Doitch)
            .build(), RC_SIGN_IN)
    }
}