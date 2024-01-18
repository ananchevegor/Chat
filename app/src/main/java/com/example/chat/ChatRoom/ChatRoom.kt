package com.example.chat.ChatRoom

import android.annotation.SuppressLint
import android.content.SharedPreferences
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
import com.example.chat.consts.constants
import com.example.chat.databinding.ActivityChatRoomBinding
import io.github.cdimascio.dotenv.dotenv
import org.json.JSONArray
import org.json.JSONObject

class ChatRoom : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomBinding
    private lateinit var messagesArrayList: ArrayList<ChatRoomClass>

    @SuppressLint("WrongConstant", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val sharedPreferences = getSharedPreferences("currentUser", MODE_APPEND)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        messagesArrayList = ArrayList()
        val companion = intent.getStringExtra("companionUser")?.let { JSONObject(it) }
        companion?.let { getMessages(sharedPreferences, it) }
        Log.i("resp", companion.toString())

        binding.apply {
            toolbar.title = companion!!.getString("name")
            buttonSendMessage.setOnClickListener {
                messagesArrayList.clear()

                val queue_insert = Volley.newRequestQueue(applicationContext)
                val params_insert = HashMap<String, String>()
                queue_insert.add(object : StringRequest(
                    Method.POST, constants().dotenv["CHAT_ROOM_MESSAGE"],
                    Response.Listener { response ->
                    },
                    Response.ErrorListener { error ->
                        Log.i("error", error.toString())
                    }
                ){
                    override fun getParams(): MutableMap<String, String>? {
                        params_insert.put("user_id", sharedPreferences.getString("currentUser", "7").toString())
                        params_insert.put("companion_id", companion.getString("id").toString())
                        params_insert.put("message", editTextMessage.text.toString())
                        return params_insert
                    }
                })

                getMessages(sharedPreferences, companion)
                editTextMessage.text.clear()
            }
        }











    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun getMessages(sharedPreferences: SharedPreferences, companion: JSONObject){
        val queue = Volley.newRequestQueue(applicationContext)
        val params = HashMap<String, String>()
        val jsonRequest = object : StringRequest(
            Method.POST, constants().dotenv["CHAT_ROOM"],
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
                params.put("user_id", sharedPreferences.getString("currentUser", "7").toString())
                params.put("companion_id", companion.getString("id").toString())
                return params
            }
        }
        queue.add(jsonRequest)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.listviewMessages.adapter = AdapterChatRoom(this, messagesArrayList, sharedPreferences)
        }, 500)
    }
}