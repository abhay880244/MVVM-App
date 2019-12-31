package com.abhay.mvvmapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abhay.mvvmapp.R
import com.abhay.mvvmapp.data.db.entities.User
import com.abhay.mvvmapp.databinding.ActivityLoginBinding
import com.abhay.mvvmapp.util.hide
import com.abhay.mvvmapp.util.show
import com.abhay.mvvmapp.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {

        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        toast("${user.name} is Logged In")
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        toast(message)
        progress_bar.hide()

    }
}
