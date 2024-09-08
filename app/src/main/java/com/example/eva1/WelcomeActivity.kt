package com.example.eva1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Obtener el Intent que inició esta actividad
        val username = intent.getStringExtra("username")

        // Mostrar el nombre de usuario en el TextView
        val welcomeMessage: TextView = findViewById(R.id.welcome_text)
        welcomeMessage.text = "¡Bienvenido, $username!"
    }
}
