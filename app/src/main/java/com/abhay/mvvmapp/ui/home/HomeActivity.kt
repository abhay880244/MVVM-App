package com.abhay.mvvmapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.abhay.mvvmapp.R
import com.abhay.mvvmapp.data.db.AppDatabase
import com.abhay.mvvmapp.data.db.entities.User
import com.abhay.mvvmapp.data.network.MyApi
import com.abhay.mvvmapp.data.network.NetworkConnectionInterceptor
import com.abhay.mvvmapp.data.repositories.UserRepository
import com.abhay.mvvmapp.databinding.ActivityHomeBinding
import com.abhay.mvvmapp.databinding.ActivityLoginBinding
import com.abhay.mvvmapp.ui.auth.AuthViewModel
import com.abhay.mvvmapp.ui.auth.AuthViewModelFactory
import com.abhay.mvvmapp.util.Coroutines
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.Internal.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class HomeActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, KodeinAware {


    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    var loggedInUser: User? = null


    var viewModel: AuthViewModel? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.quotesFragment -> {
                Navigation.findNavController(
                    this,
                    R.id.fragment
                ).navigate(R.id.actionQuotes)
            }

            R.id.loginActivity -> {
                Navigation.findNavController(
                    this,
                    R.id.fragment
                ).navigate(R.id.actionLogOut)
                drawer_layout.closeDrawer(GravityCompat.START)
                delete()
                finish()

            }


//                R.id.quotesFragment->
//            R.id.loginActivity->

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true


    }

    private fun delete() {

        if (loggedInUser != null) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel?.deleteUser(loggedInUser!!)
            }


        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)


        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)



        binding.viewmodel = viewModel

//        viewModel.authListener = this


        viewModel!!.getLoggedInUser().observe(this, Observer { user ->

            if (user != null) {
                loggedInUser = user
            }

        })

        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)


        val mNavigationView = nav_view as NavigationView

        mNavigationView?.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment), drawer_layout
        )
    }


}
