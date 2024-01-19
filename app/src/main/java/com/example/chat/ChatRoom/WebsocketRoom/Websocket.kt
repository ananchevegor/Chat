package com.example.chat.ChatRoom.WebsocketRoom

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import com.example.chat.ChatRoom.AdapterChatRoom.AdapterChatRoom
import com.example.chat.ChatRoom.ChatRoom
import com.example.chat.Classes.ChatRoomClass
import com.example.chat.databinding.ActivityChatRoomBinding
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class Websocket(
    private val binding: ActivityChatRoomBinding,
    private val activity: ChatRoom,
    private val messagesList: ArrayList<ChatRoomClass>,
    private val sharedPreferences: SharedPreferences
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i("conn", response.toString())
    }
    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.i("conn", text)
        activity.runOnUiThread {
            messagesList.add(ChatRoomClass(JSONObject(text)))
            binding.listviewMessages.adapter = AdapterChatRoom(activity, messagesList, sharedPreferences)
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(code, null)
    }

    @SuppressLint("SetTextI18n")
    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

    }

}