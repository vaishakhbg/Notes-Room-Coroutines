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
        /*createCo()
        Log.d("reciprocal","1");*/
    }


    fun createCo(){
        val service = ApiClient.getClient()
        CoroutineScope(Dispatchers.IO).launch {
            delay(5000)
            Log.d("reciprocal","2");
            val response = service.getCity("12.884665","74.860102","aa9a27e3bbe36c45d90d7851c687d904")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity,"Success " + response.body()!!.name,Toast.LENGTH_LONG).show()

                        //Do something with response e.g show to the UI.
                    } else {
                        Toast.makeText(this@MainActivity,"Error: ${response.code()}",Toast.LENGTH_LONG).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(this@MainActivity,"Exception ${e.message}",Toast.LENGTH_LONG).show()

                } catch (e: Throwable) {
                    Toast.makeText(this@MainActivity,"Ooops: Something else went wrong",Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}
