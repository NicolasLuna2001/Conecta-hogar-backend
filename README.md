
<div align="center">

# 🏠 Conecta Hogar - Backend API REST

<p align="center">
  <img src="https://github.com/valentina-lulic/conecta-hogar-web/blob/main/src/assets/icons/logoCH.jpeg" width="180" alt="Conecta Hogar"/>
</p>

### Documentación oficial de la arquitectura, tecnologías y estructura de paquetes del Backend para la plataforma ConectaHogar.

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)

</div>

---

# 📖 Descripción

**ConectaHogar Backend** es la API REST encargada de proporcionar toda la lógica de negocio, seguridad, autenticación y persistencia de datos para la plataforma ConectaHogar. 

La aplicación permite la gestión de usuarios, autenticación basada en JWT, administración de maestros/profesionales de servicios del hogar, carga de imágenes y un sistema de valoraciones.

---

# 🛠 Tecnologías Utilizadas

| Tecnología | Descripción |
|------------|------------|
| Java (JDK 17+) | Lenguaje de programación principal |
| Spring Boot 3 | Framework principal para el desarrollo de la API REST |
| Spring Security + JWT | Seguridad y gestión de tokens de autenticación |
| Apache Maven | Gestión de dependencias (`pom.xml` / `./mvnw`) |
| MapStruct / ModelMapper | Mapeo y conversión entre Entidades y DTOs |
| Spring Data JPA / Hibernate | Persistencia y gestión de base de datos ORM |
| Application.yaml + .env | Configuración de la aplicación y variables de entorno |
| Lombok | Reducción de código boilerplate |

---

# 📁 Estructura del Proyecto

El proyecto sigue una **Arquitectura en Capas (Layered Architecture)** organizada por paquetes dentro del paquete raíz `com.example.conecta_hogar`:

```
CursoSpringBoot/
├── .mvn/                             ← Wrapper de Maven
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/conecta_hogar/
│   │   │       ├── auth/             ← Controladores y DTOs específicos de autenticación
│   │   │       ├── config/           ← Configuraciones globales (CORS, Mappers, Beans)
│   │   │       ├── controller/       ← Endpoints de la API REST (MaestroController, etc.)
│   │   │       ├── dto/              ← Data Transfer Objects (Requests y Responses)
│   │   │       ├── mapper/           ← Conversión entre Entidades (Model) y DTOs
│   │   │       ├── model/            ← Entidades JPA que mapean con la Base de Datos
│   │   │       ├── repository/       ← Interfaces JPA Repository (consultas a DB)
│   │   │       ├── security/         ← Filtros JWT, UserDetailsService y SecurityConfig
│   │   │       ├── service/          ← Lógica de negocio (Interfaces e Implementaciones)
│   │   │       └── ConectaHogarApplication.java  ← Clase Principal de Spring Boot
│   │   │
│   │   └── resources/
│   │       └── application.yaml      ← Configuración de la app (Base de datos, JPA, JWT)
│   │
│   └── test/                         ← Pruebas unitarias y de integración
│
├── uploads/                          ← Carpeta física donde se almacenan las fotos
├── .env                              ← Variables de entorno locales (credenciales DB, JWT Secret)
├── .gitignore                        ← Archivos excluidos de Git
├── mvnw / mvnw.cmd                   ← Scripts ejecutables de Maven Wrapper
└── pom.xml                           ← Configuración de dependencias de Maven
```

---

# 🏛 Arquitectura

El sistema está estructurado bajo un flujo en capas responsabilidades bien definidas:

```
Cliente / Frontend

↓

Controller (API REST Endpoints)

↓

Service (Lógica de Negocio)

↓

Repository (Spring Data JPA)

↓

Base de Datos (MySQL / PostgreSQL)
```

---

# 🚀 Instalación y Ejecución Local

## 1. Requisitos Previos

- **Java JDK:** `>= 17`
- **Base de Datos:** MySQL / PostgreSQL corriendo en servidor local o remoto.

---

## 2. Clonar el repositorio

```bash
git clone https://github.com/NicolasLuna2001/Conecta-hogar-backend.git
```

---

## 3. Entrar al proyecto

```bash
cd Conecta-hogar-backend
```

---

## 4. Configurar variables de entorno

Crear o configurar el archivo `.env` en la raíz del proyecto:

```env
DB_URL=jdbc:mysql://localhost:3306/conecta_hogar
DB_USERNAME=root
DB_PASSWORD=tu_contraseña
JWT_SECRET=tu_clave_secreta_super_segura
```

---

## 5. Ejecutar la aplicación

### En Linux/Mac:
```bash
./mvnw spring-boot:run
```

### En Windows (CMD/PowerShell):
```cmd
mvnw.cmd spring-boot:run
```

La API estará disponible y escuchando en: `http://localhost:8080`

---

# 🌐 Endpoints Principales

### 🔑 Autenticación (`/auth`)

- `POST /auth/login` → Autenticación de usuario y retorno de JWT.
- `POST /auth/register` → Registro de nuevos usuarios.

### 👷 Maestros (`/maestros`)

- `GET /maestros` → Obtener lista completa de maestros.
- `GET /maestros/top` → Obtener el Top 5 de maestros mejor valorados.
- `POST /maestros/con-foto` → Crear un maestro adjuntando su foto (`multipart/form-data`).
- `PUT /maestros/{id}/foto` → Actualizar la foto de un maestro existente (`multipart/form-data`).
- `PATCH /maestros/{id}/me-gusta` → Incrementar valoración positiva.
- `PATCH /maestros/{id}/no-me-gusta` → Incrementar valoración negativa.

---

# 📌 Convenciones de Commits

Para mantener el historial limpio y ordenado, se utilizan los siguientes prefijos:

- `feat:` Nuevas funcionalidades (ej. `feat: agregar endpoint para actualizar foto de maestro`)
- `fix:` Solución de errores (ej. `fix: corregir ruta de carga de archivos`)
- `refactor:` Cambios de código que no modifican la funcionalidad existente.

---

# 👨‍💻 Equipo de desarrollo

<div align="center">

<p align="center">
  <img
    src="https://github.com/bunni3sby/ImagenesREADMEConectaHogar/blob/b982ecf50b012b76c3df8eec564883a2e3b7809b/Imagenes/CodeToCash.jpg"
    alt="Code To Cash"
    width="250">
</p>

# CODE TO CA$H
</div>




- Valentina Lulic | Front End
- Denisse Labrana | Front End
- Benjamin Pinto | Front End
- Nicolas Luna | Back End
- Jorge Gatica | Back End
- Aaron Guerra | Back end
