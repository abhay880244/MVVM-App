package com.abhay.mvvmapp

import android.app.Application
import com.abhay.mvvmapp.data.db.AppDatabase
import com.abhay.mvvmapp.data.network.MyApi
import com.abhay.mvvmapp.data.network.NetworkConnectionInterceptor
import com.abhay.mvvmapp.data.repositories.UserRepository
import com.abhay.mvvmapp.ui.auth.AuthViewModelFactory
import com.abhay.mvvmapp.ui.home.profile.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication  : Application(),KodeinAware{

    override val kodein =Kodein.lazy {
        import(androidXModule(this@MVVMApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
    }
}