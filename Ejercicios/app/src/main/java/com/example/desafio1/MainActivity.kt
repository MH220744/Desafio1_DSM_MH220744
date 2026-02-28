package com.example.desafio1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEjercicio1 = findViewById<Button>(R.id.btn_Ejercicio_1)
        btnEjercicio1.setOnClickListener {
            startActivity(Intent(this, PromedioActivity::class.java))
        }

        val btnEjercicio2 = findViewById<Button>(R.id.btn_Ejercicio_2)
        btnEjercicio2.setOnClickListener {
            startActivity(Intent(this, SalarioActivity::class.java))
        }

        val btnEjercicio3 = findViewById<Button>(R.id.btn_Ejercicio_3)
        btnEjercicio3.setOnClickListener {
            startActivity(Intent(this, CalculadoraActivity::class.java))
        }
    }
}