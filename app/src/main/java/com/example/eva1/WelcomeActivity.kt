package com.example.eva1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    lateinit var nombreInput: EditText
    lateinit var apellidoInput: EditText
    lateinit var comunaInput: EditText
    lateinit var observacionInput: EditText
    lateinit var enviarBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val username = intent.getStringExtra("username") ?: "Invitado"

        val welcomeMessage: TextView = findViewById(R.id.welcomeMessage)
        welcomeMessage.text = "¡Bienvenido, $username!"

        nombreInput = findViewById(R.id.etNombre)
        apellidoInput = findViewById(R.id.etApellido)
        comunaInput = findViewById(R.id.etComuna)
        observacionInput = findViewById(R.id.etObservacion)
        enviarBtn = findViewById(R.id.btnEnviar)

        enviarBtn.setOnClickListener {
            val nombre = nombreInput.text.toString()
            val apellido = apellidoInput.text.toString()
            val comuna = comunaInput.text.toString()
            val observacion = observacionInput.text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && comuna.isNotEmpty() && observacion.isNotEmpty()) {

                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("cristofer.flores29.09@gmail.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Información de usuario: $nombre $apellido")
                    putExtra(Intent.EXTRA_TEXT, """
                        Nombre: $nombre
                        Apellido: $apellido
                        Comuna: $comuna
                        Observación: $observacion
                    """.trimIndent())
                }

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar correo..."))
                } catch (ex: android.content.ActivityNotFoundException) {
                    Toast.makeText(this, "No hay ninguna aplicación de correo instalada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


