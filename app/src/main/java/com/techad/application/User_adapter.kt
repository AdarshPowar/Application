package com.techad.application

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class User_adapter(val context: Context, val userList:ArrayList<User> ): RecyclerView.Adapter<User_adapter.user_viewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): user_viewholder {
        val view:View= LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return user_viewholder(view)

    }

    override fun getItemCount(): Int {
        return userList.size

    }

    override fun onBindViewHolder(holder: user_viewholder, position: Int) {
        val currentUser=userList[position]
        holder.txtname.text=currentUser.name

        holder.itemView.setOnClickListener{
            val intent= Intent(context,ChatActivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)

        }

    }
    class user_viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtname=itemView.findViewById<TextView>(R.id.txt_name)

    }
}