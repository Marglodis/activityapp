package com.mtovar.activityapp.data

data class ActivityItem(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val date: String, // formato dd/MM/yyyy
    val time: String, // formato HH:mm
    val description: String
)
