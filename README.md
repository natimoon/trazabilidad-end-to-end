🌿 Sistema IA Ecomart - Trazabilidad End-to-End
Este repositorio contiene el código fuente del sistema desarrollado para el Trabajo Práctico Integrador de la asignatura Arquitectura Web (2026) en la Facultad Politécnica - Universidad Nacional de Asunción.

El objetivo de este proyecto es servir como instancia práctica para el análisis técnico y experimental del flujo completo de una solicitud web, desde la perspectiva del cliente hasta el procesamiento en el servidor y la persistencia de datos.

🛠️ Especificaciones Técnicas
Para garantizar una observación empírica de todas las capas de la arquitectura, el sistema se ha desarrollado e integrado con las siguientes tecnologías:

Lenguaje y Framework: Java 21 con Spring Boot 3.

Gestión de Dependencias: Maven (identificado en el archivo pom.xml).

Seguridad: Implementación de headers para soporte nativo de HTTPS y negociación TLS 1.3.

Persistencia: Integración con PostgreSQL.

Documentación de API: Swagger UI (OpenAPI 3.0) para la experimentación de solicitudes HTTP.

Infraestructura de Despliegue: Cloud Computing (Railway) con soporte para CDN (Fastly) y terminación TLS en el Edge.

📊 Propósito Experimental
Este sistema permite la validación de los siguientes puntos analizados en el informe técnico:

Capa de Aplicación: Procesamiento de peticiones REST mediante controladores de Spring.

Seguridad de Red: Verificación de certificados digitales y handshakes TLS mediante herramientas de diagnóstico.

Infraestructura de Red: Análisis de enrutamiento y latencia desde el ISP local hacia el nodo regional de la nube.

DevOps: Implementación de un pipeline de despliegue continuo desde este repositorio de GitHub hacia el entorno de producción.

🚀 Acceso al Sistema
La instancia de producción del sistema analizado en este trabajo se encuentra disponible en:
https://trazabilidad-end-to-end-production.up.railway.app/
