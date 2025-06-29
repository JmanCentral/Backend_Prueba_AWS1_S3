# 🌟 WishApp - Lista de Deseos con Java, Spring Boot y Amazon S3

¡Bienvenido a **WishApp**! Esta es una aplicación desarrollada como parte de mi proceso de aprendizaje backend. Se trata de una plataforma tipo **wish list**, donde los usuarios pueden guardar, gestionar y visualizar sus deseos, utilizando el framework **Spring Boot** y el servicio de almacenamiento en la nube **Amazon S3**.

---

## 🚀 Características Principales

- ✅ Registro y autenticación de usuarios
- 📝 CRUD de deseos (crear, leer, actualizar, eliminar)
- 📂 Subida de imágenes de los deseos a Amazon S3
- 🌐 API RESTful desarrollada con Java y Spring Boot
- 🛡️ Buenas prácticas de seguridad (cifrado de contraseñas, validaciones)
- 📦 Integración con Amazon S3 para almacenamiento de archivos

---

## 🧠 Tecnologías Utilizadas

| Tecnología      | Rol                               |
|-----------------|------------------------------------|
| Java 21         | Lenguaje principal                 |
| Spring Boot     | Framework backend                  |
| Spring Data JPA | Persistencia de datos              |
| Spring Security | Seguridad y autenticación          |
| Amazon S3       | Almacenamiento de objetos          |
| Oracle          | Base de datos relacional           |
| Maven           | Gestión de dependencias            |
| Postman         | Pruebas de la API                  |

---
## ⚙️ Configuración de Amazon S3
1. Crea un bucket en AWS S3.
2. Configura tus credenciales en el archivo `application.properties` o `application.yml`:

```properties
aws.accessKey=TU_ACCESS_KEY
aws.secretKey=TU_SECRET_KEY
aws.region=us-east-1
aws.s3.bucketName=nombre-de-tu-bucket

