package org.example

import java.time.LocalDateTime

open class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val fechaIngreso: LocalDateTime,
    val tipoDueno: String
) {

    open fun calcularMonto(minutos: Int): Double {
        return 0.0
    }

    open fun tipoPaciente(): String {
        return "Paciente"
    }
}