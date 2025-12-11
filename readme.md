# Prueba3 - Sistema de Gestión de Prácticas Profesionales.

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
  "empresa": {
    "id": 1
  },
  "profesor": {
    "id": 1
  },
  "fechaInicio": "2025-01-15",
  "fechaFin": "2025-06-30",
  "actividades": "Desarrollo de aplicaciones web"
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
    "id": 1
  },
  "empresa": {
    "id": 1
  },
  "fechaInicio": "2025-01-15",
  "fechaFin": "2025-06-30",
  "actividades": "Desarrollo de aplicaciones web"
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