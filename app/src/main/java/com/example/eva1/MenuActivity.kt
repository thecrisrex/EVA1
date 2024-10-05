package com.example.eva1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    lateinit var btnFormulario: Button
    lateinit var btnRegistrarUsuario: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnFormulario = findViewById(R.id.btn_Formulario)
        btnRegistrarUsuario = findViewById(R.id.btn_RegistrarUsuario)

        val username = intent.getStringExtra("username") ?: "Invitado"

        btnFormulario.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        btnRegistrarUsuario.setOnClickListener {
            val intent = Intent(this, RegisterUsersActivity::class.java)
            startActivity(intent)
        }
    }
}
