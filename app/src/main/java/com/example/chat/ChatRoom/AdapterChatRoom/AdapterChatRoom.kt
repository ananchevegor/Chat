package com.example.chat.ChatRoom.AdapterChatRoom

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.chat.Classes.ChatRoomClass
import com.example.chat.Classes.UserChat
import com.example.chat.R
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams

class AdapterChatRoom(private val context: Activity, private val arrayList: ArrayList<ChatRoomClass>) : ArrayAdapter<ChatRoomClass>(context,
R.layout.list_item, arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_messages, null)

        val message : TextView = view.findViewById(R.id.messageUsers)
        val layoutParams = message.layoutParams as LinearLayout.LayoutParams

        if (arrayList[position].userMessage.getString("user_id") == "7") {
            layoutParams.gravity = Gravity.END
        }else{
            layoutParams.gravity = Gravity.START
            layoutParams.leftMargin = 16
        }



        // Применяем изменения
        message.layoutParams = layoutParams
        message.text = arrayList[position].userMessage.getString("message")

        return view
    }
}