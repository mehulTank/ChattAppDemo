package com.example.chatappdemo.viewmodel.login

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.data.firebase.FirebaseUtils
import com.example.chatappdemo.ChatApplication
import com.example.chatappdemo.R
import com.example.chatappdemo.data.model.User
import com.example.chatappdemo.utils.Constans
import io.reactivex.disposables.CompositeDisposable
import java.util.*


class LoginViewModel(val context: Context) : Observable() {

    private var loginNavigator: LoginNavigator? = null
    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    var mobileNumber: MutableLiveData<String> = MutableLiveData()

    private val mIsLoading = ObservableBoolean()

    init {
        loginNavigator = context as LoginNavigator
    }

    fun onUserLoginClick() {
        loginNavigator!!.userLogin()
    }


    fun userLogin() {
        setIsLoading(true)



        FirebaseUtils().fDbRefUser.whereEqualTo("mobileNumber", mobileNumber.value.toString()).get()
            .addOnSuccessListener {
                setIsLoading(false)
                Log.d("-----",""+it.documents.size)

                if (it.isEmpty) {
                    loginNavigator?.openRegisterUserDetail()

                } else {

                    var user : User
                    user = it.documents.get(0).toObject(User::class.java)!!


                    ChatApplication.mInstance!!.savePreferenceDataBoolean(Constans.PREF_IS_USER_LOGIN,true)
                    ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_ID,user.userId.toString())
                    ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_EMAIL,user.userEmail.toString())
                    ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_MOBILE_NUMBER,user.mobileNumber.toString())
                    ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_NAME,user.userName.toString())
                    ChatApplication.mInstance!!.savePreferenceDataString(Constans.PREF_USER_LAST_MESSAGE,user.userLastmessage.toString())

                    /*ChatApplication.mInstance?.savePreferenceDataBoolean()
                        ?.getBoolean(ChatApplication.mInstance?.applicationContext?.getString(R.string.preferances_islogin), true)*/
                    //dataManager.saveUserData(user)
                    loginNavigator!!.openMainActivity()
                }


            }.addOnFailureListener {
                setIsLoading(false)

            }


    }


    fun validateData(
        context: Context
    ): String {
        var errorMessage: String = ""

        if (TextUtils.isEmpty(mobileNumber.value)) {
            errorMessage = context.getString(R.string.str_enter_mobile_number)
        } else if (mobileNumber.value!!.length < 10) {
            errorMessage = context.getString(R.string.str_enter_valid_mobile_number)
        }

        return errorMessage
    }


    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }


    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

}