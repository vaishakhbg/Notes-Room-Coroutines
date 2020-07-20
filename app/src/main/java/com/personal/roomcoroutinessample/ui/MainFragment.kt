package com.personal.roomcoroutinessample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.personal.roomcoroutinessample.R
import com.personal.roomcoroutinessample.databinding.MainfragmentBinding
import com.personal.roomcoroutinessample.databinding.MessageitemBinding
import com.personal.roomcoroutinessample.entity.Message
import com.personal.roomcoroutinessample.ui.adapters.MessageRecyclerAdapter
import com.personal.roomcoroutinessample.viewmodel.MainViewModel
import com.personal.roomcoroutinessample.viewmodel.VMFactory

class MainFragment : Fragment(){

    private lateinit var viewModel: MainViewModel
    private  lateinit var mainfragmentBinding: MainfragmentBinding
    private lateinit var animView : LottieAnimationView

    private lateinit var listener:MessageRecyclerAdapter.CustomViewListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = VMFactory(requireActivity().application)
        viewModel= ViewModelProvider(requireActivity(),viewModelFactory).get(MainViewModel::class.java)
        subscribe(viewModel)
        mainfragmentBinding = DataBindingUtil.inflate(inflater, R.layout.mainfragment,
            container,false)
        populateMessages()
        return mainfragmentBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {


            val args = Bundle()
            args.putBoolean("message",false)
            openMessageFragment(args)

        }

        animView = view.findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        animView.playAnimation()

        listener = object : MessageRecyclerAdapter.CustomViewListener{
            override fun onCustomItemClicked(x: Message) {
                Toast.makeText(requireContext(),x.id.toString(),Toast.LENGTH_SHORT).show()
                viewModel._editMessage.value = x
                val args = Bundle()
                args.putBoolean("message",true)
                openMessageFragment(args)
            }


        }



        val refresh = view.findViewById<ImageButton>(R.id.refresh)
        refresh.setOnClickListener{
            populateMessages()
        }
    }

    private fun populateMessages() {
        viewModel.getAllMessages()
    }

    private fun subscribe(viewModel: MainViewModel) {
        viewModel.allMessageList.observe(requireActivity(), Observer { newList ->
            if(newList.isNullOrEmpty()){
                animView.playAnimation()
            }else{
                animView.cancelAnimation()
                animView.visibility = View.GONE
            }

            val messageRecyclerAdapter = MessageRecyclerAdapter(newList,listener)
            mainfragmentBinding.myadapter = messageRecyclerAdapter

        })


    }

    /*.setCustomAnimations(R.anim.slide_in_right,R.anim.fragment_fade_exit, R.anim.fragment_fade_enter,
    R.anim.slide_out_right)
    .replace()
    .addToBackStack(null)
    .commit()*/

    fun openMessageFragment(bundle : Bundle?){
        val fragmentTxn = requireActivity().supportFragmentManager.beginTransaction()
        val newMessageFragment = NewMessageFragment()
        if(bundle != null){
            newMessageFragment.arguments = bundle
        }
        fragmentTxn.setCustomAnimations(R.anim.slide_in_right,R.anim.fragment_fade_exit, R.anim.fragment_fade_enter,
        R.anim.slide_out_right)
        fragmentTxn.replace(R.id.fragmenthere, newMessageFragment)
        fragmentTxn.addToBackStack(null)
        fragmentTxn.commit()


    }
}