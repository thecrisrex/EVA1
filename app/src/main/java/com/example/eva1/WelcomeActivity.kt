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

        // Obtener el nombre de usuario del intent
        val username = intent.getStringExtra("username")

        // Mostrar el mensaje de bienvenida
        val welcomeMessage: TextView = findViewById(R.id.welcomeMessage)
        welcomeMessage.text = "¡Bienvenido, $username!"

        // Inicializar los campos
        nombreInput = findViewById(R.id.etNombre)
        apellidoInput = findViewById(R.id.etApellido)
        comunaInput = findViewById(R.id.etComuna)
        observacionInput = findViewById(R.id.etObservacion)
        enviarBtn = findViewById(R.id.btnEnviar)

        // Configurar el botón para enviar datos
        enviarBtn.setOnClickListener {
            val nombre = nombreInput.text.toString()
            val apellido = apellidoInput.text.toString()
            val comuna = comunaInput.text.toString()
            val observacion = observacionInput.text.toString()

            // Validar que todos los campos estén completos
            if (nombre.isNotEmpty() && apellido.isNotEmpty() && comuna.isNotEmpty() && observacion.isNotEmpty()) {
                // Crear el intent para enviar el correo
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:") // Solo apps de email manejarán esto
                    putExtra(Intent.EXTRA_SUBJECT, "Información de usuario: $nombre $apellido")
                    putExtra(Intent.EXTRA_TEXT, """
                        Nombre: $nombre
                        Apellido: $apellido
                        Comuna: $comuna
                        Observación: $observacion
                    """.trimIndent())
                }

                // Verificar si hay una aplicación de correo instalada
                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(emailIntent)
                } else {
                    Toast.makeText(this, "No hay ninguna aplicación de correo instalada", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Mostrar un mensaje de error si hay campos vacíos
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

