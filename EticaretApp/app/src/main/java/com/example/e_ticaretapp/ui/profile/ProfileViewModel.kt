package com.example.e_ticaretapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_ticaretapp.api.ApiConfig
import com.example.e_ticaretapp.models.UpdateData
import com.example.e_ticaretapp.models.UserInfo
import com.example.e_ticaretapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    fun getUserInfo(){
        val client = ApiConfig.getApiService().getUserInfoById(Utils.user?.id.toString())

        client.enqueue(object: Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null){
                    return
                }
                _userInfo.postValue(responseBody as UserInfo)
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun updateUser(updatedText: String, textId: String){

        var updateData = UpdateData()

        when(textId){
            "firstName" -> updateData = UpdateData(firstName = updatedText)
            "lastName" -> updateData = UpdateData(lastName = updatedText)
            "age" -> updateData = UpdateData(age = updatedText)
            "gender" -> updateData = UpdateData(gender = updatedText)
            "email" -> updateData = UpdateData(email = updatedText)
            "phone" -> updateData = UpdateData(phone = updatedText)
        }

        val client = ApiConfig.getApiService().updateUser(Utils.user?.id.toString(), updateData)

        client.enqueue(object: Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null){
                    return
                }
                _userInfo.postValue(responseBody as UserInfo)
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}