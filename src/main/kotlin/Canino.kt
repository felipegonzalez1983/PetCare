package org.example

import java.time.LocalDateTime

class Canino(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDueno: String) : Paciente(codigoAtencion, nombre, especie, fechaIngreso, tipoDueno)
{

    override fun calcularMonto(minutos: Int): Double {

        val horas = minutos / 60.0
        var monto = horas * 12000

        // Aca hay que verificar como dice la rubrica si el dueño tiene convenio, se aplica un 20% de descuento.
        if (tipoDueno.lowercase() == "convenio") {
            monto *= 0.80
        }

        return monto
    }

    override fun tipoPaciente(): String {
        return "Canino"
    }
}