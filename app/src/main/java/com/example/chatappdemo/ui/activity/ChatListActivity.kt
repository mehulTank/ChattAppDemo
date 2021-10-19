package com.example.chatappdemo.ui.activity


/*
* Chat actitivty
* */

import android.os.Bundle
import android.text.TextUtils

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.ChatModel
import com.example.chatappdemo.databinding.ActivityChatListBinding
import com.example.chatappdemo.ui.adapter.ChatListAdapter
import com.example.chatappdemo.ui.interfaces.OnRecylerItemClickListener
import com.example.chatappdemo.utils.CommonUtils
import com.example.chatappdemo.utils.Constans
import com.example.chatappdemo.utils.NetworkUtils
import com.example.chatappdemo.viewmodel.chat.ChatListNavigator
import com.example.chatappdemo.viewmodel.chat.ChatListViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.ArrayList

class ChatListActivity : AppCompatActivity(), ChatListNavigator {

    private var activityChatListBinding: ActivityChatListBinding? = null
    private var chatListViewModel: ChatListViewModel? = null
    private lateinit var chatListNavigator: ChatListNavigator

    private lateinit var chatListAdapter: ChatListAdapter


    var strReciverUserId: String? = null
    var strUserName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        strReciverUserId = intent.getStringExtra(Constans.RECIVER_USER_ID)
        strUserName = intent.getStringExtra(Constans.USER_NAME)

        initComponenet()
        setUpAdapter()
        getChatListData()


    }

    /*
*
*  Intialize componenet
* */

    private fun initComponenet() {
        chatListNavigator = this
        activityChatListBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat_list)
        chatListViewModel = ChatListViewModel(this@ChatListActivity)
        activityChatListBinding!!.chatListViewModel = chatListViewModel

        activityChatListBinding!!.txtToolbarTitle.setText(strUserName)
    }



    /*
*
* This function define for sending message
* */
    override fun sendMessage() {
        val message = chatListViewModel!!.validateData(this,activityChatListBinding!!.etChat.text.toString())

        if (TextUtils.isEmpty(message)) {
            if (NetworkUtils.isNetworkConnected(this,true))
                chatListViewModel!!.SendMessageInFireStore(strReciverUserId,activityChatListBinding!!.etChat.text.toString())
        } else {
            CommonUtils.snackbar(
                this.activityChatListBinding!!.rootView,
                message,
                true,
                this
            )
        }
    }


    /*
   * Set up adaapter
   * */
    private fun setUpAdapter() {

        chatListAdapter = ChatListAdapter(this,
            object : OnRecylerItemClickListener<ChatModel> {

                override fun onViewItemClicked(obj: ChatModel, pos: Int) {

                }
            })



        var mLinearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        activityChatListBinding!!.rvUserList.layoutManager = mLinearLayoutManager

        activityChatListBinding!!.rvUserList.adapter =
            chatListAdapter
    }


    private fun getChatListData() {

        if (NetworkUtils.isNetworkConnected(this,true)) {

            var chatList: List<ChatModel>
            chatListViewModel!!.getChatList(strReciverUserId)!!.observe(this,
                Observer {
                    chatList = it.sortedWith(compareBy(ChatModel::createdDate))
                    chatList.reversed()
                    chatListAdapter.clearList()
                    chatListAdapter.setList(chatList)
                    activityChatListBinding!!.rvUserList.scrollToPosition(it.size-1)

                })
        }

    }


    /*
   *  On back click handle
   * */
    override fun onBackClick() {
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out)
        finish()
    }



    override fun resetChatUi() {
        activityChatListBinding!!.etChat.setText("")

    }


}