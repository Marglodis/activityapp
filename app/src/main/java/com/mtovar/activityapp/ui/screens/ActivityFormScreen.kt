package com.mtovar.activityapp.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mtovar.activityapp.viewmodel.ActivityViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * Pantalla de formulario para registrar actividades
 * Principio SOLID: Single Responsibility - Solo maneja el formulario
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFormScreen(
    viewModel: ActivityViewModel,
    onNavigateBack: () -> Unit
) {
    // Observar estados
    val formState by viewModel.formState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.error.collectAsState()

    // Estados locales para el formulario
    var name by remember { mutableStateOf(formState.name) }
    var description by remember { mutableStateOf(formState.description) }
    var selectedDate by remember { mutableStateOf(formState.date) }
    var selectedTime by remember { mutableStateOf(formState.time) }

    // Estados de validación
    var nameError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }

    // Efecto para limpiar errores cuando el usuario escribe
    LaunchedEffect(name) {
        if (name.isNotBlank()) nameError = false
        viewModel.updateFormState(name = name)
    }

    LaunchedEffect(description) {
        if (description.isNotBlank()) descriptionError = false
        viewModel.updateFormState(description = description)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Actividad") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mostrar mensaje de error si existe
            errorMessage?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            // Campo: Nombre de la actividad
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la actividad *") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError,
                supportingText = {
                    if (nameError) {
                        Text("El nombre es obligatorio")
                    }
                },
                singleLine = true,
                enabled = !isLoading
            )

            // Selector de fecha
            DatePickerField(
                selectedDate = selectedDate,
                onDateSelected = {
                    selectedDate = it
                    viewModel.updateFormState(date = it)
                },
                enabled = !isLoading
            )

            // Selector de hora
            TimePickerField(
                selectedTime = selectedTime,
                onTimeSelected = {
                    selectedTime = it
                    viewModel.updateFormState(time = it)
                },
                enabled = !isLoading
            )

            // Campo: Descripción
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                isError = descriptionError,
                supportingText = {
                    if (descriptionError) {
                        Text("La descripción es obligatoria")
                    }
                },
                maxLines = 5,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de guardar
            Button(
                onClick = {
                    // Validar campos
                    nameError = name.isBlank()
                    descriptionError = description.isBlank()

                    if (!nameError && !descriptionError) {
                        viewModel.addActivity(
                            name = name,
                            date = selectedDate,
                            time = selectedTime,
                            description = description
                        )

                        // Navegar de vuelta después de guardar
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar Actividad")
                }
            }

            // Botón de cancelar
            OutlinedButton(
                onClick = {
                    viewModel.clearForm()
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                Text("Cancelar")
            }
        }
    }
}

/**
 * Campo personalizado para seleccionar fecha
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerField(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    enabled: Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    )

    OutlinedTextField(
        value = "${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}",
        onValueChange = {},
        label = { Text("Fecha") },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        enabled = enabled,
        trailingIcon = {
            TextButton(onClick = { showDatePicker = true }) {
                Text("Cambiar")
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            val newDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            onDateSelected(newDate)
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


/**
 * Campo personalizado para seleccionar hora
 */
@Composable
private fun TimePickerField(
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    enabled: Boolean
) {
    val context = LocalContext.current
    var showTimePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = "${selectedTime.hour.toString().padStart(2, '0')}:${
            selectedTime.minute.toString().padStart(2, '0')
        }",
        onValueChange = {},
        label = { Text("Hora") },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        enabled = enabled,
        trailingIcon = {
            TextButton(onClick = { showTimePicker = true }) {
                Text("Cambiar")
            }
        }
    )

    if (showTimePicker) {
        val timePickerDialog = remember {
            TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    onTimeSelected(LocalTime.of(hour, minute))
                    showTimePicker = false
                },
                selectedTime.hour,
                selectedTime.minute,
                true
            )
        }

        LaunchedEffect(Unit) {
            timePickerDialog.setOnDismissListener {
                showTimePicker = false
            }
            timePickerDialog.show()
        }
    }
}