package org.example

import java.time.LocalDateTime

class Felino(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDueno: String) : Paciente(codigoAtencion, nombre, especie, fechaIngreso, tipoDueno)
{

    override fun calcularMonto(minutos: Int): Double {

        // Recordar la rubrica, si la atención dura menos de 20 minutos, el cobro es $0.
        if (minutos < 20) {
            return 0.0
        }

        val horas = minutos / 60.0
        return horas * 9000
    }

    override fun tipoPaciente(): String {
        return "Felino"
    }
}