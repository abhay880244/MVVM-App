package com.abhay.mvvmapp.ui.auth

import com.abhay.mvvmapp.data.db.entities.User

interface AuthListener {

    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}