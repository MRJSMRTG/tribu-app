# 🌿 TRIBU - App social para familias

TRIBU es una aplicación Android desarrollada como Trabajo de Fin de Grado cuyo objetivo es conectar familias para organizar actividades, compartir planes y fomentar el ocio saludable.

La aplicación permite crear quedadas familiares, gestionar perfiles, confirmar asistencia, interactuar mediante comentarios y utilizar funcionalidades sociales como el Modo Parque.

---

## 📱 Funcionalidades principales

### 👨‍👩‍👧‍👦 Gestión de perfil familiar

Cada familia puede registrar:

- Nombre de familia
- Zona de residencia
- Número y edad de hijos
- Intereses


### 📅 Gestión de quedadas

La aplicación permite realizar un CRUD completo sobre las quedadas.

#### Crear

Las familias pueden crear nuevas quedadas indicando:

- Título
- Lugar
- Fecha
- Tipo de actividad
- Descripción
- Precio opcional

#### Consultar

La aplicación permite visualizar:

- Lista de quedadas disponibles
- Detalle completo de cada actividad

#### Editar

Se pueden modificar los datos principales de una quedada:

- Título
- Fecha
- Lugar
- Descripción
- Tipo
- Precio

#### Eliminar

La aplicación permite eliminar quedadas almacenadas en Firestore.

### 👥 Confirmación de asistencia

Las familias pueden indicar cuántos miembros asistirán a una actividad.

Ejemplo:

- Familia de 4 miembros: suma 4 asistentes

El contador se actualiza mediante Cloud Firestore.

### 💬 Comentarios

Cada quedada dispone de un pequeño muro de comentarios para la coordinación entre familias.

Permite:

- Escribir comentarios
- Guardar comentarios en Firestore
- Visualizar comentarios previos de cada quedada

### 🗺️ Integración con mapas

Cada quedada permite abrir su ubicación directamente en una aplicación de mapas externa, como Google Maps.

Esto facilita encontrar rápidamente el punto de encuentro.


### 🌳 Modo Parque

Funcionalidad social que permite:

- Indicar que una familia se encuentra en un parque
- Visualizar otras familias activas
- Ver en qué parque están
- Abrir su ubicación en mapa
- Activar o desactivar el estado

Esta funcionalidad está planteada como una versión MVP de una futura geolocalización en tiempo real.

---

## 🔐 Autenticación

Sistema de autenticación mediante Firebase Authentication:

- Registro de usuario
- Inicio de sesión
- Cierre de sesión

---

## ☁️ Base de datos

Persistencia de datos en la nube mediante Cloud Firestore.

Colecciones utilizadas:

- usuarios
- planes
- comentarios
- modoParque

---

## 🧠 Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Firebase Authentication
- Cloud Firestore
- Android Studio
- Integración externa con Google Maps

---

## 🏗️ Arquitectura general

Estructura principal del proyecto:

- app/model
- app/ui/auth
- app/ui/home
- app/ui/crear
- app/ui/lista
- app/ui/detalle
- app/ui/editar
- app/ui/perfil
- app/ui/parque

---

## 🎯 Objetivo del proyecto

Crear una aplicación social orientada a familias que facilite:

- Encontrar actividades
- Organizar encuentros
- Compartir ocio saludable
- Generar comunidad local

---

## 🚀 Futuras mejoras

- Geolocalización automática en tiempo real
- Notificaciones push
- Chat en tiempo real
- Subida de imágenes
- Sistema de favoritos
- Filtros avanzados por edad y actividad

---

## 👩‍💻 Autora

María José García Martagón

Trabajo de Fin de Grado

---

## 🔗 Repositorio

https://github.com/MRJSMRTG/tribu-app.git