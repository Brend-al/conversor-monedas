
# 🪙 Conversor de Moneda - Challenge ONE - Backend Java

Este proyecto implementa un conversor de moneda interactivo en la consola, desarrollado como parte del programa **Challenge ONE - Back-end Java** de **Alura Latam** y **Oracle Next Education (ONE)**. Utiliza una API externa para obtener tasas de cambio en tiempo real.

## 🎯 Objetivo del Proyecto

El objetivo principal es construir una aplicación de consola en Java que consuma una **API REST**, demuestre el manejo de la comunicación HTTP (`HttpClient`) y la deserialización de datos JSON (`Gson`), y presente una interfaz de usuario interactiva y funcional.

-----

## 💻 Herramientas y Tecnologías

| Herramienta | Versión | Propósito |
| :--- | :--- | :--- |
| **Java Development Kit (JDK)** | 21+ | Entorno de desarrollo y ejecución del código. |
| **API Externa** | Exchange Rate API | Fuente de datos para las tasas de cambio en tiempo real. |
| **`java.net.http.HttpClient`** | Java Estándar | Realizar solicitudes HTTP de manera asíncrona y eficiente. |
| **Biblioteca Gson** | 2.13.2+ | Deserialización (mapeo) de JSON a objetos Java (`TasaDeCambio`). |
| **`java.util.Scanner`** | Java Estándar | Capturar la entrada de datos del usuario desde la consola. |

-----

## ⚙️ Configuración y Ejecución

Sigue estos pasos para configurar y ejecutar el conversor en tu entorno de desarrollo.

### 1\. Obtener la Clave de API

Para que la aplicación funcione, es indispensable obtener una clave de API gratuita:

1.  Regístrate en **[Exchange Rate API](https://www.exchangerate-api.com/)**.
2.  Obtén tu clave de API personal (se encuentra en tu panel).

### 2\. Configurar el Proyecto

1.  **Descarga el JAR de Gson:** Asegúrate de que el archivo `gson-2.13.2.jar` (o superior) esté en la carpeta `/lib` de tu proyecto y que haya sido añadido correctamente al **Classpath** en la configuración de tu IDE.

2.  **Configurar la Clave:** Localiza la clase `ConversorApp.java` y reemplaza el *placeholder* **`TU_CLAVE_AQUI`** con tu clave real obtenida en el paso anterior:

    ```java
    // Archivo: src/conversor/ConversorApp.java
    private static final String API_KEY = "TU_CLAVE_AQUI"; 
    ```

### 3\. Ejecutar la Aplicación

1.  Abre el proyecto en tu IDE (IntelliJ, Eclipse, etc.).
2.  Compila y ejecuta la clase principal: **`src/main.java`**.
3.  La aplicación iniciará y mostrará el menú en la consola.

-----

## 🚀 Flujo y Funcionalidades

La aplicación utiliza la clase central `ConversorApp` para manejar la lógica de la API, el control del menú y la interacción con el usuario.

### Menú Principal

El usuario interactúa a través de un menú de consola que se repite hasta que se selecciona la opción de "Salir". Las opciones disponibles son:

| Opción | Conversión |
| :--- | :--- |
| **1-6** | Pares predefinidos (USD/ARS, ARS/USD, USD/BRL, BRL/USD, USD/COP, CLP/USD). |
| **7** | Conversión Personalizada (permite ingresar cualquier código de moneda soportado por la API). |
| **8** | Muestra el historial completo de conversiones realizadas. |
| **9** | Finaliza el programa. |

### Características Clave

* **Deserialización:** La respuesta JSON de la API se mapea automáticamente al objeto **`TasaDeCambio`** usando Gson, facilitando el acceso a la `conversion_rate`.
* **Manejo de Errores:** Se implementan bloques `try-catch` para capturar errores de red (`IOException`) y errores de entrada de usuario (`NumberFormatException`).
* **Historial de Conversiones (Extra):** Cada conversión exitosa se registra en una lista de objetos **`RegistroConversion`**, incluyendo la marca de tiempo (`java.time.LocalDateTime`), y puede ser consultada en cualquier momento.

-----

## 👤 Autor

**[Brenda]**
