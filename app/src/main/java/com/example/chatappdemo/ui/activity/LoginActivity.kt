package com.example.chatappdemo.ui.activity


/*
* Login activity
* */
import android.content.Intent

import android.os.Bundle
import android.text.TextUtils

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.chatappdemo.R
import com.example.chatappdemo.databinding.ActivityLoginBinding
import com.example.chatappdemo.utils.*
import com.example.chatappdemo.viewmodel.login.LoginNavigator
import com.example.chatappdemo.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginNavigator {

    private var activityLoginBinding: ActivityLoginBinding? = null
    private var loginViewModel: LoginViewModel? = null
    private lateinit var loginNavigator: LoginNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponenet()

    }

    private fun initComponenet() {
        loginNavigator = this
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = LoginViewModel(this@LoginActivity)
        activityLoginBinding!!.loginViewModel = loginViewModel
    }

    /*
  *
  * User Login
  * */
    override fun userLogin() {
        val message = loginViewModel?.validateData(this)

        if (TextUtils.isEmpty(message)) {
            if (NetworkUtils.isNetworkConnected(this,true))
                loginViewModel?.userLogin()
        } else {
            CommonUtils.snackbar(
                this.activityLoginBinding?.rootView,
                message,
                true,
                this
            )
        }
    }


    /*
* open main actitivty
* */
    override fun openMainActivity() {
        var intent = Intent(this@LoginActivity, UserListActivity::class.java)
        startActivity(intent)
        intent.putExtra(Constans.MOBILE, loginViewModel?.mobileNumber?.value.toString())
        finishAffinity()
    }

    /*
    * open dialog to alrrt user
    * */
    override fun openRegisterUserDetail() {
        DialogUtils.showConfirmationDialog(
            this,
            getString(R.string.str_confirmation),
            getString(R.string.lbl_you_are_not_register_pelease_fill_detail),
            getString(R.string.btn_ok),
            object :
                OnDialogListener {
                override fun onDialogButtonClicked(btnPosition: Int) {
                    var intent = Intent(this@LoginActivity, UserRegisterActivity::class.java)
                    intent.putExtra(Constans.MOBILE, loginViewModel?.mobileNumber?.value.toString())
                    startActivity(intent)
                    finishAffinity()
                }

            }, isCloseOn = true
        )
    }
}