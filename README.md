LiterAluraAlexandria  

Este proyecto se creó como parte de mi proceso de aprendizaje. El objetivo principal era integrar la API Gutendex y almacenar los resultados en una base de datos PostgreSQL. El enfoque del proyecto se centra en funcionalidades específicas que permiten realizar operaciones básicas de gestión de libros.

✨ Características
El proyecto incluye las siguientes funcionalidades:

Buscar Libro: Permite buscar libros por título utilizando la API de Gutendex y guarda el primer resultado encontrado en la base de datos.

Listar Libros: Muestra por consola todos los libros guardados en la base de datos.

Autores por Año: Imprime por consola los autores que estaban vivos en un año específico, basado en la información almacenada en la base de datos.

Buscar Libros por Idioma: Filtra y muestra por consola los libros según el idioma especificado pero que esten guardados en la base de PosgresSQL.

Obtener estadicas: Nos permite obtener estadisticas  de descargas tanto de la API Gutendex como de la base local.

Requisitos
Para ejecutar este proyecto se requieren las siguientes tecnologías y herramientas:

Java JDK: versión 17 o superior, disponible en Download the Latest Java LTS.
Maven: versión 4 o superior, para la gestión de dependencias y construcción del proyecto.
Spring Boot: versión 3.3.0, configurado a través de Spring Initializr.
PostgreSQL: versión 14.12 o superior, como base de datos relacional. Configurado y conectado con SQL.
IDE: Se recomienda IntelliJ IDEA, disponible en JetBrains, para el desarrollo integrado del proyecto.
Dependencias para agregar al crear el proyecto en Spring Initializr:
Spring Data JPA: para la integración con la capa de persistencia.
PostgreSQL Driver: para la conexión con la base de datos PostgreSQL.
Jackson: para el manejo de JSON.

