package com.abhay.mvvmapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abhay.mvvmapp.data.db.entities.CURRENT_USER_ID
import com.abhay.mvvmapp.data.db.entities.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User): Long

    @Query("select * from user where uid=$CURRENT_USER_ID")
    fun getUser(): LiveData<User>

    @Delete
    suspend fun deleteUser(user: User)
}