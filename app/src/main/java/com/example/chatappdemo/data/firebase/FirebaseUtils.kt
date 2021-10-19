package com.example.chatapp.data.firebase


/*
* Firebase util located firebase path
* */

import com.example.chatappdemo.utils.Constans.USER_CHAT
import com.example.chatappdemo.utils.Constans.USER_PATH
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUtils {
        val fDbRefUser = FirebaseFirestore.getInstance().collection(USER_PATH)
        val fDbRefChat = FirebaseFirestore.getInstance().collection(USER_CHAT)
}