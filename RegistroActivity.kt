package com.example.to_do

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } // Encuentra el botón de login en tu layout
        val loginButton = findViewById<Button>(R.id.button) // Ajusta el ID según tu layout
        loginButton.setOnClickListener {
            // Este bloque solo se ejecuta cuando el usuario hace clic en el botón
            val intent = Intent(this, que_eres::class.java)
            startActivity(intent)
            finish()
        }
    }
}