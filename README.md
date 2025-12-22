# PET FOUNDING
**Propuesta Técnica de Desarrollo de Software**
**Proyecto:** Plataforma Web **PET FOUNDING**

---

##  1. Resumen Ejecutivo

El presente documento detalla la arquitectura, tecnologías y alcance funcional para el desarrollo de **PET FOUNDING**.
El objetivo principal de la plataforma es **facilitar la conexión entre refugios de animales y adoptantes potenciales**, integrando además un **sistema de recaudación de fondos (fundraising) seguro, transparente y eficiente**.

---

## 2. Stack Tecnológico Propuesto

Para garantizar **escalabilidad, modularidad y alto rendimiento**, se define la siguiente arquitectura técnica:

### 🔹 Frontend

* **Framework:** React (con Vite)
* **Gestión de estado:** Redux
* **Ruteo:** React Router
* **Estilos:** Tailwind CSS

**Justificación:**
React junto con Vite permite un desarrollo ágil y moderno. Redux resulta clave para manejar estados complejos como sesión de usuario, carrito de donaciones y filtros de búsqueda de manera predecible.

---

### 🔹 Backend

* **Lenguaje / Framework:** Java (Spring Boot)
* **Persistencia:** Spring Data JPA
* **Seguridad:** Spring Security (JWT, roles y permisos)
* **Gestión de dependencias:** Maven o Gradle

**Justificación:**
Spring Boot facilita la creación de una **API REST robusta, segura y escalable**, aprovechando el tipado fuerte y la madurez del ecosistema Java.

---

### 🔹 Base de Datos

* **Producción:** PostgreSQL o MySQL
* **Testing:** H2 (opcional)

**Justificación:**
Motores relacionales confiables que garantizan la integridad de los datos transaccionales (donaciones) y las relaciones entre entidades (usuarios, refugios y mascotas).

---

### 🔹 DevOps y Despliegue

* **Contenedores:** Docker
* **Control de versiones:** Git
* **Cloud / Deploy:** AWS, Google Cloud, Heroku o Render

**Justificación:**
Docker asegura consistencia entre entornos de desarrollo, testing y producción, facilitando la escalabilidad y el despliegue continuo.

---

##  3. Objetivos del Proyecto

La solución busca cubrir las siguientes necesidades estratégicas:

* **Funcionalidad Core:** Centralizar la interacción entre refugios, rescatistas y adoptantes.
* **Fundraising:** Proveer un mecanismo seguro para procesar donaciones monetarias.
* **Gestión Autónoma:** Permitir que los refugios gestionen sus perfiles, mascotas y solicitudes sin intermediarios.
* **Seguridad:** Protección de datos sensibles mediante autenticación JWT y roles diferenciados.
* **Experiencia de Usuario (UX):** Interfaz moderna, intuitiva y responsive.

---

##  4. Alcance Funcional – Frontend (SPA)

La aplicación contará con vistas gestionadas por **React Router** y estado global compartido mediante **Redux**.

### 4.1 Vistas Públicas

* **Inicio (Landing Page):**

  * Presentación de la misión del proyecto
  * Mascotas destacadas
  * Métricas de impacto
  * Llamadas a la acción (Adoptar / Donar)

* **Catálogo de Mascotas:**

  * Listado con tarjetas informativas
  * Filtros avanzados (especie, edad, ubicación)
  * Persistencia de filtros mediante Redux

* **Detalle de Mascota:**

  * Información completa
  * Galería de imágenes
  * Historia clínica
  * Botón de **Solicitar Adopción**

---

### 4.2 Vistas Privadas y Transaccionales

* **Módulo de Donaciones:**

  * Aportes únicos o recurrentes
  * Selección de método de pago

* **Panel de Usuario:**

  * Historial de solicitudes de adopción
  * Estado de solicitudes
  * Historial de donaciones

* **Dashboard de Refugio (Administrador):**

  * ABM de mascotas
  * Gestión de solicitudes de adopción
  * Visualización de reportes financieros

---

##  5. Arquitectura del Backend – API REST

La API expone endpoints RESTful con intercambio de datos en formato **JSON**.

### 5.1 Gestión de Mascotas

* `GET /api/v1/mascotas` → Listado general con paginación y filtros
* `GET /api/v1/mascotas/{id}` → Detalle de mascota
* `POST /api/v1/mascotas` → Crear mascota *(Rol Refugio/Admin)*
* `PUT /api/v1/mascotas/{id}` → Actualizar mascota *(Rol Refugio/Admin)*

---

### 5.2 Usuarios y Seguridad

* `POST /api/v1/auth/registro` → Registro de usuarios
* `POST /api/v1/auth/login` → Login y emisión de JWT
* `GET /api/v1/usuarios/perfil` → Perfil del usuario autenticado

---

### 5.3 Operaciones de Negocio

#### 🐶 Adopciones

* `POST /api/v1/adopciones` → Crear solicitud de adopción
* `GET /api/v1/adopciones/pendientes` → Solicitudes para refugios
* `PATCH /api/v1/adopciones/{id}/estado` → Aprobar / Rechazar solicitud

#### Donaciones

* `POST /api/v1/donaciones` → Procesamiento de donación
* `GET /api/v1/donaciones/historial` → Historial de donaciones

---

## Integración de Pasarela de Pago – Mercado Pago

La plataforma integra **Mercado Pago** como pasarela oficial para la recepción y gestión de donaciones, garantizando **transacciones seguras** y conformes a los estándares vigentes.

### Endpoints asociados:

* `POST /api/v1/donaciones` → Validación y procesamiento de pagos
* `GET /api/v1/donaciones/historial` → Consulta detallada del historial

---

## Estado del Proyecto

 **Fase:** Diseño y definición técnica
 **Arquitectura:** SPA + API REST
**Enfoque:** Escalabilidad, seguridad y experiencia de usuario

---

🐾 *PET FOUNDING — Tecnología al servicio de la adopción y el bienestar animal.*
