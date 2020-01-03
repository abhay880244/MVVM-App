package com.abhay.mvvmapp.data.repositories

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhay.mvvmapp.data.db.AppDatabase
import com.abhay.mvvmapp.data.db.entities.Quote
import com.abhay.mvvmapp.data.network.MyApi
import com.abhay.mvvmapp.data.network.SafeApiRequest
import com.abhay.mvvmapp.data.preferences.PreferenceProvider
import com.abhay.mvvmapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit


class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {

            fetchQuotes()
            db.quoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {

        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(lastSavedAt)) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: String): Boolean {

        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
        val startDate = Date()

        var endDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(savedAt)

        val duration = endDate.time - startDate.time


        var diffInHours = TimeUnit.MILLISECONDS.toHours(duration)
        return (diffInHours > 6)


    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {

            val dt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val stringdate = dt.format(Date())
            prefs.saveLastSavedAt(stringdate)

            db.quoteDao().saveAllQuotes(quotes)
        }

    }
}