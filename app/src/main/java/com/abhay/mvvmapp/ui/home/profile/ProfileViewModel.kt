package com.abhay.mvvmapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.abhay.mvvmapp.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {


    val user =repository.getUser()
}
