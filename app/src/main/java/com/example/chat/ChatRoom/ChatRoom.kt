package com.example.chat.ChatRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chat.AdapterUsers.AdapterUsers
import com.example.chat.ChatRoom.AdapterChatRoom.AdapterChatRoom
import com.example.chat.Classes.ChatRoomClass
import com.example.chat.Classes.UserChat
import com.example.chat.R
import com.example.chat.databinding.ActivityChatRoomBinding
import io.github.cdimascio.dotenv.dotenv
import org.json.JSONArray

class ChatRoom : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var messagesArrayList: ArrayList<ChatRoomClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            toolbar.title = intent.getStringExtra("name")
        }
        messagesArrayList = ArrayList()

        val dotenv = dotenv {
            directory = "/assets"
            filename = "env" // instead of '.env', use 'env'
        }
        val queue = Volley.newRequestQueue(applicationContext)
        val params = HashMap<String, String>()
        val jsonRequest = object : StringRequest(
            Method.POST, dotenv["CHAT_ROOM"],
            Response.Listener { response ->
                Log.i("response", response.toString())
                for(i in 0 until JSONArray(response).length()){
                    val user = ChatRoomClass(JSONArray(response).getJSONObject(i))
                    messagesArrayList.add(user)
                }

            },
            Response.ErrorListener { error ->
                Log.i("error", error.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                params.put("user_id", "7")
                params.put("companion_id", "3")
                return params
            }
        }
        queue.add(jsonRequest)


        Handler(Looper.getMainLooper()).postDelayed({
            binding.listviewMessages.adapter = AdapterChatRoom(this, messagesArrayList)
        }, 500)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}