package com.example.chatappdemo.viewmodel.userList

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.data.firebase.FirebaseUtils
import com.example.chatappdemo.ChatApplication
import com.example.chatappdemo.data.model.User
import com.example.chatappdemo.utils.Constans
import io.reactivex.disposables.CompositeDisposable
import java.util.*


class UserListViewModel(val context: Context) : Observable() {

    private var userListNavigator: UserListNavigator? = null


    val mutableResponse: MutableLiveData<List<User>> = MutableLiveData()

    lateinit var tempList :List<User>
    var userList= ArrayList<User>()
    private val mIsLoading = ObservableBoolean()

    init {
        userListNavigator = context as UserListNavigator
    }


    fun getAllHistory(): LiveData<List<User>>? {

        setIsLoading(true)

        var currentUserMobileNumber =
            ChatApplication.mInstance?.getSharedPreferences()
                ?.getString(Constans.PREF_USER_MOBILE_NUMBER, "")



        FirebaseUtils().fDbRefUser.whereNotEqualTo("mobileNumber", currentUserMobileNumber).addSnapshotListener{
                data,e->
            setIsLoading(false)
            if (e != null) {

            } else {
                if (data != null) {

                    userList.clear()
                    tempList = data.toObjects(User::class.java)

                    for (a in tempList) {
                        if(a.mobileNumber.toString() != currentUserMobileNumber.toString())
                        {
                            userList.add(a)
                        }
                    }
                    mutableResponse.postValue(userList)

                } else {

                }
            }
        }
        return mutableResponse
    }

    fun onLogOutCkick() {

        ChatApplication.mInstance?.clearPreference()
        userListNavigator!!.onLogOutCkick()
    }



    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }


    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

}