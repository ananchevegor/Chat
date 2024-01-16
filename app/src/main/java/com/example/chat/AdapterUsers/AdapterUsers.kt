package com.example.chat.AdapterUsers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chat.Classes.UserChat
import com.example.chat.R

class AdapterUsers(private val context: Activity, private val arrayList: ArrayList<UserChat>) : ArrayAdapter<UserChat>(context,
    R.layout.list_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val username : TextView = view.findViewById(R.id.name)
        val lastMessage: TextView = view.findViewById(R.id.last_message)

        username.text = arrayList[position].json.getString("name")
        lastMessage.text = arrayList[position].json.getString("lastMessage")

        return view
    }

}