# 🏃‍♀️ ActivityApp

Aplicación Android nativa para registrar y gestionar actividades diarias.
Desarrollada con Kotlin, Jetpack Compose y arquitectura MVVM.
Diseño moderno, fluido y totalmente reactivo.

---

## ✨ Características principales

- ➕ **Agrega** actividades con título, descripción y horario
- 📋 **Lista y elimina** tus actividades fácilmente
- 🎨 **Animaciones suaves** con Jetpack Compose
- ⚡ **Estado reactivo** con `StateFlow` y `Coroutines`
- 🧭 **Navegación desacoplada** mediante `Navigation Compose`
- 📱 Diseño moderno basado en **Material Design 3**

---

## 🧩 Estructura del proyecto

| 🧱 **Componente**        | 💡 **Función principal**                            |
|---------------------------|----------------------------------------------------|
| `MainActivity`            | Inicializa Compose y el `ViewModel` global         |
| `ActivityViewModel`       | Gestiona la lógica, estado y coroutines            |
| `AppNavigation`           | Define las rutas entre pantallas                  |
| `ui/screens`              | Pantallas Compose (listado y formulario)           |
| `data/ActivityItem`       | Modelo de datos de actividad                      |

---

## 🛠️ Tecnologías utilizadas

- 💻 **Lenguaje:** Kotlin
- 🎨 **UI:** Jetpack Compose
- 🧠 **Arquitectura:** MVVM
- ⚙️ **Asincronía:** Coroutines + StateFlow
- 🧭 **Navegación:** Navigation Compose

---

## 🚀 Cómo ejecutar el proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/marglodis/activityapp.git
2. Abre el proyecto en Android Studio
3. Ejecuta en un emulador o dispositivo físico
3. ¡Empieza a registrar tus actividades diarias! 🗓️

### 🔄 Vistas de Interacción con la Aplicación

1. **Pantalla Principal y Formulario de Registro de Actividades**

<p float="left">
  <img src="screenshots/home_and_empty_screen.webp" alt="Pantalla Principal" width="200"/>
  <img src="screenshots/form_screen.webp" alt="Registro de actividades" width="200"/>
  <img src="screenshots/validation.png" alt="Validación de campos" width="200"/>
</p>

2. **Date and Time Picker, Circular Progress Indicator**

<p float="left">
  <img src="screenshots/circular_progress.webp" alt="Circular Progress" width="200"/>
  <img src="screenshots/datepicker.webp" alt="Seleccionar fecha" width="200"/>
  <img src="screenshots/timepicker.webp" alt="Seleccionar hora" width="200"/>
</p>

3. **Pantalla con lista de actividades**
<p float="left">
  <img src="screenshots/list_activities.webp" alt="Listado de actividades" width="200"/>
  <img src="screenshots/delete_dialog_alert.webp" alt="Dialogo de eliminación" width="200"/>
  <img src="screenshots/list_activity_after_delete_one.webp" alt="Listado luego de eliminar" width="200"/>
</p>