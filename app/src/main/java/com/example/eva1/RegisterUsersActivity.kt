package com.example.eva1;
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva1.R
import com.example.eva1.SQLiteHelper

class RegisterUsersActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var registrarBtn: Button
    lateinit var updateBtn: Button
    lateinit var deleteBtn: Button
    lateinit var viewUsersBtn: Button
    lateinit var userListView: ListView
    lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        dbHelper = SQLiteHelper(this)

        usernameInput = findViewById(R.id.etNewUsername)
        passwordInput = findViewById(R.id.etNewPassword)
        registrarBtn = findViewById(R.id.btnRegistrar)
        updateBtn = findViewById(R.id.btnActualizar)
        deleteBtn = findViewById(R.id.btnEliminar)
        viewUsersBtn = findViewById(R.id.btnVerUsuarios)
        userListView = findViewById(R.id.userListView)

        registrarBtn.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                dbHelper.addUser(username, password)
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                mostrarUsuarios()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        updateBtn.setOnClickListener {
            val username = usernameInput.text.toString()
            val newPassword = passwordInput.text.toString()

            if (username.isNotEmpty() && newPassword.isNotEmpty()) {
                val rowsUpdated = dbHelper.updateUser(username, newPassword)
                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                    mostrarUsuarios()
                } else {
                    Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        deleteBtn.setOnClickListener {
            val username = usernameInput.text.toString()

            if (username.isNotEmpty()) {
                val rowsDeleted = dbHelper.deleteUser(username)
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    mostrarUsuarios()
                } else {
                    Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa un nombre de usuario", Toast.LENGTH_SHORT).show()
            }
        }

        viewUsersBtn.setOnClickListener {
            mostrarUsuarios()
        }

        mostrarUsuarios()
    }

    private fun mostrarUsuarios() {
        val userList = dbHelper.getAllUsers()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        userListView.adapter = adapter
    }
}

