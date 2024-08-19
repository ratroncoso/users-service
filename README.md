# Evaluación: JAVA
Desarrolle una aplicación que exponga una API RESTful de creación de usuarios.

Todos los endpoints deben aceptar y retornar solamente JSON, inclusive para los mensajes de error.

Todos los mensajes deben seguir el formato: ``` {"mensaje": "mensaje de error"} ```

## Registro
* Ese endpoint deberá recibir un usuario con los campos ```"nombre", "correo", "contraseña"```, más un listado de objetos ```"teléfono"```, respetando el siguiente formato:

    ```json
    {
        "name": "Juan Rodriguez",
        "email": "juan@rodriguez.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "1234567",
                "citycode": "1",
                "countrycode": "57"
            }
        ]
    } 
    ```
* Responder el código de status HTTP adecuado
* En caso de éxito, retorne el usuario y los siguientes campos:
    - id: id del usuario (puede ser lo que se genera por el banco de datos, pero sería más deseable un UUID)
    - created: fecha de creación del usuario
    - modified: fecha de la última actualización de usuario
    - last_login: del último ingreso (en caso de nuevo usuario, va a coincidir con la fecha de creación)
    - token: token de acceso de la API (puede ser UUID o JWT)
    - isactive: Indica si el usuario sigue habilitado dentro del sistema.
*  Si caso el correo conste en la base de datos, deberá retornar un error "El correo ya se encuentra registrado"
* El correo debe seguir una expresión regular para validar que formato sea el correcto. (aaaaaaa@dominio.cl)
* La clave debe seguir una expresión regular para validar que formato sea el correcto. (El
  valor de la expresión regular debe ser configurable)
* El token deberá ser persistido junto con el usuario

## Technologías
* Java 17
* Maven
* Spring Boot 3.2
* Spring Web
* Spring Data JPA / Hibernate
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt

## Como iniciar la solución
### Para comenzar con este proyecto, deberá tener instalado lo siguiente en su máquina local:

* JDK 17+
* Maven 3+

### Para compilar e iniciar el proyecto, siga estos pasos:

* Clonar el repositorio: `git clone https://github.com/ratroncoso/users-service.git`
* Construir los paquetes: `mvn clean install`
* Arrancar la aplicación : `mvn spring-boot:run`

## Diagrama de Solución

![Diagrama de Solución](diagrama.jpg)

# Pruebas

Para probar el sistema debe registrarse como usuario, luego autenticarse para obtener un token
Con este token JWT podrá acceder a los recursos como el listado de usuario

para registro ejecutar el siguiente comando

### Registro - JRodriguez
COMANDO CURL A EJECUTAR
```
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=A72FA900734AB6CA282011EF0E3E0D08' \
--data-raw '{
"email": "juanrodriguez@gmail.com",
"password": "Testing193"
}'
```

RESPONSE JSON ESPERADA
```json
{
  "id": "9676b8c0-6eaf-4429-9599-a4a1cd187c15",
  "name": "Juan Rodriguez",
  "email": "juanrodriguez@gmail.com",
  "password": "$2a$10$XPThqzFVPGQ1vs2b1mrBxOPHGE0gR6XHT/bLUD5wnp.5F/P8LtuOy",
  "created": "2024-08-19T05:37:10.540480855",
  "modified": null,
  "last_login": "2024-08-19T05:37:10.540497459",
  "active": true,
  "role": "USER",
  "phones": [
    {
      "id": "511ebb9e-e90a-41d9-beb8-e94189badfa8",
      "number": "1234567",
      "citycode": "1",
      "countrycode": "57"
    }
  ],
  "tokens": [
    {
      "id": null,
      "token": "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJqdWFucm9kcmlndWV6QGdtYWlsLmNvbSIsImlhdCI6MTcyNDA2MDIzMCwiZXhwIjoxNzI0MDYwMzE3fQ.sgGIPDP-QTmbObD1wK9J5vl1-utHxU0HkXDGN3FB3FHkxSCH1JS5OAAnCEf-nreKb3RJvUxHdejxpUpuMW8iRQ",
      "tokenType": "BEARER",
      "revoked": false,
      "expired": false
    }
  ],
  "authorities": [
    {
      "authority": "USER"
    }
  ],
  "username": "juanrodriguez@gmail.com",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "enabled": true
}
```

### Login - JRodriguez
COMANDO CURL A EJECUTAR
```
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=A72FA900734AB6CA282011EF0E3E0D08' \
--data-raw '{
    "email": "juanrodriguez@gmail.com",
    "password": "Testing193"
}'
```
RESPONSE JSON ESPERADA
```json
{
    "token": "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJqdWFucm9kcmlndWV6QGdtYWlsLmNvbSIsImlhdCI6MTcyNDA2MDI2MSwiZXhwIjoxNzI0MDYwMzQ3fQ.BR56WcPGTsq3mcTdJ0eWe49BRZoapha5leFzUiEoF1yKK52gKZqsqbyEBN-EXgaF-ph7d27gvWrxaLM2NbfMhw"
}
```

### Consulta Listado de Usuarios
El listado de usuario se obtiene haciendo una petición a un endpoint securitizado
Debemos completar el request con el Token JWT del Login

COMANDO CURL A EJECUTAR
```
curl --location 'http://localhost:8080/api/users' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer <<AQUI_REEMPLAZAR_TOKEN>>
```
