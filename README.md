# NextTasks-back — Backend en Java Spring Boot  
⚙️ API REST para la aplicación de gestión de tareas estilo **Trello**

> Este proyecto es el **backend** de la plataforma NextTasks, un sistema completo para crear, gestionar y organizar tareas, tableros y usuarios. Implementado con **Spring Boot** y principios REST para integrarse con múltiples frontends incluyendo micro-frontends. :contentReference[oaicite:0]{index=0}

---

## 🧠 Descripción del proyecto

NextTasks-back es una API REST construida con **Java y Spring Boot** como parte del ecosistema NextTasks.  
Su objetivo es proporcionar todos los servicios backend necesarios para:

- CRUD de usuarios, tableros y tareas  
- Autenticación y control de permisos  
- Integración con otros micro-servicios / frontends  
- Persistencia de datos con una base de datos relacional o NoSQL

Este proyecto demuestra habilidades en desarrollo backend moderno con buenas prácticas, arquitectura limpia y REST, útiles para roles de **Backend Developer y Full-stack Developer**.

---

## ⚙️ Tecnologías utilizadas

- 🟦 **Java (Spring Boot)**  
- 📡 **Spring Web (REST API)**  
- 🛡️ **Spring Security / JWT** (si lo tienes implementado)  
- 🗄️ **JPA / Hibernate** (si usas ORM)  
- ☁️ **Bases de datos:** Oracle / MySQL / MongoDB (según configuración)  
- 🧪 **Pruebas:** JUnit / Mockito (si están incluidos)  
- 📦 **Construcción:** Maven / Gradle  
- 📜 **Documentación de API:** OpenAPI / Swagger (si está presente) :contentReference[oaicite:1]{index=1}

---

## 🚀 Funcionalidades principales

✔ Endpoints REST para entidades principales  
✔ Gestión de autenticación y autorización (JWT opcional)  
✔ patrones arquitectura en capas (Controller, Service, Repository)  
✔ Integración con frontend (NextTasksFront)  
✔ Buenas prácticas de código y estructura

---

## 📌 Estructura del proyecto (modelo típico)

├── main
│ ├── java
│ │ └── com/tu/paquete
│ │ ├── controller # Endpoints REST
│ │ ├── service # Lógica de negocio
│ │ ├── repository # Persistencia
│ │ ├── model # Entidades / DTOs
│ │ └── config # Configuración Spring
│ └── resources
│ ├── application.yml # Configuraciones
│ └── ...


---

## 📘 Endpoints principales

> Aquí deberías documentar tus rutas REST más importantes. Ejemplos:

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/boards` | Lista todos los tableros |
| POST | `/api/tasks` | Crea una nueva tarea |
| PUT | `/api/tasks/{id}` | Actualiza una tarea |
| DELETE | `/api/boards/{id}` | Elimina un tablero |

*(Reemplaza con tus rutas reales según tu código.)*

---

## 🛠️ Cómo ejecutar localmente

### Requisitos
- Java 17+  
- Maven o Gradle  
- Base de datos configurada (Oracle/MySQL/MongoDB)

### Pasos
# 1. Clona el repositorio  

git clone https://github.com/Jechig0/NextTasks-back.git

# Configura tu base de datos en application.yml o application.properties

# Ejecuta la API
mvn spring-boot:run
# o con Gradle
gradle bootRun

Accede a la API en:
http://localhost:8080
