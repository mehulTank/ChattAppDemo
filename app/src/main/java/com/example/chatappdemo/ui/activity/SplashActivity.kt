package com.example.chatappdemo.ui.activity


/*
*
* Splash screen
* */
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.chatappdemo.ChatApplication
import com.example.chatappdemo.R
import com.example.chatappdemo.utils.Constans


class SplashActivity : AppCompatActivity() {


    private var mHandler: Handler? = null
    private val delay: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        initCompnenet()


    }

    private fun initCompnenet() {

        ChatApplication.setmInstance(application as ChatApplication)
        mHandler = Handler()
        mHandler!!.postDelayed(runnable, delay)


    }

    val runnable: Runnable = Runnable {
        if (!isFinishing) {


            val isLogin = ChatApplication.mInstance?.getSharedPreferences()
                ?.getBoolean(Constans.PREF_IS_USER_LOGIN, false)



            if (!isLogin!!) {
                val mMenuIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(mMenuIntent)
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out)
                finish()

            } else {

                val mMainActivity = Intent(this@SplashActivity, UserListActivity::class.java)
                startActivity(mMainActivity)
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out)
                finish()


            }


        }
    }


}
