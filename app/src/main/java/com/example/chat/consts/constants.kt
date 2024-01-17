package com.example.chat.consts

import io.github.cdimascio.dotenv.dotenv

class constants {

    val countryCodeArray = arrayOf(
        "+49",
        "+7",
        "+380",
        "+33",
        "+354",
        "+39"
    )

    val dotenv = dotenv {
        directory = "/assets"
        filename = "env" // instead of '.env', use 'env'
    }

}