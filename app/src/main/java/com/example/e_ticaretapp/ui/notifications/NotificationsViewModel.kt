package com.example.e_ticaretapp.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_ticaretapp.models.NotificationObject

class NotificationsViewModel : ViewModel() {
    companion object{
        val notifications: MutableList<NotificationObject> = mutableListOf()
    }

    private val _notificationList = MutableLiveData<List<NotificationObject>>()
    val notificationList: LiveData<List<NotificationObject>> get() = _notificationList

    fun getNotifications(){
        _notificationList.postValue(notifications)
    }
}