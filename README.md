# 🌿 TRIBU - App para familias

Aplicación Android desarrollada como Trabajo de Fin de Grado (TFG) cuyo objetivo es conectar familias para organizar actividades y quedadas.

---

## 📱 Funcionalidades principales

- 🔐 Autenticación de usuarios (Firebase Auth)
- 👨‍👩‍👧‍👦 Gestión de perfil familiar
- 📅 Creación de quedadas
- 📋 Visualización de planes
- ☁️ Almacenamiento en la nube (Firestore)
- 🔍 Clasificación por tipo de actividad

---

## 🧠 Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Firebase Authentication
- Cloud Firestore
- Android Studio

---

## 🚀 Estructura del proyecto

- `ui/` → pantallas (home, crear, lista, perfil, auth)
- `model/` → modelo de datos (Plan)
- `MainActivity.kt` → navegación principal

---

## 🔐 Autenticación

Los usuarios pueden:
- Registrarse con email y contraseña
- Iniciar sesión
- Cerrar sesión

---

## ☁️ Base de datos

Se utiliza **Cloud Firestore** para:
- Guardar quedadas
- Guardar perfil familiar

---

## 👨‍👩‍👧‍👦 Perfil familiar

Cada usuario puede registrar:
- Nombre de familia
- Zona
- Número y edad de hijos
- Intereses

---

## 🎯 Objetivo del proyecto

Fomentar la conexión entre familias y promover el ocio saludable mediante la organización de actividades compartidas.

---

## 👩‍💻 Autora

Proyecto desarrollado por Mª José García Martagón

---

## 🔗 Repositorio

https://github.com/MRJSMRTG/tribu-app.git