# 🦷 Proyecto de Registro de Turnos - Clínica Odontológica

Este proyecto es un sistema de gestión de turnos para una clínica odontológica. Facilita el registro, listado, modificación y eliminación de turnos, así como la administración de la información de pacientes y odontólogos.

## 👨🏻‍💻 *Integrantes*
- Oriana Valentina Guerrero Torrado
- Claudio Van Anghelo Flores Uceda

---

## ✨ *Características Principales*
- *Registro de Turnos*: Permite seleccionar una fecha y hora para el turno dentro del horario de atención (08:00 a 18:00 horas).
- *Validaciones*: Asegura que los turnos no sean agendados fuera del horario permitido.
- *Gestión de Pacientes*: Funcionalidad para crear, modificar, listar y eliminar pacientes.
- *Gestión de Odontólogos*: Funcionalidad para crear, modificar, listar y eliminar odontólogos.
- *Interfaz Amigable*: Diseño limpio y fácil de usar, adaptado a una clínica odontológica.
- *Registro de Logs: Utilización de **Log4j* para gestionar los eventos importantes del sistema.
- *Pruebas Unitarias: Pruebas implementadas con **JUnit* para garantizar la calidad y confiabilidad del código.

---

## 🚀 *Tecnologías Utilizadas*
### Backend
- *Java*: Lenguaje de programación principal para el desarrollo del backend.
- *Spring Boot*:
  - spring-boot-starter-web: Para el desarrollo de aplicaciones web y APIs REST.
  - spring-boot-starter-data-jpa: Para la integración con bases de datos mediante *Hibernate*.
  - spring-boot-starter-test: Para pruebas unitarias e integración.
- *JPA*: Para la gestión de persistencia y operaciones CRUD.
- *Hibernate*: ORM para interactuar con la base de datos.
- *Maven*: Para la gestión de dependencias y automatización del proyecto.
- *Lombok*: Para simplificar la creación de getters, setters y métodos repetitivos.
- *Log4j (v1.2.17)*: Para la gestión y registro de logs del sistema.

### Frontend
- *JavaScript*: Para agregar interactividad en la aplicación.
- *HTML/CSS*: Para estructurar y estilizar el frontend.
- *Bootstrap*: Framework CSS para crear una interfaz moderna y responsiva.

### Base de Datos
- *H2*: Base de datos en memoria, utilizada para pruebas y desarrollo rápido.

---

## 🛠 *Configuración del Proyecto*
- *Puerto: La aplicación se ejecuta por defecto en el puerto **8080*. Si deseas cambiarlo, puedes modificar el archivo application.properties.

---

## 🧪 *Pruebas y Calidad*
- *JUnit*: Herramienta utilizada para realizar pruebas unitarias, asegurando la calidad del código en los distintos módulos de la aplicación.

---

## 📋 *Cómo ejecutar la aplicación*
1. Clona este repositorio.
2. Importa el proyecto como un *proyecto Maven* en tu IDE.
3. Ejecuta el comando mvn spring-boot:run o ejecuta la clase principal.
4. Accede a la aplicación en http://localhost:8080.

---

¡Gracias por revisar este proyecto!
