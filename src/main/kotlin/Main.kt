package org.example

import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

fun main() = runBlocking {

    val petCare = PetCare()

    println("========== PRUEBA SISTEMA SIN CAPACIDAD ==========")

    for (i in 1..10) {

        val pacientePrueba = Canino(
            codigoAtencion = "AA${i.toString().padStart(2, '0')}BB",
            nombre = "Paciente$i",
            especie = "Canino",
            fechaIngreso = LocalDateTime.now(),
            tipoDueno = "particular"
        )

        petCare.registrarEntrada(pacientePrueba)
    }

    println("\n--- ESTADO DE LOS 10 BOXES ---")

    petCare.mostrarBoxes()

    println("\n--- INTENTO DE INGRESAR PACIENTE 11 ---")

    val pacienteExtra = Canino(
        codigoAtencion = "ZZ99ZZ",
        nombre = "Paciente Extra",
        especie = "Canino",
        fechaIngreso = LocalDateTime.now(),
        tipoDueno = "particular"
    )

    petCare.registrarEntrada(pacienteExtra)

    println("\n========== FIN DE LA PRUEBA ==========")
}