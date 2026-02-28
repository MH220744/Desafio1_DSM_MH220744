package com.example.desafio1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

class CalculadoraActivity : AppCompatActivity() {

    private val df = DecimalFormat("0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        val editarN1 = findViewById<EditText>(R.id.editar_numero_1)
        val editarN2 = findViewById<EditText>(R.id.editar_numero_2)
        val textoResultado = findViewById<TextView>(R.id.tresultado_calculadora)

        val btnSumar = findViewById<Button>(R.id.btn_sumar)
        val btnRestar = findViewById<Button>(R.id.btn_restar)
        val btnMultiplicar = findViewById<Button>(R.id.btn_multiplicar)
        val btnDividir = findViewById<Button>(R.id.btn_dividir)
        val btnExponente = findViewById<Button>(R.id.btn_exponente)
        val btnRaiz = findViewById<Button>(R.id.btn_raiz)

        val btnLimpiar = findViewById<Button>(R.id.btn_limpiar)
        val btnVolver = findViewById<Button>(R.id.btn_volver_menu_calculadora)

        btnSumar.setOnClickListener {
            val (a, b) = leerDosNumeros(editarN1, editarN2) ?: return@setOnClickListener
            mostrar(textoResultado, "Suma", a + b)
        }

        btnRestar.setOnClickListener {
            val (a, b) = leerDosNumeros(editarN1, editarN2) ?: return@setOnClickListener
            mostrar(textoResultado, "Resta", a - b)
        }

        btnMultiplicar.setOnClickListener {
            val (a, b) = leerDosNumeros(editarN1, editarN2) ?: return@setOnClickListener
            mostrar(textoResultado, "Multiplicación", a * b)
        }

        btnDividir.setOnClickListener {
            val (a, b) = leerDosNumeros(editarN1, editarN2) ?: return@setOnClickListener
            if (b == 0.0) {
                editarN2.error = "No se puede dividir entre cero"
                editarN2.requestFocus()
                return@setOnClickListener
            }
            mostrar(textoResultado, "División", a / b)
        }

        btnExponente.setOnClickListener {
            val (a, b) = leerDosNumeros(editarN1, editarN2) ?: return@setOnClickListener
            mostrar(textoResultado, "Exponente", a.pow(b))
        }

        btnRaiz.setOnClickListener {
            val a = leerUnNumero(editarN1) ?: return@setOnClickListener
            if (a < 0.0) {
                editarN1.error = "No existe raíz real de un número negativo"
                editarN1.requestFocus()
                return@setOnClickListener
            }
            mostrar(textoResultado, "Raíz cuadrada", sqrt(a))
        }

        btnLimpiar.setOnClickListener {
            editarN1.text.clear()
            editarN2.text.clear()
            editarN1.error = null
            editarN2.error = null
            textoResultado.text = "Resultado: "
            editarN1.requestFocus()
        }

        btnVolver.setOnClickListener { finish() }
    }

    private fun leerUnNumero(campo: EditText): Double? {
        val txt = campo.text.toString().trim()
        if (txt.isEmpty()) {
            Toast.makeText(this, "Ingrese el Número 1", Toast.LENGTH_SHORT).show()
            campo.requestFocus()
            return null
        }
        val n = txt.toDoubleOrNull()
        if (n == null) {
            campo.error = "Número inválido"
            campo.requestFocus()
            return null
        }
        return n
    }

    private fun leerDosNumeros(c1: EditText, c2: EditText): Pair<Double, Double>? {
        val a = leerUnNumero(c1) ?: return null

        val txt2 = c2.text.toString().trim()
        if (txt2.isEmpty()) {
            Toast.makeText(this, "Ingrese el Número 2", Toast.LENGTH_SHORT).show()
            c2.requestFocus()
            return null
        }
        val b = txt2.toDoubleOrNull()
        if (b == null) {
            c2.error = "Número inválido"
            c2.requestFocus()
            return null
        }
        return Pair(a, b)
    }

    private fun mostrar(tv: TextView, op: String, valor: Double) {
        tv.text = "Resultado: $op = ${df.format(valor)}"
    }
}