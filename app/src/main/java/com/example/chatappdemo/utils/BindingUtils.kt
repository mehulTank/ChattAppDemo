package com.example.chatappdemo.utils


import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

import com.example.chatappdemo.data.model.ChatModel
/*
* Binding util adapter
* */
class BindingUtils {

    companion object {


        @JvmStatic
        @BindingAdapter("android:setUserMessage")
        fun setHistoryDetailMessage(view: AppCompatTextView, chat: ChatModel?) {
            try {
                var message = "N/A"
                if (chat != null) {
                    message = chat.message.toString()
                    if (chat.senderUserId == Constans.SENDER_ID) {
                        view.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    } else {

                        view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                    }

                }
                view.text = message

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
