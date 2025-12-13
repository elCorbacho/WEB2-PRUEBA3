# Prueba3 - Sistema de Gestión de Prácticas Profesionales

## 🚀 Ejecución del Programa

### Requisitos Previos
- Java 17 o superior
- Maven 3.8+

### Comandos de Ejecución

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

---

## 🌐 Rutas Web de Acceso

| Ruta | Descripción |
|------|-------------|
| `/` | Página de inicio |
| `/login` | Formulario de login |
| `/seleccionar-perfil` | Seleccionar rol (Estudiante/Profesor) |
| `/estudiante/dashboard` | Dashboard de estudiante |
| `/estudiante/practicas/lista` | Listar prácticas del estudiante |
| `/estudiante/practicas/crear` | Crear nueva práctica |
| `/profesor/dashboard` | Dashboard de profesor |
| `/profesor/practicas/agregar` | Agregar práctica |
| `/profesor/practicas/editar` | Editar práctica |

---

## 📡 Endpoints de API REST

### Base URL: `http://localhost:8080/api`

#### **Estudiantes**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/estudiantes` | Obtener todos los estudiantes |
| GET | `/estudiantes/{id}` | Obtener estudiante por ID |
| GET | `/estudiantes/{estudianteId}/practicas` | Listar prácticas del estudiante |
| POST | `/estudiantes/{estudianteId}/practicas` | Crear práctica como estudiante |

**Body de POST (ejemplo):**
```json
{
  "fechaInicio": "2025-02-01",
  "fechaFin": "2025-04-30",
  "actividades": "Desarrollo backend, pruebas, documentación",
  "empresa": {
    "id": 1
  },
  "profesor": {
    "id": 1
  }
}
```

---

#### **Profesores**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/profesores` | Obtener todos los profesores |
| GET | `/profesores/{id}` | Obtener profesor por ID |
| GET | `/profesores/{profesorId}/practicas` | Listar prácticas del profesor |
| POST | `/profesores/{profesorId}/practicas` | Crear práctica como profesor |
| DELETE | `/profesores/{profesorId}/practicas/{practicaId}` | Eliminar práctica |

**Body de POST (ejemplo):**
```json
{
  "estudiante": {
    "id": 2
  },
  "empresa": {
    "id": 3
  },
  "fechaInicio": "2025-01-15",
  "fechaFin": "2025-06-15",
  "actividades": "Desarrollo de aplicaciones web con Spring Boot y React"
}
```

---

## 📦 Respuesta Estándar de API

Todos los endpoints retornan el siguiente formato:

```json
{
  "status": 200,
  "message": "Descripción de la operación",
  "data": {},
  "timestamp": "2025-12-11T10:30:45.123456"
}
```

**Códigos de respuesta:**
- `200` - OK
- `201` - Creado
- `400` - Solicitud inválida
- `404` - No encontrado
- `500` - Error interno del servidor

---

## 🏗️ Stack Tecnológico

- **Spring Boot** 6.2.14
- **Spring Security** (Autenticación)
- **Jakarta Persistence (JPA)**
- **Lombok** (Reducción de boilerplate)
- **Jackson** (Serialización JSON)
- **H2 Database**
- **Maven** (Build Tool)

---

## 📚 Documentación Interactiva (Swagger)

```
http://localhost:8080/swagger-ui.html
```


## 📝 Notas
- CSRF deshabilitado para endpoints API
- Todos los endpoints API (`/api/**`) están permitidos
- Las respuestas incluyen timestamp para auditoría
- Los errores retornan mensajes descriptivos en el campo `error`

---

## 📸 Evidencias de Funcionamiento de la Aplicación

<details>
<summary><b>📱 Ver Capturas de Pantalla (Click para expandir)</b></summary>

<br>

<div align="center">

<table>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165644.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165652.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165658.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165705.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165711.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165716.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165720.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165725.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165730.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165736.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165747.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165753.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165813.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165817.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165821.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165848.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165852.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165904.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165910.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165925.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165930.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165935.png" width="100%"></td>
</tr>
<tr>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165946.png" width="100%"></td>
<td><img src="screenshots/Captura%20de%20pantalla%202025-12-13%20165953.png" width="100%"></td>
</tr>
</table>

</div>

</details>

---

## ✅ Funcionalidades Demostradas

- ✔️ Autenticación y gestión de perfiles (Estudiante/Profesor)
- ✔️ Dashboard personalizado por rol
- ✔️ CRUD completo de prácticas profesionales
- ✔️ API REST con endpoints documentados
- ✔️ Respuestas estandarizadas con códigos HTTP correctos
- ✔️ Validación de datos en formularios y API
- ✔️ Integración con base de datos H2
- ✔️ Logs y trazabilidad de operaciones

---

## 👨‍💻 Autor

Sistema desarrollado con Spring Boot para la gestión de prácticas profesionales.