package com.mtovar.activityapp.data

import java.time.LocalDate
import java.time.LocalTime

data class ActivityItem(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val date: LocalDate, // formato dd/MM/yyyy
    val time: LocalTime, // formato HH:mm
    val description: String
) {
    /**
     * Validación usando Kotlin Standard fuction  -let
     */
    fun isValid(): Boolean = name.isNotBlank() && description.isNotBlank()

    /**
     * Formatear la fecha para visualziación
     */
    fun getFormattedDate(): String = "${date.dayOfMonth}/${date.monthValue}/${date.year}"

    /**
     * Formatear la hora para visualziación
     */
    fun getFormattedTime(): String =
        "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
}
