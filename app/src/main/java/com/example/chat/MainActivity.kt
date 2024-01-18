package com.example.chat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest


import com.android.volley.toolbox.Volley
import com.example.chat.AdapterUsers.AdapterUsers
import com.example.chat.ChatRoom.ChatRoom
import com.example.chat.Classes.UserChat
import com.example.chat.consts.constants
import com.example.chat.databinding.ActivityMainBinding
import io.github.cdimascio.dotenv.dotenv


import org.json.JSONArray


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var userArrayList: ArrayList<UserChat>
    private lateinit var toogle: ActionBarDrawerToggle


    @SuppressLint("WrongConstant", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("currentUser", MODE_APPEND)
       sharedPreferences.edit().putString("currentUser", "7").apply()



        userArrayList = java.util.ArrayList()
        val queue = Volley.newRequestQueue(applicationContext)
        val params = HashMap<String, String>()
        val jsonRequest = object : StringRequest(Method.POST, constants().dotenv["USERS_LINK"],
            Response.Listener { response ->
                for(i in 0 until JSONArray(response).length()){
                    val user = UserChat(JSONArray(response).getJSONObject(i))
                    userArrayList.add(user)
                }

            },
            Response.ErrorListener { error ->
                Log.i("error", error.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                params.put("user_id", sharedPreferences.getString("currentUser", "").toString())
                return params
            }
        }
        queue.add(jsonRequest)
        binding.listviewUsers.isClickable = true
        Handler(Looper.getMainLooper()).postDelayed({
            binding.listviewUsers.adapter = AdapterUsers(this,userArrayList)
        }, 1000)

        binding.listviewUsers.setOnItemClickListener { parent, view, position, id ->
            //Log.i("namen", userArrayList[position].json.getJSONObject(position.toString()).getString("name"))
            val name = userArrayList[position].json
            val i = Intent(this, ChatRoom::class.java)
            i.putExtra("companionUser", name.toString())
            startActivity(i)
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}