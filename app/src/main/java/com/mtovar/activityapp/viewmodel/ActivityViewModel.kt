package com.mtovar.activityapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtovar.activityapp.data.ActivityItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class ActivityViewModel : ViewModel() {
    // StateFlow privado mutable para uso interno
    private val _activities = MutableStateFlow<List<ActivityItem>>(emptyList())

    //Stateflow úblico inmutable para observación
    val activities: StateFlow<List<ActivityItem>> = _activities.asStateFlow()

    //Estado de carag
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    //Estado de errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    //Estado de formulario
    private val _formState = MutableStateFlow(FormState())
    val formState: StateFlow<FormState> = _formState.asStateFlow()


    init {
        loadInitialData()
    }

    /**
     * Carga datos iniciales usando coroutines
     * Simula una operación asíncrona
     */
    private fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            // Simula carag de datos en segundo plano
            delay(2000)
            _isLoading.value = false
        }
    }

    /**
     * Agrega una nueva actividad usando coroutines
     * Aplica Scope Functions (apply, also)
     */
    fun addActivity(name: String, date: LocalDate, time: LocalTime, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            //Simular la operación asincrona
            delay(2000)
            ActivityItem(
                name = name.trim(),
                date = date,
                time = time,
                description = description.trim()
            ).takeIf { it.isValid() }?.also { activity ->
                //Scoupe Function: also - ejecuta y retorna el objeto
                _activities.value = _activities.value.toMutableList().apply {
                    // Scoupe function: apply configura y retorna el objeto
                    add(0, activity) // AGrega al inicio de la lista
                }.sortedByDescending { it.name }
                clearForm()
            } ?: run {
                // Scope function: run - cuando la validción falla
                _error.value = "Los campos no pueden estar vacíos"
            }
            _isLoading.value = false
        }
    }
    fun clearForm(){
        _formState.value = FormState()
    }
}


data class FormState(
    val name: String = "",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val description: String = ""
)