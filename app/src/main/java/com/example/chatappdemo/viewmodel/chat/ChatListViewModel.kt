package com.example.chatappdemo.viewmodel.chat

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.data.firebase.FirebaseUtils
import com.example.chatappdemo.ChatApplication
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.ChatModel
import com.example.chatappdemo.utils.Constans
import java.util.*


class ChatListViewModel(val context: Context) : Observable() {

    private var chatListNavigator: ChatListNavigator? = null


    val mutableResponse: MutableLiveData<List<ChatModel>> = MutableLiveData()

    lateinit var tempList :List<ChatModel>
    var chatList= ArrayList<ChatModel>()
    private val mIsLoading = ObservableBoolean()

    //var userInputMessage: MutableLiveData<String> = MutableLiveData()



    init {
        chatListNavigator = context as ChatListNavigator
    }

    fun onBackClick() {
        chatListNavigator!!.onBackClick()
    }

    fun onSendMessageClick() {
        chatListNavigator!!.sendMessage()
    }

    fun validateData(
        context: Context,
        userInputMessage: String
    ): String {
        var errorMessage: String = ""

        if (TextUtils.isEmpty(userInputMessage)) {
            errorMessage = context.getString(R.string.str_please_enter_message)
        }

        return errorMessage
    }


    fun getChatList(strReciverUserId: String?): LiveData<List<ChatModel>>? {

        setIsLoading(true)


        var senderUserId =  ChatApplication.mInstance?.getSharedPreferences()
            ?.getString(Constans.PREF_USER_ID, "")
        Constans.SENDER_ID = senderUserId.toString()


        FirebaseUtils().fDbRefChat.addSnapshotListener { data, e ->
            setIsLoading(false)

            if (e != null) {
                Log.e("--- error","--- "+e.toString())
                //channel.close(error)
            } else {
                if (data != null) {
                    chatList.clear()
                    tempList = data.toObjects(ChatModel::class.java)

                    for (a in tempList) {
                        if (a.uniqId.equals(senderUserId.toString() + "_" + strReciverUserId) ||
                            a.uniqId.equals(strReciverUserId.toString() + "_" +senderUserId)
                        ) {

                            chatList.add(a)

                        }
                    }
                    mutableResponse.postValue(chatList)
                } else {
                    //channel.close(CancellationException("No data received"))
                }
            }
        }
        return mutableResponse
    }


    fun SendMessageInFireStore(strReciverUserId: String?, userInputMessage: String) {
        setIsLoading(true)

        val currentTimestamp = System.currentTimeMillis()

        var senderUserId =  ChatApplication.mInstance?.getSharedPreferences()
            ?.getString(Constans.PREF_USER_ID, "")

        val chatMap = HashMap<String, Any>()
        chatMap["message"] = userInputMessage
        chatMap["senderUserId"] = senderUserId.toString()
        chatMap["receiverUserId"] = strReciverUserId.toString()
        chatMap["createdDate"] = currentTimestamp
        chatMap["uniqId"] = senderUserId.toString() + "_" + strReciverUserId


        FirebaseUtils().fDbRefChat.add(chatMap).addOnSuccessListener {

            FirebaseUtils().fDbRefUser.document(strReciverUserId.toString()).update("userLastmessage",userInputMessage)
            FirebaseUtils().fDbRefUser.document(senderUserId.toString()).update("userLastmessage",userInputMessage)
            chatListNavigator!!.resetChatUi()
            setIsLoading(false)




        }.addOnFailureListener {
            setIsLoading(false)

        }


    }




    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }


    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

}