package org.example

class Reportes {

    fun ingresoPromedio(historial: List<Ticket>): Double {

        return if (historial.isNotEmpty()) {
            historial.map { it.montoPagado }.average()
        } else {
            0.0
        }
    }

    fun codigosFinalizados(historial: List<Ticket>): List<String> {

        return historial.map {
            it.codigoAtencion
        }
    }

    fun pacientesConvenio(historial: List<Ticket>): List<Ticket> {

        return historial.filter {
            it.tipoDueno.lowercase() == "convenio"
        }
    }

    fun pacienteConMasTiempo(historial: List<Ticket>): Ticket? {

        return historial.maxByOrNull {
            it.minutosAtencion
        }
    }

    fun recaudacionPorTipo(historial: List<Ticket>): Map<String, Double> {

        return historial
            .groupBy { it.tipoPaciente }
            .mapValues { entrada ->
                entrada.value.sumOf { it.montoPagado }
            }
    }

    fun tipoConMasIngresos(historial: List<Ticket>): String? {

        return recaudacionPorTipo(historial)
            .maxByOrNull { it.value }
            ?.key
    }

    fun mostrarReporteFinal(
        historial: List<Ticket>,
        recaudacionTotal: Double,
        boxesDisponibles: Int
    ) {

        println("\n========== REPORTE FINAL PETCARE ==========")

        historial.forEach { ticket ->

            println(
                "Ticket ${ticket.numeroTicket} | " + "${ticket.nombrePaciente} | " + "${ticket.tipoPaciente} | " +
                        "Código: ${ticket.codigoAtencion} | " +
                        "Tiempo: ${ticket.minutosAtencion} minutos | " +
                        "Monto: $${ticket.montoPagado}")
        }

        val promedio = ingresoPromedio(historial)

        val mayorTiempo = pacienteConMasTiempo(historial)

        val tipoMayorIngreso = tipoConMasIngresos(historial)

        println("\nTotal recaudado: $$recaudacionTotal")
        println("Pacientes atendidos: ${historial.size}")
        println("Ingreso promedio: $$promedio")
        println("Boxes disponibles: $boxesDisponibles")

        if (tipoMayorIngreso != null) {
            println("Tipo de paciente con más ingresos: $tipoMayorIngreso")
        }

        if (mayorTiempo != null) {
            println("Paciente con más tiempo: " + "${mayorTiempo.nombrePaciente} - " + "${mayorTiempo.codigoAtencion} " +
                        "con ${mayorTiempo.minutosAtencion} minutos")
        }

        println("\n--- PACIENTES CON CONVENIO ---")

        val convenios = pacientesConvenio(historial)

        if (convenios.isEmpty()) {

            println("No hay pacientes con convenio")

        } else { convenios.forEach {
                println("${it.nombrePaciente} - ${it.codigoAtencion}")
            }
        }

        println("\n--- RECAUDACIÓN POR TIPO ---")

        recaudacionPorTipo(historial).forEach { tipo, total ->

            println(
                "$tipo: $$total"
            )
        }
    }
}