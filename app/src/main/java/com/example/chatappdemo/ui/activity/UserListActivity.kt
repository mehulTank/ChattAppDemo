package com.example.chatappdemo.ui.activity



/*
* User list screen
*
* */
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.User
import com.example.chatappdemo.databinding.ActivityUserListBinding
import com.example.chatappdemo.ui.adapter.UserListAdapter
import com.example.chatappdemo.ui.interfaces.OnRecylerItemClickListener
import com.example.chatappdemo.utils.Constans
import com.example.chatappdemo.utils.NetworkUtils
import com.example.chatappdemo.viewmodel.userList.UserListNavigator
import com.example.chatappdemo.viewmodel.userList.UserListViewModel
import kotlinx.android.synthetic.main.activity_login.*

class UserListActivity : AppCompatActivity(), UserListNavigator {

    private var activityUserListBinding: ActivityUserListBinding? = null
    private var userListViewModel: UserListViewModel? = null
    private lateinit var userListNavigator: UserListNavigator

    private lateinit var userListAdapter: UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponenet()
        setUpAdapter()
        SetUpObserverList()

    }
/*
* Intialize component
* */
    private fun initComponenet() {
        userListNavigator = this
        activityUserListBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        userListViewModel = UserListViewModel(this@UserListActivity)
        activityUserListBinding!!.userListViewModel = userListViewModel
    }


/*
* Set up Recycler view adapter
*
* */
    private fun setUpAdapter() {

        userListAdapter = UserListAdapter(this,
            object : OnRecylerItemClickListener<User> {

                override fun onViewItemClicked(obj: User, pos: Int) {

                    var intent = Intent(this@UserListActivity, ChatListActivity::class.java)
                    intent.putExtra(Constans.RECIVER_USER_ID, obj.userId.toString())
                    intent.putExtra(Constans.USER_NAME, obj.userName.toString())

                    startActivity(intent)
                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out)

                }
            })
        var mLinearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        activityUserListBinding!!.rvUserList.layoutManager = mLinearLayoutManager
        activityUserListBinding!!.rvUserList.adapter =
            userListAdapter
    }

/*
*
* Set up obsever
* */
    private fun SetUpObserverList() {


        if (NetworkUtils.isNetworkConnected(this,true)) {


            userListViewModel!!.getAllHistory()!!.observe(this,
                Observer {
                    userListAdapter.clearList()
                    userListAdapter.setList(it)
                    try {

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })




        }


    }
/*
* On logout click handle
* */
    override fun onLogOutCkick() {

        var intent = Intent(this@UserListActivity, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out)
        finish()
    }


}