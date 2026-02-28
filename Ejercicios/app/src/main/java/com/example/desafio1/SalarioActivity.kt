package com.example.desafio1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import kotlin.math.min

class SalarioActivity : AppCompatActivity() {

    private val df = DecimalFormat("0.00")

    // Descuentos
    private val tasaAfp = 0.0725
    private val tasaIsss = 0.03
    private val techoIsss = 30.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        val editarNombre = findViewById<EditText>(R.id.editar_nombre_empleado)
        val editarSalario = findViewById<EditText>(R.id.editar_salario_base)

        val botonCalcular = findViewById<Button>(R.id.boton_calcular_salario)
        val botonLimpiar = findViewById<Button>(R.id.btn_limpiar_salario)
        val botonVolver = findViewById<Button>(R.id.btn_volver_menu_salario)

        val textoDetalle = findViewById<TextView>(R.id.t_detalle_salario)

        botonCalcular.setOnClickListener {
            val nombre = editarNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                editarNombre.error = "Ingrese el nombre"
                editarNombre.requestFocus()
                return@setOnClickListener
            }

            val salarioTxt = editarSalario.text.toString().trim()
            val salarioBase = salarioTxt.toDoubleOrNull()
            if (salarioBase == null) {
                editarSalario.error = "Ingrese un salario válido"
                editarSalario.requestFocus()
                return@setOnClickListener
            }
            if (salarioBase <= 0.0) {
                editarSalario.error = "El salario debe ser positivo"
                editarSalario.requestFocus()
                return@setOnClickListener
            }

            val afp = salarioBase * tasaAfp
            val isss = min(salarioBase * tasaIsss, techoIsss)

            val baseImponible = salarioBase - afp - isss
            val renta = calcularRentaMensualPorTramos(baseImponible)

            val totalDescuentos = afp + isss + renta
            val salarioNeto = salarioBase - totalDescuentos

            textoDetalle.text = buildString {
                append("Resultado:\n\n")
                append("Empleado: ").append(nombre).append("\n")
                append("Salario base: $").append(df.format(salarioBase)).append("\n\n")
                append("AFP (7.25%): $").append(df.format(afp)).append("\n")
                append("ISSS (3%): $").append(df.format(isss)).append("\n")
                append("Renta: $").append(df.format(renta)).append("\n")
                append("-----------------------------\n")
                append("Total descuentos: $").append(df.format(totalDescuentos)).append("\n")
                append("Salario neto: $").append(df.format(salarioNeto))
            }

            Toast.makeText(this, "Cálculo realizado", Toast.LENGTH_SHORT).show()
        }

        botonLimpiar.setOnClickListener {
            editarNombre.text.clear()
            editarSalario.text.clear()

            editarNombre.error = null
            editarSalario.error = null

            textoDetalle.text = "Resultado: "
            editarNombre.requestFocus()
        }

        botonVolver.setOnClickListener { finish() }
    }

    private fun calcularRentaMensualPorTramos(baseMensual: Double): Double {
        if (baseMensual <= 550.00) return 0.0

        return when {
            baseMensual <= 895.24 ->
                17.67 + (baseMensual - 550.00) * 0.10

            baseMensual <= 2038.10 ->
                60.00 + (baseMensual - 895.24) * 0.20

            else ->
                288.57 + (baseMensual - 2038.10) * 0.30
        }
    }
}