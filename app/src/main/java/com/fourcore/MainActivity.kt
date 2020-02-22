package com.fourcore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fourcore.data.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            get<UserRepository>().getAllUsers().map { Log.e("user", it.toString()) }
        }
    }
}
