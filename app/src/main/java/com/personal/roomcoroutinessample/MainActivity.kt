package com.personal.roomcoroutinessample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.personal.roomcoroutinessample.apiservice.ApiClient
import com.personal.roomcoroutinessample.ui.MainFragment
import kotlinx.coroutines.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmenthere,
            MainFragment()
        ).commit()
        
    }


    
            
        
    
}
