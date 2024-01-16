package com.example.chat.RegisterAndLogin

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chat.consts.constants
import com.example.chat.databinding.ActivityLoginAndRegisterBinding
import com.example.chat.functions.common_functions
import io.github.cdimascio.dotenv.dotenv
import java.util.jar.Manifest

class LoginAndRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAndRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAndRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arrayAdapterCountryCode = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, constants().countryCodeArray)

        binding.apply {
            spinner.adapter = arrayAdapterCountryCode



            val dotenv = dotenv {
                directory = "/assets"
                filename = "env" // instead of '.env', use 'env'
            }

            registerButton.setOnClickListener {
                val fullPhone = spinner.selectedItem.toString()+editText.text.toString()
                val queue = Volley.newRequestQueue(applicationContext)
                val params = HashMap<String, String>()
                val jsonRequest = object : StringRequest(
                    Method.POST, dotenv["USER_REGISTER"],
                    Response.Listener { response ->
                        if (response == "true") {

                        }
                    },
                    Response.ErrorListener { error ->
                        Log.i("error", error.toString())
                    }
                ){
                    override fun getParams(): MutableMap<String, String>? {
                        params.put("phone", fullPhone)
                        return params
                    }
                }
                queue.add(jsonRequest)
            }


        }
    }
}