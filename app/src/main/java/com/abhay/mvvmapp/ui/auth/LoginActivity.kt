package com.abhay.mvvmapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abhay.mvvmapp.R
import com.abhay.mvvmapp.data.db.AppDatabase
import com.abhay.mvvmapp.data.db.entities.User
import com.abhay.mvvmapp.data.network.MyApi
import com.abhay.mvvmapp.data.network.NetworkConnectionInterceptor
import com.abhay.mvvmapp.data.repositories.UserRepository
import com.abhay.mvvmapp.databinding.ActivityLoginBinding
import com.abhay.mvvmapp.ui.home.HomeActivity
import com.abhay.mvvmapp.util.hide
import com.abhay.mvvmapp.util.show
import com.abhay.mvvmapp.util.snackbar
import com.abhay.mvvmapp.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)



        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->

            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }

        })
    }

    override fun onStarted() {

        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        root_layout.snackbar("${user.name} is Logged In")
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        root_layout.snackbar(message)
        progress_bar.hide()

    }
}
