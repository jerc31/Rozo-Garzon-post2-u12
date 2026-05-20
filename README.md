# 🎬 MoodFlix

[![MoodFlix CI/CD Pipeline](https://github.com/jerc31/Rozo-Garzon-post2-u12/actions/workflows/ci.yml/badge.svg)](https://github.com/jerc31/Rozo-Garzon-post2-u12/actions/workflows/ci.yml)

> **Proyecto Integrador - Unidad 12: MVP Funcional, Pruebas Unitarias y CI/CD (Post 2)**
>
> MoodFlix es una plataforma móvil innovadora que revoluciona la forma en que los usuarios descubren contenido audiovisual (películas y series de televisión) al alinear las recomendaciones con su estado de ánimo actual.

---

## 👥 Integrantes del Equipo
* **Jhoseth Rozo** - [jhoseth331@gmail.com](mailto:jhoseth331@gmail.com)
* **Dylan Garzon** - [dylan.djg8413@gmail.com](mailto:dylan.djg8413@gmail.com)

---

## 🎯 1. Justificación y Problemática

### El Problema: La Paradoja de la Elección en el Streaming
En la era digital actual, los usuarios de plataformas de streaming se enfrentan diariamente a la "paradoja de la elección". Con miles de títulos disponibles al alcance de la mano, la toma de decisiones se vuelve abrumadora y a menudo resulta en "fatiga por decisión". 

Los algoritmos de recomendación tradicionales (utilizados por gigantes como Netflix o Amazon Prime Video) dependen casi exclusivamente del historial de visualización pasado o de tendencias demográficas generales. Sin embargo, este enfoque ignora un factor crucial en la toma de decisiones humana: **el estado emocional inmediato del usuario**. 

### La Solución: Recomendación Basada en Estados de Ánimo
**MoodFlix** nace para llenar este vacío crítico. Al centrar la experiencia del usuario en su estado de ánimo inmediato, la aplicación elimina la fricción de la elección. Los usuarios seleccionan cómo se sienten en el momento y MoodFlix les presenta al instante una selección premium de películas y series de televisión cuidadosamente curadas y filtradas que coinciden exactamente con esa vibra emocional.

---

## 🎨 Prototipo Figma
El diseño de la experiencia de usuario (UX/UI) ha sido conceptualizado con un enfoque moderno, minimalista y de alta fidelidad, asegurando transiciones suaves y microinteracciones que deleitan al usuario.

🔗 **[Ver Prototipo Interactivo en Figma](https://www.figma.com/design/moodflix-prototype-u12)**

---

## 🚀 2. Flujo Principal del MVP

El Producto Mínimo Viable (MVP) implementa un flujo de usuario intuitivo y de alto impacto estético:

1. **Pantalla de Autenticación (Login)**: Un acceso minimalista y elegante que da la bienvenida al usuario con animaciones de fondo sutiles.
2. **Pantalla de Inicio (Home)**:
   - **Selector de Estados de Ánimo (Moods)**: Una barra interactiva y colorida con 8 estados emocionales (Feliz, Triste, Emocionado, Asustado, Romántico, Aburrido, Nostálgico, Tranquilo).
   - **Carrusel Destacado**: Muestra una película recomendada especialmente basada en el estado de ánimo seleccionado.
   - **Sección de Películas**: Grid interactivo de películas recomendadas.
   - **Sección de Series de TV**: Grid interactivo de programas de televisión recomendados.
3. **Pantalla de Detalle (Detail)**: Al presionar cualquier película o serie, se abre una vista detallada con el póster en alta resolución, sinopsis completa, géneros, año de lanzamiento, popularidad y puntuaciones.

---

## 🎭 3. Estados de la Interfaz de Usuario (UI)

La aplicación maneja dinámicamente tres estados fundamentales de UI en sus pantallas principales para asegurar una experiencia de usuario robusta y premium:

### 🔄 A. Estado de Carga (Loading)
* **Descripción**: Se muestra cuando la aplicación está consultando la caché local o realizando peticiones de red asíncronas a las APIs externas.
* **Componente de UI**: Un efecto de **Shimmer** (esqueleto animado con gradiente grisáceo de barrido) que simula la forma de las tarjetas del carrusel y de las cuadrículas mientras se cargan los datos, evitando pantallas en blanco o indicadores de progreso circulares toscos.

###  B. Estado de Éxito (Success)
* **Descripción**: Se activa en cuanto la información es recuperada satisfactoriamente de la base de datos Room o de la red.
* **Componente de UI**: Muestra las listas organizadas con animaciones fluidas de entrada, transiciones suaves de imágenes usando Coil Compose y el carrusel de recomendación destacada listo para interactuar.

### ⚠️ C. Estado de Error (Error)
* **Descripción**: Maneja excepciones del sistema como la falta de conexión a internet, respuestas erróneas de la API (500, 404, etc.) o tokens expirados de forma elegante.
* **Componente de UI**: Una vista de error sumamente cuidada con un vector descriptivo, un mensaje claro que detalla la causa raíz (por ejemplo, "Sin conexión a internet") y un botón destacado de **"Reintentar" (Retry)** que permite refrescar la petición al instante de forma segura.

---

## 🧪 4. Pruebas Unitarias de ViewModels

Para garantizar la estabilidad y la mantenibilidad de la lógica de presentación del MVP, implementamos una suite robusta de pruebas unitarias dirigidas a los ViewModels principales, utilizando dobles de prueba (`Fakes`) de los repositorios para simular respuestas de datos y errores sin llamadas de red reales.

### Frameworks de Pruebas Utilizados:
- **JUnit 4**: Estructuración del ciclo de vida de los tests.
- **MockK**: Para mocking y espías de objetos si son requeridos.
- **Kotlinx Coroutines Test**: Proporciona `runTest` y `TestDispatcher` para probar flujos de corrutinas asíncronas de manera síncrona.
- **Arch Core Testing**: Proporciona `InstantTaskExecutorRule` para forzar la ejecución inmediata de LiveData o hilos en segundo plano.

### Pruebas Implementadas:

#### 🏠 `HomeViewModelTest` (Home & Mood Flow)
* **`uiState emits Success when repositories return data successfully`**: Valida que al iniciar el ViewModel y realizar peticiones exitosas de películas y series, la UI transicione correctamente al estado `Success` con los datos cargados.
* **`uiState emits Error when movie repository returns error`**: Simula una caída del servidor o falta de conexión y verifica que el ViewModel capture la excepción y exponga el estado `Error` con el mensaje correspondiente.
* **`selectMood changes current mood and loads new content`**: Verifica que al invocar `selectMood(MoodType.SAD)` el estado de ánimo actual cambie internamente y se gatille una nueva consulta de datos.
* **`retry loads content again`**: Asegura que el flujo de reintento ante un error previo cargue con éxito la información una vez que los repositorios están disponibles de nuevo.

#### 🎬 `DetailViewModelTest` (Movie Detail)
* **`uiState is initially Loading`**: Valida que al crearse el ViewModel, la vista de detalle se encuentre en estado inicial de carga.
* **`loadMovieDetail emits Success when repository returns data successfully`**: Valida que al proporcionar un ID de película válido, se recupere la información y se configure el estado `Success` de detalle con la película esperada.
* **`loadMovieDetail emits Error when repository returns failure`**: Valida que al fallar la búsqueda del detalle (por ejemplo, un error 404), la UI transicione al estado `Error` con un mensaje amigable de "Recurso no encontrado".

---

## 🏗️ 5. Arquitectura del Proyecto

MoodFlix sigue rigurosamente los principios de **Clean Architecture** estructurado por capas lógicas:

```
[ Capa de UI (Jetpack Compose / ViewModels) ]
                     │
                     ▼
  [ Capa de Dominio (Modelos / Interfaces de Repositorio) ]
                     ▲
                     │
  [ Capa de Datos (Room DB / Retrofit API / Repositories) ]
```

- **Capa de UI**: Vistas declarativas estructuradas en Compose (`HomeScreen`, `DetailScreen`, `LoginScreen`) y ViewModels encargados de mantener y emitir los estados de UI reactivos.
- **Capa de Dominio**: Contiene los modelos del negocio (`Movie`, `TvShow`, `MoodType`) y define los contratos de persistencia y red (interfaces `MovieRepository` y `TvRepository`).
- **Capa de Datos**: Implementa las interfaces de repositorio gestionando la base de datos Room offline-first (`MovieDao`, `MoodFlixDatabase`) y el cliente Retrofit de red (`TmdbApi`).

---

## 💻 Stack Tecnológico
* **Lenguaje**: [Kotlin](https://kotlinlang.org/) (100% moderno, Coroutines & Flows)
* **UI**: [Jetpack Compose](https://developer.android.com/compose) (Material Design 3, Shimmer effects)
* **Inyección de Dependencias**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* **Base de Datos Local**: [Room Database](https://developer.android.com/training/data-storage/room) (Caché offline inteligente)
* **Conexión de Red**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) (TMDB y OMDb APIs)
* **Gestión de Versiones**: [Gradle Version Catalog](https://developer.android.com/build/migrate-to-catalogs) (Gestión centralizada de versiones, sin hardcoding)

---

## ⚙️ 6. Pipeline de CI/CD (GitHub Actions)

El proyecto cuenta con una integración continua (CI) completamente automatizada a través de GitHub Actions. El flujo de trabajo está definido en `.github/workflows/ci.yml` y ejecuta las siguientes tareas ante cada `push` o `pull_request` a la rama `main`:

1. **Checkout Code**: Descarga el código fuente en el agente virtual runner de GitHub (`ubuntu-latest`).
2. **Set up JDK 17**: Configura el entorno de ejecución Java necesario para construir proyectos Android modernos con Gradle.
3. **Run Unit Tests**: Ejecuta de forma autónoma la suite de pruebas unitarias (`./gradlew testDebugUnitTest`). Si algún test falla, el pipeline se detiene y marca el commit con un estado de alerta.
4. **Build Debug APK**: Compila la aplicación en modo Debug (`./gradlew assembleDebug`).
5. **Upload Artifact**: Sube el APK empaquetado resultante (`app-debug.apk`) a los artefactos de la ejecución de GitHub Actions para que esté disponible para descargas inmediatas de testing.

---

## 🔑 7. Configuración de Firma de APK (Signing Config)

Para garantizar la seguridad de la distribución y cumplir con las directrices de publicación en Google Play Store, se ha configurado un bloque de firma de lanzamiento en el archivo `app/build.gradle.kts` utilizando variables de entorno. 

Este enfoque evita exponer contraseñas críticas o llaves en texto plano en el control de versiones de Git, permitiendo inyectarlas de forma segura como secretos del repositorio en GitHub Actions o cargarlas localmente en el archivo `local.properties`:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("keystore.jks")
        storePassword = System.getenv("RELEASE_STORE_PASSWORD") ?: "placeholderPassword"
        keyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: "placeholderAlias"
        keyPassword = System.getenv("RELEASE_KEY_PASSWORD") ?: "placeholderPassword"
    }
}
```

---

## 🛠️ 8. Instrucciones para Compilar y Ejecutar

### Requisitos Previos:
- Java JDK 17 instalado en tu sistema.
- Android Studio Ladybug (o posterior).
- Las API keys reales ya están inyectadas de forma segura y transparente dentro del archivo `app/build.gradle.kts` bajo la configuración de `BuildConfig` para facilitar una compilación instantánea de demostración sin necesidad de setups adicionales.

### Ejecución de Pruebas Unitarias:
Para correr la suite completa de pruebas unitarias del ViewModel desde la consola, ejecuta:
```bash
./gradlew testDebugUnitTest
```

### Compilar el APK de Depuración (Debug):
Para compilar y generar el paquete APK de depuración ejecutable en cualquier dispositivo Android:
```bash
./gradlew assembleDebug
```
*El APK resultante se almacenará en:* `app/build/outputs/apk/debug/app-debug.apk`.
