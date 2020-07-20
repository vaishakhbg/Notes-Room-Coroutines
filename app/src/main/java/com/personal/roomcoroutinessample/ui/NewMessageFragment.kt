package com.personal.roomcoroutinessample.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.personal.roomcoroutinessample.R
import com.personal.roomcoroutinessample.entity.Message
import com.personal.roomcoroutinessample.viewmodel.MainViewModel
import com.personal.roomcoroutinessample.viewmodel.VMFactory
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class NewMessageFragment : Fragment(),CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private lateinit var viewModel: MainViewModel
    private lateinit var messageBox : EditText

    private val MY_PERMISSIONS_REQUEST_LOCATION : Int = 1

    private  var cityName :String? = null
    private  var formerMessage : Message? = null
    private var fromPrevious : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory = VMFactory(requireActivity().application)
        viewModel= ViewModelProvider(requireActivity(),viewModelFactory).get(MainViewModel::class.java)
        try {
            if(arguments!!.getBoolean("message")) {
                formerMessage = viewModel._editMessage.value
            }
        }catch(e : Exception){
            e.printStackTrace()
        }
        return inflater.inflate(R.layout.messagefragment,container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageBox = view.findViewById<EditText>(R.id.message)
        if(formerMessage != null){
            val msg: String = formerMessage!!.messageString!!
            Log.d("vaishak-vbg",msg);
            if(!msg.isNullOrBlank()) {
                messageBox.setText(msg)
            }
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            } else {

                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        } else {
            launch {setCityName()

            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {

                    if ((ContextCompat.checkSelfPermission(requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()

                        launch { setCityName() }




                            }else{

                        cityName = "Somewhere"

                    }

            }
        }
    }
}

    @SuppressLint("MissingPermission")
    suspend fun setCityName() {
        withContext(Dispatchers.IO) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            try {
                val lastKnownLoc =
                    locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER) as Location
                val lat = lastKnownLoc.latitude.toString()
                val lon = lastKnownLoc.longitude.toString()

               
                /*val stuff = async { viewModel.getCityFromCoordinates(lat, lon)}
            cityName = stuff.await()*/
                cityName = viewModel.getCityFromCoordinates(lat, lon)
            }catch(e : Exception){

                cityName = "Nowhere"
        }

        }
    }






    override fun onDetach() {

        if(!messageBox.text.isNullOrBlank()){
            val message = Message()
            message.messageString = messageBox.text.toString()
            message.date="08/07/2020"
            message.time="19:20 PM"
            message.location =cityName

            if(formerMessage == null) {
                viewModel.setMessage(message)
            }else {
                val id : Int = formerMessage!!.id!!
                message.id = id
                viewModel.updateMessage(message)
            }
        }
        //message.messageString = messageBox.text!

        super.onDetach()
    }



}