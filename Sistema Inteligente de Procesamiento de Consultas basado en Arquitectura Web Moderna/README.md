<div align="center">
  <h1>🌿 Sistema IA Ecomart: Trazabilidad e Infraestructura Distribuida</h1>
  <p><b>Laboratorio Experimental de Arquitecturas Web Modernas</b></p>
  <p><i>Proyecto Integrador Completo (Etapas 1 y 2)</i></p>
  <p><b>Facultad Politécnica (FP-UNA)</b></p>
  <hr>
  <p>👨‍🏫 <b>Profesor:</b> Rodrigo Benítez</p>
</div>

---

## 📖 Sobre el Proyecto General
**Ecomart** es un entorno de experimentación de alta fidelidad diseñado para analizar el comportamiento, rendimiento y seguridad de los datos en entornos distribuidos. El proyecto abarca desde el despliegue e infraestructura cloud (Etapa 1) hasta la simulación local de arquitecturas multinodo de alta disponibilidad con balanceo de carga (Etapa 2).

---

## 🏗️ Arquitectura General del Sistema

El sistema integra las siguientes tecnologías distribuidas en sus dos fases de análisis:

| Capa / Componente | Tecnología | Detalle Técnico |
| :--- | :--- | :--- |
| **Backend** | ![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk) ![Spring](https://img.shields.io/badge/Spring_Boot-3.0-green?logo=springboot) | Implementación de **Virtual Threads** para alta concurrencia y controladores REST eficientes. |
| **Balanceador (Etapa 2)** | ![NGINX](https://img.shields.io/badge/NGINX-Reverse__Proxy-009639?logo=nginx) | Configurado localmente como Proxy Inverso usando **Round Robin** en el puerto `8080`. |
| **Persistencia** | ![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql) | Motor relacional centralizado para optimización e integridad de datos. |
| **Cloud/Edge (Etapa 1)** | ![Railway](https://img.shields.io/badge/Cloud-Railway-7E33FF?logo=railway) ![Fastly](https://img.shields.io/badge/CDN-Fastly-red?logo=fastly) | Terminación TLS regional en nodo **GRU (Brasil)** para reducción de latencia en producción. |
| **Seguridad (Etapa 1)** | ![TLS](https://img.shields.io/badge/Security-TLS_1.3-blue) ![SSL](https://img.shields.io/badge/Cert-Let's_Encrypt-003366) | Negociación forzada de cifrado y headers de seguridad nativos. |
| **Pruebas (Etapa 2)** | ![JMeter](https://img.shields.io/badge/Apache-JMeter-D22128?logo=apachejmeter) | Entorno experimental para pruebas de estrés, latencias y tolerancia a fallos (*Failover*). |

---

## 📊 Dimensiones del Experimento (Evidencia de Laboratorio)

### 🚀 Módulos de la Etapa 1 (Cloud & Red externa)
* **Seguridad e Integridad:** Verificación de Certificados Digitales, ciclos de *Handshake* TLS y validación de identidad del servidor remoto.
* **Red e Infraestructura:** Estudio de enrutamiento (BGP), tránsito y saltos intermedios desde el **ISP local (Paraguay)** hacia nodos de borde internacionales mediante `traceroute`.
* **Ciclo DevOps:** Gestión de infraestructura virtualizada en contenedores y pipeline de **Despliegue Continuo (CI/CD)** automatizado a Railway.

### 🛠️ Módulos de la Etapa 2 (Infraestructura Local & Balanceo)
* **Alta Disponibilidad y Failover Pasivo:** Configuración de directivas de tiempo de espera (`proxy_connect_timeout` y `proxy_read_timeout`) en NGINX para mitigar la degradación del servicio ante caídas de nodos en pleno procesamiento de ráfagas.
* **Evaluación bajo Estrés:** Recopilación de métricas clave (Percentiles P50, P90, P99 y Tasa de Error %) a través de planes de pruebas concurrentes en JMeter para identificar cuellos de botella sistémicos.

---

## 🔗 Acceso y Documentación en Producción (Etapa 1)
* 🔗 **App en Producción:** [Acceder a Ecomart Live](https://trazabilidad-end-to-end-production.up.railway.app/)
* 📜 **Documentación API:** `/swagger-ui/index.html` (Basado en el estándar OpenAPI 3.0)

---

## 🛠️ Guía de Ejecución Local (Etapa 2)

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/natimoon/trazabilidad-end-to-end.git](https://github.com/natimoon/trazabilidad-end-to-end.git)