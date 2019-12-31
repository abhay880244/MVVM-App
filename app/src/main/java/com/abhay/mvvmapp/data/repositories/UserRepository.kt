package com.abhay.mvvmapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhay.mvvmapp.data.db.AppDatabase
import com.abhay.mvvmapp.data.db.entities.User
import com.abhay.mvvmapp.data.network.MyApi
import com.abhay.mvvmapp.data.network.SafeApiRequest
import com.abhay.mvvmapp.data.network.responses.AuthResponse
import com.abhay.mvvmapp.util.ApiException
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {


    suspend fun userLogin(email: String, password: String): AuthResponse {
//        val loginResponse = MutableLiveData<String>()
//
//        MyApi().userLogin(email, password).enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                loginResponse.value = t?.message
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    loginResponse.value = response.body()?.string()
//                } else {
//                    loginResponse.value = response.errorBody()?.string()
//                }
//
//            }
//
//        })

//        return MyApi().userLogin(email, password)

        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getDao().upsert(user)

     fun getUser() = db.getDao().getUser()
}