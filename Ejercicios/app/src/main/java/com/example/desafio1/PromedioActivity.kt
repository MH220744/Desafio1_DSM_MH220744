package com.example.desafio1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class PromedioActivity : AppCompatActivity() {

    private val df = DecimalFormat("0.00")
    private val pesos = doubleArrayOf(10.0, 15.0, 20.0, 25.0, 30.0)
    private val notaMinimaAprobacion = 6.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio)

        val etNombre = findViewById<EditText>(R.id.etNombre)

        val edNota1 = findViewById<EditText>(R.id.edNota1)
        val edNota2 = findViewById<EditText>(R.id.edNota2)
        val edNota3 = findViewById<EditText>(R.id.edNota3)
        val edNota4 = findViewById<EditText>(R.id.edNota4)
        val edNota5 = findViewById<EditText>(R.id.edNota5)

        val camposNotas = listOf(edNota1, edNota2, edNota3, edNota4, edNota5)

        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiar)
        val btnVolver = findViewById<Button>(R.id.btnVolverMenu)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        btnCalcular.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                etNombre.error = "Ingrese el nombre"
                etNombre.requestFocus()
                return@setOnClickListener
            }

            val notas = leerYValidarNotas(camposNotas) ?: return@setOnClickListener

            val promedio = promedioPonderado(notas, pesos)
            val estado = if (promedio >= notaMinimaAprobacion) "APROBÓ" else "REPROBÓ"

            tvResultado.text = buildString {
                append("Resultado:\n")
                append("Estudiante: ").append(nombre).append("\n")
                append("Promedio final: ").append(df.format(promedio)).append("\n")
                append("Estado: ").append(estado)
            }
        }

        btnLimpiar.setOnClickListener {
            etNombre.text.clear()
            edNota1.text.clear()
            edNota2.text.clear()
            edNota3.text.clear()
            edNota4.text.clear()
            edNota5.text.clear()

            etNombre.error = null
            edNota1.error = null
            edNota2.error = null
            edNota3.error = null
            edNota4.error = null
            edNota5.error = null

            tvResultado.text = "Resultado: "
            etNombre.requestFocus()
        }

        btnVolver.setOnClickListener { finish() }
    }

    private fun leerYValidarNotas(campos: List<EditText>): DoubleArray? {
        val notas = DoubleArray(campos.size)

        for (i in campos.indices) {
            val texto = campos[i].text.toString().trim()

            if (texto.isEmpty()) {
                Toast.makeText(this, "Complete todas las notas", Toast.LENGTH_SHORT).show()
                campos[i].requestFocus()
                return null
            }

            val valor = texto.toDoubleOrNull()
            if (valor == null) {
                campos[i].error = "Número inválido"
                campos[i].requestFocus()
                return null
            }

            if (valor < 0.0 || valor > 10.0) {
                campos[i].error = "Debe estar entre 0 y 10"
                campos[i].requestFocus()
                return null
            }

            notas[i] = valor
        }

        return notas
    }

    private fun promedioPonderado(notas: DoubleArray, pesos: DoubleArray): Double {
        var suma = 0.0
        var totalPesos = 0.0

        for (i in notas.indices) {
            suma += notas[i] * pesos[i]
            totalPesos += pesos[i]
        }

        return if (totalPesos == 0.0) 0.0 else suma / totalPesos
    }
}