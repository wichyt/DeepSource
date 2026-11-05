# DeepSource Horas

App para registrar las horas trabajadas en los proyectos de DeepSource.
DSY1105 Desarrollo de Aplicaciones Móviles, Grupo 2.

## Cómo abrirla

1. Android Studio → Open → carpeta `DeepSourceHorasClase`.
2. Esperar que termine Gradle.
3. Ejecutar en el emulador o en un teléfono.

Cuenta de prueba: `luis.gomez@deepsource.cl` / `horas123`

## Pantallas

- Login: valida que el correo tenga @ y que la contraseña no esté vacía.
- Hoy: total del día, lo que falta de la jornada de 9 horas y la lista de registros (se pueden borrar).
- Registrar horas: formulario con proyecto, tarea, horas, minutos, tipo de hora y descripción.
- Perfil: foto con cámara o galería y modo oscuro.

## Cómo está ordenado (MVVM)

- `model`: los datos. Room (`local/entity`, `local/dao`, `AppDatabase`), DataStore (`AjustesDataStore`) y los repositorios.
- `viewmodel`: el estado de cada pantalla con StateFlow y las validaciones.
- `view`: las pantallas, los componentes, la navegación y el tema.

## Qué usamos de la evaluación

- Material 3 en todas las pantallas.
- Formularios validados: login y registrar horas.
- Navegación con NavHost y barra inferior.
- Estado con ViewModel, StateFlow y derivedStateOf.
- Almacenamiento local: Room para los registros y DataStore para el modo oscuro.
- Recursos nativos: cámara (con permiso) y galería.
- Animaciones: animateColorAsState, animateDpAsState y AnimatedVisibility.
