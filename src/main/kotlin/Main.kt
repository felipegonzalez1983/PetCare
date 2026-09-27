package org.example

import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

fun main() = runBlocking {

    val petCare = PetCare()
    val reportes = Reportes()

    val max = Canino(codigoAtencion = "CA12CD",
        nombre = "Max",
        especie = "Golden Retriever",
        fechaIngreso = LocalDateTime.now(),
        tipoDueno = "convenio")

    val luna = Canino(codigoAtencion = "CA99ZA",
        nombre = "Luna",
        especie = "Labrador",
        fechaIngreso = LocalDateTime.now(),
        tipoDueno = "particular")

    val misi = Felino(codigoAtencion = "FE22TO",
        nombre = "Misi",
        especie = "Siamés",
        fechaIngreso = LocalDateTime.now(),
        tipoDueno = "particular")

    val loro = Exotico(codigoAtencion = "EX44RG",
        nombre = "Loro",
        especie = "Amazónico",
        fechaIngreso = LocalDateTime.now(),
        tipoDueno = "municipal",
        esSilvestre = true)

    val iguana = Exotico(codigoAtencion = "EX77RG",
        nombre = "Iguana",
        especie = "Verde",
        fechaIngreso = LocalDateTime.now(),
        tipoDueno = "particular",
        esSilvestre = false)

    val pacienteInvalido = Canino(codigoAtencion = "123ABC", nombre = "Paciente Prueba", especie = "Canino", fechaIngreso = LocalDateTime.now(), tipoDueno = "particular")

    println("========== PETCARE ==========")

    println("\n--- REGISTRO DE ENTRADAS ---")

    petCare.registrarEntrada(max)
    petCare.registrarEntrada(luna)
    petCare.registrarEntrada(misi)
    petCare.registrarEntrada(loro)
    petCare.registrarEntrada(iguana)

    println("\n--- PRUEBA DE CÓDIGO INVÁLIDO ---")

    petCare.registrarEntrada(pacienteInvalido)

    println("\n--- ESTADO DE LOS BOXES ---")

    petCare.mostrarBoxes()

    println("\n--- REGISTRO DE SALIDAS ---")

    petCare.registrarSalida("CA12CD", 75)
    petCare.registrarSalida("CA99ZA", 180)
    petCare.registrarSalida("FE22TO", 18)
    petCare.registrarSalida("EX44RG", 120)
    petCare.registrarSalida("EX77RG", 45)

    println("\n--- PRUEBA DE PACIENTE NO ENCONTRADO ---")

    petCare.registrarSalida(codigoAtencion = "ZZ99ZZ", minutos = 60)

    println("\n--- CÓDIGOS FINALIZADOS ---")

    reportes.codigosFinalizados(petCare.historial).forEach { codigo ->
        println(codigo)
    }

    reportes.mostrarReporteFinal(historial = petCare.historial, recaudacionTotal = petCare.recaudacionTotal, boxesDisponibles = petCare.boxesDisponibles())

    println("\n========== FIN DEL TURNO ==========")
}