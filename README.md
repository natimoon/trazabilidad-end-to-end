<div align="center">
  <h1>🌿 Sistema IA Ecomart: Trazabilidad End-to-End</h1>
  <p><b>Laboratorio Experimental de Arquitecturas Web Modernas</b></p>
  <p><i>Proyecto Integrador - Arquitectura Web 2026</i></p>
  <p><b>Facultad Politécnica (FP-UNA)</b></p>
  <hr>
  <p>👨‍🏫 <b>Profesor:</b> Rodrigo Benítez</p>
</div>

---

## 📖 Sobre el Proyecto
**Ecomart** no es solo una plataforma de retail; es un **entorno de experimentación de alta fidelidad** diseñado para analizar el comportamiento de los datos en entornos distribuidos. El sistema permite la observación empírica del flujo de información desde un host cliente en Paraguay hasta la infraestructura cloud.

---

## 🏗️ Arquitectura y Especificaciones Técnicas
Para garantizar un análisis técnico profundo, el sistema integra las siguientes tecnologías:

| Capa | Tecnología | Detalle Técnico |
| :--- | :--- | :--- |
| **Backend** | ![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk) ![Spring](https://img.shields.io/badge/Spring_Boot-3.0-green?logo=springboot) | Implementación de **Virtual Threads** para alta concurrencia. |
| **Seguridad** | ![TLS](https://img.shields.io/badge/Security-TLS_1.3-blue) ![SSL](https://img.shields.io/badge/Cert-Let's_Encrypt-003366) | Negociación forzada de cifrado y headers de seguridad nativos. |
| **Persistencia** | ![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql) | Persistencia relacional con optimización de integridad de datos. |
| **Cloud/Edge** | ![Railway](https://img.shields.io/badge/Cloud-Railway-7E33FF?logo=railway) ![Fastly](https://img.shields.io/badge/CDN-Fastly-red?logo=fastly) | Terminación TLS regional en nodo **GRU (Brasil)** y reducción de latencia. |

---

## 📊 Dimensiones del Experimento (Evidencia para el TP)
Este repositorio constituye la base de evidencia empírica para los siguientes módulos del trabajo técnico:

### 1. Capa de Aplicación
Análisis de controladores REST, gestión de rutas y eficiencia en el procesamiento de peticiones mediante el stack de **Spring Web**.

### 2. Seguridad e Integridad
Verificación de **Certificados Digitales**, ciclos de *Handshake* TLS y validación de identidad del servidor mediante herramientas de diagnóstico como `curl`.

### 3. Red e Infraestructura
Estudio de enrutamiento (BGP), tránsito y saltos intermedios desde el **ISP local (Paraguay)** hacia nodos de borde internacionales mediante `traceroute`.

### 4. Ciclo DevOps
Gestión de infraestructura virtualizada (Contenedores) y pipeline de **Despliegue Continuo (CI/CD)** automatizado desde GitHub a Railway.

---

## 🚀 Acceso y Documentación
* 🔗 **App en Producción:** [Acceder a Ecomart Live](https://trazabilidad-end-to-end-production.up.railway.app/)
* 📜 **Documentación API:** `/swagger-ui/index.html` (Basado en el estándar OpenAPI 3.0)

---

## 🛠️ Guía de Ejecución Local
Para replicar el entorno experimental localmente:

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/natimoon/trazabilidad-end-to-end.git](https://github.com/natimoon/trazabilidad-end-to-end.git)
