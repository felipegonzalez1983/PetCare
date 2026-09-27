package org.example

import java.time.LocalDateTime

class Exotico(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDueno: String,
    val esSilvestre: Boolean) : Paciente(codigoAtencion, nombre, especie, fechaIngreso, tipoDueno) {

    override fun calcularMonto(minutos: Int): Double {

        val horas = minutos / 60.0
        var monto = horas * 20000

        // Indicar con el if, Si el animal exótico es silvestre, se aplica un recargo del 30%.
        if (esSilvestre) {
            monto *= 1.30
        }

        return monto
    }

    override fun tipoPaciente(): String {
        return "Exotico"
    }
}