package org.example

import kotlinx.coroutines.delay

class PetCare {

    val boxes = MutableList(10) { indice ->
        Box(indice + 1)
    }

    val historial = mutableListOf<Ticket>()

    var recaudacionTotal = 0.0

    private var contadorTicket = 1

    fun validarCodigo(codigo: String): Boolean {

        val formato = Regex("^[A-Za-z]{2}[0-9]{2}[A-Za-z]{2}$")

        return formato.matches(codigo)
    }

    fun validarTipoDueno(tipoDueno: String): Boolean {

        return tipoDueno.lowercase() in listOf("particular", "convenio", "municipal")
    }

    fun boxesDisponibles(): Int {

        return boxes.count {
            it.estado is EstadoBox.Libre
        }
    }

    suspend fun registrarEntrada(paciente: Paciente) {

        try {

            if (!validarCodigo(paciente.codigoAtencion)) { println("Error: código de atención inválido")
                return
            }

            if (!validarTipoDueno(paciente.tipoDueno)) { println("Error: tipo de dueño inválido")
                return
            }

            val boxLibre = boxes.firstOrNull {
                it.estado is EstadoBox.Libre
            }

            if (boxLibre == null) { println("Error: no hay boxes disponibles")
                return
            }

            boxLibre.estado = EstadoBox.EnProceso("Registrando entrada")

            println("Procesando entrada de ${paciente.nombre}...")

            delay(3000)

            boxLibre.estado =
                EstadoBox.EnAtencion(paciente)

            println("${paciente.nombre} ingresó al Box ${boxLibre.numero}")

            // Si el paciente es exótico, se indica si es silvestre o no.
            if (paciente is Exotico) {

                val detalleSilvestre = if (paciente.esSilvestre) {
                    "Sí"
                } else {
                    "No"
                }
                println("Animal silvestre: $detalleSilvestre")
            }

        } catch (e: Exception) { println("Error al registrar entrada")
        }
    }

    suspend fun registrarSalida(codigoAtencion: String, minutos: Int)
    {

        try {

            // Validamos primero el formato del código.
            if (!validarCodigo(codigoAtencion)) { println("Error: código de atención inválido")
                return
            }

            // Buscamos el box donde está el paciente.
            val boxPaciente = boxes.firstOrNull { box ->

                val estado = box.estado

                estado is EstadoBox.EnAtencion &&
                        estado.paciente.codigoAtencion == codigoAtencion
            }

            if (boxPaciente == null) { println("Error: paciente no encontrado")
                return
            }

            val estadoActual = boxPaciente.estado

            if (estadoActual !is EstadoBox.EnAtencion) { println("Error: el box no se encuentra en atención")
                return
            }

            val paciente = estadoActual.paciente

            boxPaciente.estado = EstadoBox.EnProceso("Calculando tarifa")

            println("Procesando salida de ${paciente.nombre}...")
            delay(6500)

            var monto = paciente.calcularMonto(minutos)

            // El felino puede pagar $0 si estuvo menos de 20 minutos.
            val ceroPermitido =
                paciente is Felino && minutos < 20

            if (monto <= 0 && !ceroPermitido) { println("Error: tarifa inválida")

                boxPaciente.estado = EstadoBox.EnAtencion(paciente)

                return
            }

            // Se aplica IVA del 19%.
            if (monto > 0) {
                monto *= 1.19
            }

            // Dueño municipal:
            // 50% de descuento sobre el valor con IVA.
            if (paciente.tipoDueno.lowercase() == "municipal") {
                monto *= 0.50
            }

            val ticket = Ticket(numeroTicket = contadorTicket, nombrePaciente = paciente.nombre, tipoPaciente = paciente.tipoPaciente(), tipoDueno = paciente.tipoDueno, codigoAtencion = paciente.codigoAtencion, minutosAtencion = minutos, montoPagado = monto)

            historial.add(ticket)
            recaudacionTotal += monto
            contadorTicket++

            boxPaciente.estado = EstadoBox.Libre

            println("Salida registrada correctamente")
            println("Ticket N° ${ticket.numeroTicket}")
            println("Paciente: ${ticket.nombrePaciente}")
            println("Monto pagado: $${ticket.montoPagado}")

        } catch (e: Exception) { println("Error al registrar salida")
        }
    }

    fun mostrarBoxes() { boxes.forEach { it.mostrarEstado()
        }
    }
}