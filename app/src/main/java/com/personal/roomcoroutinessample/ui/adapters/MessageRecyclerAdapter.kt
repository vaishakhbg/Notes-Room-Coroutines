package com.personal.roomcoroutinessample.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.personal.roomcoroutinessample.BR
import com.personal.roomcoroutinessample.R
import com.personal.roomcoroutinessample.databinding.MessageitemBinding
import com.personal.roomcoroutinessample.entity.Message

class MessageRecyclerAdapter(private val allMessages : MutableList<Message>,private val listener : CustomViewListener) : RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder>()  {


    interface  CustomViewListener{
        fun onCustomItemClicked(x : Message)
    }

    override fun onBindViewHolder(holder: MessageRecyclerAdapter.ViewHolder, position: Int) {
        val message = allMessages.get(position)
        holder.bind(message,listener)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageRecyclerAdapter.ViewHolder {
        val messageitemBinding : MessageitemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.messageitem,parent,false)
        return ViewHolder(messageitemBinding)
    }

    override fun getItemCount()=allMessages.size

class ViewHolder(messageItemBinding : MessageitemBinding): RecyclerView.ViewHolder(messageItemBinding.root){


    private  var messageItem = messageItemBinding
    fun bind(obj:Message,listener: CustomViewListener){
        messageItem.setVariable(BR.model,obj)

        val card = messageItem.root.findViewById<CardView>(R.id.carditem)
        card.setOnClickListener{
                listener.onCustomItemClicked(obj)
        }
        messageItem.executePendingBindings()

}
}
}