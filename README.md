# 🎬 MoodFlix

> **Proyecto Integrador - Unidad 12: Fundamentos del Proyecto (Post 1)**
>
> MoodFlix es una plataforma móvil innovadora que revoluciona la forma en que los usuarios descubren contenido audiovisual (películas y series de televisión) al alinear las recomendaciones con su estado de ánimo actual.

---

## 👥 Integrantes del Equipo
* **Jhoseth Rozo**
* **Dylan Garzon**

---

## 🎯 1. Justificación y Problemática

### El Problema: La Paradoja de la Elección en el Streaming
En la era digital actual, los usuarios de plataformas de streaming se enfrentan diariamente a la "paradoja de la elección". Con miles de títulos disponibles al alcance de la mano, la toma de decisiones se vuelve abrumadora y a menudo resulta en "fatiga por decisión". 

Los algoritmos de recomendación tradicionales (utilizados por gigantes como Netflix o Amazon Prime Video) dependen casi exclusivamente del historial de visualización pasado o de tendencias demográficas generales. Sin embargo, este enfoque ignora un factor crucial en la toma de decisiones humana: **el estado emocional inmediato del usuario**. 

Un usuario que ha tenido un día difícil puede no querer ver un drama denso, a pesar de que sea su género favorito en su historial. Por el contrario, puede buscar una comedia reconfortante o un documental tranquilo. Los sistemas actuales no capturan este contexto emocional en tiempo real.

### La Solución: Recomendación Basada en Estados de Ánimo
**MoodFlix** nace para llenar este vacío crítico. Al centrar la experiencia del usuario en su estado de ánimo inmediato, la aplicación elimina la fricción de la elección. Los usuarios seleccionan cómo se sienten en el momento y MoodFlix les presenta al instante una selección premium de películas y series de televisión cuidadosamente curadas y filtradas que coinciden exactamente con esa vibra emocional.

---

## 🎨 Prototipo Figma
El diseño de la experiencia de usuario (UX/UI) ha sido conceptualizado con un enfoque moderno, minimalista y de alta fidelidad, asegurando transiciones suaves y microinteracciones que deleitan al usuario.

🔗 **[Ver Prototipo Interactivo en Figma](https://www.figma.com/design/moodflix-prototype-u12)**

---

## 🏗️ Arquitectura del Proyecto

MoodFlix está diseñado siguiendo los principios de **Clean Architecture** y la guía oficial de arquitectura de Android. Esto asegura que la aplicación sea altamente escalable, testeable y mantenible a largo plazo.

### Principios Clave:
* **Separación de Capas**: División clara entre la interfaz de usuario, la lógica de negocio y las fuentes de datos.
* **Offline-First**: La aplicación utiliza una base de datos Room local como fuente de verdad para el contenido seleccionado, ofreciendo una experiencia fluida incluso sin conexión a internet, con sincronización inteligente y fallback a la API de TMDB.
* **Inyección de Dependencias**: Acoplamiento débil facilitado por Hilt.

### Diagrama de Arquitectura de Capas
El proyecto se organiza bajo una arquitectura modular limpia dentro del módulo principal:

```
[ Capa de UI (Jetpack Compose / ViewModels) ]
                     │
                     ▼
  [ Capa de Dominio (Modelos / Interfaces de Repositorio) ]
                     ▲
                     │
  [ Capa de Datos (Room DB / Retrofit API / Repositories) ]
```

El diagrama detallado se puede encontrar en:
🔗 **[Diagrama de Arquitectura Detallado (docs/architecture-diagram.png)](docs/architecture-diagram.png)**

---

## 💻 Stack Tecnológico
* **Lenguaje principal**: [Kotlin](https://kotlinlang.org/) (100% moderno)
* **UI**: [Jetpack Compose](https://developer.android.com/compose) (UI declarativa con Material Design 3)
* **Inyección de Dependencias**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* **Base de Datos Local**: [Room Database](https://developer.android.com/training/data-storage/room) (Caché offline inteligente)
* **Conexión de Red**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) (Integración con TMDB y OMDb APIs)
* **Gestión de Corrutinas y Flujos**: Kotlin Coroutines & Flows (Programación reactiva asíncrona)
* **Carga de Imágenes**: [Coil Compose](https://coil-kt.github.io/coil/)
* **Gestión de Dependencias**: [Gradle Version Catalog](https://developer.android.com/build/migrate-to-catalogs) (Gestión centralizada de versiones)
