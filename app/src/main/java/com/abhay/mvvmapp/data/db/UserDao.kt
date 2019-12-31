package com.abhay.mvvmapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhay.mvvmapp.data.db.entities.CURRENT_USER_ID
import com.abhay.mvvmapp.data.db.entities.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User): Long

    @Query("select * from user where uid=$CURRENT_USER_ID")
    fun getUser(): LiveData<User>
}