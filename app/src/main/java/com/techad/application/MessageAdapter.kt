package com.techad.application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.credentials.webauthn.Cbor
import androidx.recyclerview.widget.RecyclerView
import android.content.Context  // CORRECT

import com.google.firebase.auth.FirebaseAuth
import com.techad.application.User_adapter.user_viewholder


class MessageAdapter(val context: Context, val messageList: ArrayList<message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEVIED = 1;
    val ITEM_SENT = 2;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1) {
            //inflate recieve
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.receive, parent, false)
            return RecieveViewHolder(view)
        } else {
            //inflate sent
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]



        if (holder.javaClass == SentViewHolder::class.java) {
            // stuff for sent viewholder

            val viewHolder = holder as SentViewHolder

            holder.sentMessage.text = currentMessage.message

        } else {
            //stuff for recieve viewholder

            val viewHolder = holder as RecieveViewHolder
            holder.recieveMessage.text = currentMessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {

        var currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECEVIED
        }
    }


    override fun getItemCount(): Int {

        return messageList.size

    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)


    }

    class RecieveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recieveMessage = itemView.findViewById<TextView>(R.id.txt_recieve_message)


    }

}
