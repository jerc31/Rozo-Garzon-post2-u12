# MoodFlix

[![MoodFlix CI/CD Pipeline](https://github.com/jerc31/Rozo-Garzon-post2-u12/actions/workflows/ci.yml/badge.svg)](https://github.com/jerc31/Rozo-Garzon-post2-u12/actions/workflows/ci.yml)

**Integrantes:**

- Jhoseth Esneider Rozo Carrillo
- Dylan Garzon

**Programa:** Ingeniería de Sistemas  
**Unidad:** Unidad 12 – Proyecto Integrador  
**Actividad:** Post-Contenido 2  
**Fecha:** 2026

---

## Descripción del Proyecto

MoodFlix es una aplicación móvil enfocada en la recomendación inteligente de películas y series basada en el estado emocional del usuario.

La aplicación busca solucionar el problema de la sobrecarga de opciones en plataformas de streaming mediante recomendaciones dinámicas alineadas con el mood actual del usuario.

En esta entrega se implementa el MVP funcional del proyecto integrador aplicando:

- Clean Architecture
- Arquitectura MVVM
- Modularización Gradle
- Pruebas unitarias
- Pipeline CI/CD con GitHub Actions
- Publicación en Google Play Internal Testing
- Cobertura mínima con JaCoCo

---

## Objetivo

Implementar el MVP funcional del proyecto integrador garantizando:

- Flujo principal completamente funcional
- Manejo de estados Loading, Success y Error
- Arquitectura limpia desacoplada por capas
- Cobertura mínima de pruebas unitarias
- Automatización CI/CD con GitHub Actions
- Generación y firma del AAB
- Publicación en Google Play Internal Testing

---

## Arquitectura del Proyecto

MoodFlix implementa Clean Architecture y MVVM para garantizar:

- Escalabilidad
- Separación de responsabilidades
- Bajo acoplamiento
- Facilidad de testing
- Mantenimiento a largo plazo

---

### Arquitectura General

````text
[ UI Layer - Compose / ViewModels ]
                │
                ▼
[ Domain Layer - UseCases / Models ]
                ▲
                │
[ Data Layer - Room / Retrofit / Repository ]

---

## 1. Justificación y Problemática

### El Problema: La Paradoja de la Elección en el Streaming
En la era digital actual, los usuarios de plataformas de streaming se enfrentan diariamente a la "paradoja de la elección". Con miles de títulos disponibles al alcance de la mano, la toma de decisiones se vuelve abrumadora y a menudo resulta en "fatiga por decisión".

Los algoritmos de recomendación tradicionales (utilizados por gigantes como Netflix o Amazon Prime Video) dependen casi exclusivamente del historial de visualización pasado o de tendencias demográficas generales. Sin embargo, este enfoque ignora un factor crucial en la toma de decisiones humana: **el estado emocional inmediato del usuario**.

### La Solución: Recomendación Basada en Estados de Ánimo
**MoodFlix** nace para llenar este vacío crítico. Al centrar la experiencia del usuario en su estado de ánimo inmediato, la aplicación elimina la fricción de la elección. Los usuarios seleccionan cómo se sienten en el momento y MoodFlix les presenta al instante una selección premium de películas y series de televisión cuidadosamente curadas y filtradas que coinciden exactamente con esa vibra emocional.

---

## Prototipo Figma
El diseño de la experiencia de usuario (UX/UI) ha sido conceptualizado con un enfoque moderno, minimalista y de alta fidelidad, asegurando transiciones suaves y microinteracciones que deleitan al usuario.

**[Ver Prototipo Interactivo en Figma](https://www.figma.com/design/moodflix-prototype-u12)**

---

## 2. Flujo Principal del MVP

El Producto Mínimo Viable (MVP) implementa un flujo de usuario intuitivo y de alto impacto estético:

1. **Pantalla de Autenticación (Login)**: Un acceso minimalista y elegante.
2. **Pantalla de Inicio (Home)**: Selector de moods y recomendaciones dinámicas.
3. **Pantalla de Detalle (Detail)**: Información completa del contenido.

### Checkpoint: Estados de la Interfaz (Capturas)
A continuación se detallan los tres estados fundamentales manejados por la aplicación:

| Estado | Captura | Descripción |
|--------|---------|-------------|
| **Loading** | ![Loading](docs/screenshots/loading.png) | Efecto **Shimmer** animado que simula la carga de datos. |
| **Success** | ![Success](docs/screenshots/success.png) | Listas de películas y series cargadas satisfactoriamente. |
| **Error** | ![Error](docs/screenshots/error.png) | Vista de error con ilustración, mensaje claro y botón de **Reintentar**. |

---

## 3. Pruebas Unitarias y Cobertura

Se ha implementado una suite de pruebas robusta superando el **60% de cobertura** en la capa de dominio/presentación.

### Ejecución de Pruebas:
```bash
./gradlew testDebugUnitTest
````

### Generación de Reporte de Cobertura (JaCoCo):

```bash
./gradlew jacocoTestReport
```

El reporte se generará en `app/build/reports/jacoco/jacocoTestReport/html/index.html`.

---

---

## Stack Tecnológico

- **Lenguaje**: Kotlin (Coroutines & Flows)
- **UI**: Jetpack Compose (Material 3)
- **DI**: Hilt
- **DB**: Room
- **Red**: Retrofit & OkHttp
- **CI/CD**: GitHub Actions

---

## 5. Pipeline de CI/CD (GitHub Actions)

El proyecto cuenta con integración continua automatizada:

- **Lint**: Verificación de calidad de código.
- **Unit Tests**: Ejecución automática de pruebas.
- **Build**: Compilación del APK.
- **Artifacts**: Almacenamiento del APK resultante.

---

## 6. Publicación (Internal Testing)

El artefacto (AAB) está configurado para ser firmado y subido al **Internal Testing track** de Google Play Console.

- **Secretos configurados**: `KEYSTORE_BASE64`, `KEY_ALIAS`, `KEY_PASSWORD`, `STORE_PASSWORD`.
- **Enlace de Testing**: [Acceder al Internal Testing](https://play.google.com/apps/internaltest/moodflix-testing)

---

## 7. Instrucciones para Compilar y Ejecutar

### Requisitos:

- JDK 17
- Android Studio Ladybug+

### Compilar APK:

```bash
./gradlew assembleDebug
```

_Localización:_ `app/build/outputs/apk/debug/app-debug.apk`.
