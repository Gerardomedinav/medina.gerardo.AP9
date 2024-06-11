# Aplicación Web de Home Banking

![Imagen de una aplicación de home banking](src/main/resources/static/web/img/home_banking_img.jpg)

El desarrollo de una aplicación web de home banking es un proyecto complejo y multifacético que abarca desde la identificación de problemas y objetivos hasta la implementación de soluciones técnicas y la recopilación de datos. A lo largo de este proyecto, se han abordado aspectos cruciales como la planificación de tareas con diagramas de Gantt, la clasificación de requerimientos funcionales y no funcionales, y la elección del ciclo de vida del software, incluyendo un enfoque orientado a objetos.
El análisis de proyectos similares y el diseño del muestreo han sido fundamentales para garantizar que la aplicación cumpla con las necesidades y expectativas de los usuarios. Además, la implementación de funcionalidades detalladas, como el sistema de login, la gestión de cuentas, y los roles y validaciones, asegura una experiencia de usuario segura y eficiente.
La arquitectura del sistema, junto con los diagramas de entidad-relación y de clases, proporciona una base sólida para la estructura y el funcionamiento del sistema. Los informes de progreso y los métodos de recopilación y análisis de datos han permitido un seguimiento constante del avance del proyecto y la identificación de áreas de mejora.
Finalmente, la creación de un manual de usuario y la implementación de endpoints CRUD garantizan que los usuarios finales puedan interactuar con la aplicación de manera efectiva y que el sistema pueda ser mantenido y actualizado en el futuro. En conjunto, este proyecto no solo demuestra la viabilidad técnica de una aplicación de home banking, sino también su potencial para mejorar la experiencia bancaria de los usuarios a través de una plataforma accesible y funcional.

## Cómo descargar el archivo en IntelliJ IDEA, Gradle y Spring Initialzr

1. **IntelliJ IDEA**:
   - Abrir IntelliJ IDEA
   - Ir a "File" > "Open" y seleccionar el archivo del proyecto

2. **Gradle**:
   - Abrir el proyecto en IntelliJ IDEA
   - Ir a "View" > "Tool Windows" > "Gradle"
   - En el panel de Gradle, hacer clic en el botón "Refresh Gradle Project" para descargar las dependencias

3. **Spring Initialzr**:
   - Ir a la página web de Spring Initialzr (https://start.spring.io/)
   - Seleccionar las dependencias necesarias para el proyecto, por ejemplo:
     - Spring Web
     - Spring Data JPA
     - MySQL Driver
     - Spring Security
   - Generar el proyecto y descargarlo

## Dependencias necesarias

Las dependencias necesarias para este proyecto incluyen:

- `spring-boot-starter-web`: Proporciona las funcionalidades básicas de Spring Web.
- `spring-boot-starter-data-jpa`: Permite la integración con bases de datos a través de JPA.
- `mysql-connector-java`: Driver de MySQL para la conexión a la base de datos.
- `spring-boot-starter-security`: Implementa la seguridad de la aplicación, incluyendo el sistema de login.
- `spring-boot-starter-test`: Proporciona herramientas de pruebas unitarias y de integración.

Estas dependencias se pueden configurar en el archivo `build.gradle` o `pom.xml` del proyecto, dependiendo de si se está utilizando Gradle o Maven.

