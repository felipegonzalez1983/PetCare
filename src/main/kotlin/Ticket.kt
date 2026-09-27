package org.example

data class Ticket(
    val numeroTicket: Int,
    val nombrePaciente: String,
    val tipoPaciente: String,
    val tipoDueno: String,
    val codigoAtencion: String,
    val minutosAtencion: Int,
    val montoPagado: Double)