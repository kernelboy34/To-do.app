package com.example.to_do

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configurar la ventana para considerar las barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Barra de progreso
        val barrita = findViewById<ProgressBar>(R.id.progressBar2)
        barrita.progress = 0

        // Verificar el estado de la conexión a Internet
        checkInternetConnection(barrita)
    }

    // Función para verificar el estado de la conexión a Internet
    private fun checkInternetConnection(progressBar: ProgressBar) {
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            // Verificar la conexión a Internet
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

            if (networkCapabilities != null) {
                val isConnected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                val isWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                val isCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

                // Ajustar la barra de progreso según el tipo de conexión
                progressBar.progress = when {
                    isConnected && isWifi -> 100 // Conexión Wi-Fi fuerte
                    isConnected && isCellular -> 75 // Conexión móvil fuerte
                    isConnected -> 50 // Conexión débil
                    else -> 0 // Sin conexión
                }

                // Simular un delay antes de realizar la siguiente acción
                handler.postDelayed({
                    if (progressBar.progress >= 75) {
                        // Conexión fuerte o aceptable, ir a la actividad de registro
                        val intent = Intent(this, RegistroActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Mostrar un mensaje si la conexión es débil o no hay conexión
                        Log.d("Network", "La conexión a Internet es débil o no hay conexión.")
                        // Puedes mostrar un mensaje de error al usuario aquí si lo deseas
                    }
                }, 2000) // Espera de 2 segundos para simular la conexión

            } else {
                Log.d("Network", "No hay conexión a Internet.")
                progressBar.progress = 0
            }

        }, 2000) // Espera de 2 segundos para simular la comprobación de conexión
    }
}

