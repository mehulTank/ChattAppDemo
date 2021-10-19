package com.example.chatappdemo.viewmodel.register

import android.content.Context
import android.text.TextUtils
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.data.firebase.FirebaseUtils
import com.example.chatappdemo.ChatApplication
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.User
import com.example.chatappdemo.utils.CommonUtils
import com.example.chatappdemo.utils.Constans
import java.util.*




class UserRegisterViewModel(val context: Context) : Observable() {

    private var userRegisterNavigator: UserRegisterNavigator? = null


    var userName: MutableLiveData<String> = MutableLiveData()
    var userEmail: MutableLiveData<String> = MutableLiveData()


    private val mIsLoading = ObservableBoolean()

    init {
        userRegisterNavigator = context as UserRegisterNavigator
    }

    fun onUserRegisterClick() {
        userRegisterNavigator!!.userRegister()
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }


    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

    fun validateData(
        context: Context
    ): String {
        var errorMessage: String = ""

        if (TextUtils.isEmpty(userName.value)) {
            errorMessage = context.getString(R.string.str_please_enter_user_name)
        } else if (TextUtils.isEmpty(userEmail.value)) {
            errorMessage = context.getString(R.string.str_please_enter_email)
        } else if (!CommonUtils.isValidEmail(userEmail.value)) {
            errorMessage = context.getString(R.string.str_enter_valid_email)
        }

        return errorMessage
    }


    fun userRegister(strMobileNumber: String?) {
        setIsLoading(true)

        val user = HashMap<String, Any>()
        user["userEmail"] = userEmail.value.toString()
        user["userName"] = userName.value.toString()
        user["mobileNumber"] = strMobileNumber.toString()
        user["userLastmessage"] = ""

        FirebaseUtils().fDbRefUser.whereEqualTo("mobileNumber", strMobileNumber.toString()).get()
            .addOnSuccessListener {

                if (it.isEmpty) {
                    FirebaseUtils().fDbRefUser.add(user).addOnSuccessListener {
                        setIsLoading(false)


                        FirebaseUtils().fDbRefUser.document(it.id).update("userId",it.id).addOnSuccessListener {

                        }
                        FirebaseUtils().fDbRefUser.document(it.id).get().addOnSuccessListener {
                            var user : User
                            user = it.toObject(User::class.java)!!

                            ChatApplication.mInstance!!.savePreferenceDataBoolean(Constans.PREF_IS_USER_LOGIN,true)
                            ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_ID,user.userId.toString())
                            ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_EMAIL,user.userEmail.toString())
                            ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_MOBILE_NUMBER,user.mobileNumber.toString())
                            ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_NAME,user.userName.toString())
                            ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_LAST_MESSAGE,user.userLastmessage.toString())


                            userRegisterNavigator!!.openUserListScreen()
                        }
                    }.addOnFailureListener {
                        setIsLoading(false)
                    }
                } else {
                    setIsLoading(false)
                }
            }.addOnFailureListener {
                setIsLoading(false)
            }

    }
}