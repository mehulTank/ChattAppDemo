package com.example.chatappdemo.ui.activity

/*
*
* User register screen
* */

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatappdemo.R
import com.example.chatappdemo.databinding.ActivityUserRegisterBinding
import com.example.chatappdemo.utils.*
import com.example.chatappdemo.viewmodel.register.UserRegisterNavigator
import com.example.chatappdemo.viewmodel.register.UserRegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*

class UserRegisterActivity : AppCompatActivity(), UserRegisterNavigator {

    private var activityUserRegisterBinding: ActivityUserRegisterBinding? = null
    private var userRegisterViewModel: UserRegisterViewModel? = null
    private lateinit var userRegisterNavigator: UserRegisterNavigator


    var strMobileNumber: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponenet()

    }
/*
* init componenet
* */
    private fun initComponenet() {
        userRegisterNavigator = this
        activityUserRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_register)
        userRegisterViewModel = UserRegisterViewModel(this@UserRegisterActivity)
        activityUserRegisterBinding!!.registerViewModel = userRegisterViewModel

        strMobileNumber = intent.getStringExtra(Constans.MOBILE)
    }


    /*
    * Finction for user register
    * */
    override fun userRegister() {

        val message = userRegisterViewModel?.validateData(this)

        if (TextUtils.isEmpty(message)) {
            if (NetworkUtils.isNetworkConnected(this,true))
                userRegisterViewModel?.userRegister(strMobileNumber)
        } else {
            CommonUtils.snackbar(
                this.activityUserRegisterBinding!!.rootView,
                message,
                true,
                this
            )
        }

    }
/*
* Oopen user list screen
* */
    override fun openUserListScreen() {
        var intent = Intent(this@UserRegisterActivity, UserListActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

}