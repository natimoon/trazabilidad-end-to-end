<div align="center">
  <h1>🌿 Sistema IA Ecomart: Trazabilidad e Infraestructura Distribuida</h1>
  <p><b>Laboratorio Experimental de Arquitecturas Web Modernas</b></p>
  <p><i>Proyecto Integrador Completo (Etapas 1, 2 y Entrega Final)</i></p>
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
| **API REST (Entrega Final)** | CRUD Completo | Endpoints GET, POST, PUT, DELETE para gestión de productos con auto-categorización por IA. |

---

## 📊 Dimensiones del Experimento (Evidencia de Laboratorio)

### 🚀 Módulos de la Etapa 1 (Cloud & Red externa)
* **Seguridad e Integridad:** Verificación de Certificados Digitales, ciclos de *Handshake* TLS y validación de identidad del servidor remoto.
* **Red e Infraestructura:** Estudio de enrutamiento (BGP), tránsito y saltos intermedios desde el **ISP local (Paraguay)** hacia nodos de borde internacionales mediante `traceroute`.
* **Ciclo DevOps:** Gestión de infraestructura virtualizada en contenedores y pipeline de **Despliegue Continuo (CI/CD)** automatizado a Railway.

### 🛠️ Módulos de la Etapa 2 (Infraestructura Local & Balanceo)
* **Alta Disponibilidad y Failover Pasivo:** Configuración de directivas de tiempo de espera (`proxy_connect_timeout` y `proxy_read_timeout`) en NGINX para mitigar la degradación del servicio ante caídas de nodos en pleno procesamiento de ráfagas.
* **Evaluación bajo Estrés:** Recopilación de métricas clave (Percentiles P50, P90, P99 y Tasa de Error %) a través de planes de pruebas concurrentes en JMeter para identificar cuellos de botella sistémicos.

### 🏁 Módulos de la Entrega Final (API REST & Capa de Servicios)
* **Capa de Servicios (`@Service`):** Separación de la lógica de negocio en servicios dedicados (`StatusService`, `GeneradorService`, `CategorizadorService`, `ImagenService`, `ProductoService`).
* **CRUD Completo:** Endpoints `GET`, `POST`, `PUT`, `DELETE` para la entidad `Producto` con persistencia en PostgreSQL.
* **Auto-categorización con IA:** Al crear un producto sin categoría, el sistema la determina automáticamente usando OpenAI GPT-4o.
* **Documentación interactiva:** Swagger UI disponible en `/swagger-ui/index.html`.

---

## 🔗 Acceso y Documentación en Producción (Etapa 1)
* 🔗 **App en Producción:** [Acceder a Ecomart Live](https://trazabilidad-end-to-end-production.up.railway.app/)
* 📜 **Documentación API:** `/swagger-ui/index.html` (Basado en el estándar OpenAPI 3.0)

---

## 🛠️ Guía de Ejecución Local (Etapa 2)

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/natimoon/trazabilidad-end-to-end.git
   cd trazabilidad-end-to-end
   ```

2. **Configurar la clave de OpenAI:**
   ```bash
   export OPENAI_API_KEY=sk-...
   ```

3. **Iniciar todos los servicios** (compila, crea las imágenes y levanta los contenedores):
   ```bash
   cd "Sistema Inteligente de Procesamiento de Consultas basado en Arquitectura Web Moderna"
   ./start.sh
   ```

4. **Probar el balanceador:**
   ```bash
   for i in {1..10}; do curl -s http://localhost:8080/instancia; echo; sleep 0.5; done
   ```
   Verás cómo alterna entre `"puerto":"8081"` y `"puerto":"8082"`.

5. **Ejecutar stress test con JMeter:**
   ```bash
   jmeter -n -t load-test-plan.jmx -l resultados.csv
   ```

6. **Probar failover** (tolerancia a fallos):
   ```bash
   ./failover-test.sh
   ```

7. **Verificar distribución Round Robin:**
   ```bash
   ./test-balanceador.sh
   ```

8. **Detener los servicios:**
   ```bash
   docker compose down
   ```

---

## 🧱 Arquitectura en Capas

El backend sigue una arquitectura en capas separando la responsabilidad de cada componente:

| Capa | Paquete | Responsabilidad |
|---|---|---|
| **Controller** | `Controller/` | Recibe peticiones HTTP, delega en servicios, devuelve respuestas |
| **Service** | `service/` | Contiene la lógica de negocio (llamadas a OpenAI, token counting, CRUD) |
| **Repository** | `repository/` | Acceso a la base de datos (JPA) |
| **Model** | `model/` | Entidades que representan las tablas de la BD |

```
HTTP Request → Controller → Service → Repository → PostgreSQL
                              ↓
                         OpenAI / DALL-E
```

## 📡 Endpoints de la API

| Método | Endpoint | Descripción |
|---|---|---|
| **GET** | `/api/productos` | Listar todos los productos |
| **GET** | `/api/productos/{id}` | Obtener un producto por ID |
| **POST** | `/api/productos` | Crear un producto (auto-categorización IA) |
| **PUT** | `/api/productos/{id}` | Actualizar un producto |
| **DELETE** | `/api/productos/{id}` | Eliminar un producto |
| GET | `/generador` | Generar 5 productos ecológicos con IA |
| GET | `/categorizador?producto=` | Clasificar un producto en categorías |
| GET | `/imagen?prompt=` | Generar una imagen con DALL-E |
| GET | `/instancia` | Información de la instancia activa |
| GET | `/api/health` | Health check del servidor |

---

## 🧪 Cómo Probar la API REST (Entrega Final)

Los endpoints están disponibles en Railway sin necesidad de instalar nada:

```bash
# Health check
curl https://trazabilidad-end-to-end-production.up.railway.app/api/health

# GET - Listar productos
curl https://trazabilidad-end-to-end-production.up.railway.app/api/productos

# POST - Crear producto (se auto-categoriza con IA)
curl -X POST https://trazabilidad-end-to-end-production.up.railway.app/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Zapatillas running","descripcion":"Zapatillas deportivas ligeras"}'

# GET - Obtener por ID
curl https://trazabilidad-end-to-end-production.up.railway.app/api/productos/1

# PUT - Actualizar
curl -X PUT https://trazabilidad-end-to-end-production.up.railway.app/api/productos/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Zapatillas running","descripcion":"Modelo 2026","categoria":"Deportes"}'

# DELETE - Eliminar
curl -X DELETE https://trazabilidad-end-to-end-production.up.railway.app/api/productos/1
```

También podés probar desde el navegador en la página principal y desde Swagger:
- 🔗 **Web:** https://trazabilidad-end-to-end-production.up.railway.app/
- 📜 **Swagger:** https://trazabilidad-end-to-end-production.up.railway.app/swagger-ui/index.html

---

## 🧪 Pruebas de Stress (JMeter)

Se incluye un plan de pruebas (`load-test-plan.jmx`) que simula **1000 usuarios concurrentes** con ramp-up de 30s, probando 3 endpoints:
- `GET /generador` (40% del tráfico)
- `GET /categorizador?producto=XXX` (30%)
- `GET /imagen?prompt=XXX` (30%)

Los resultados incluyen percentiles P50, P90, P99 y tasa de error, exportados a CSV.

---

## 📁 Estructura del Proyecto

```
📦 Sistema Inteligente de Procesamiento de Consultas basado en Arquitectura Web Moderna
├── src/                          # Código fuente Spring Boot
│   └── main/java/.../
│       ├── EcomartApplication.java
│       ├── Controller/                     # Capa de presentación (HTTP)
│       │   ├── StatusController.java        # /instancia, /api/health
│       │   ├── ProductoController.java      # CRUD: GET, POST, PUT, DELETE
│       │   ├── GeneradorDeProductosController.java  # /generador
│       │   ├── CategorizadorDeProductosController.java # /categorizador
│       │   └── GeneradorDeImagenesController.java   # /imagen
│       ├── service/                        # Capa de servicios (lógica de negocio)
│       │   ├── StatusService.java
│       │   ├── ProductoService.java         # CRUD con auto-categorización
│       │   ├── GeneradorService.java
│       │   ├── CategorizadorService.java
│       │   └── ImagenService.java
│       ├── repository/                     # Capa de persistencia
│       │   └── ProductoRepository.java
│       └── model/                          # Entidades de base de datos
│           └── Producto.java
├── docker-compose.yml            # Orquestación (app-1, app-2, nginx, db)
├── Dockerfile                    # Build multi-etapa de Spring Boot
├── nginx.conf                    # Config local del balanceador
├── nginx-docker.conf             # Config Docker del balanceador
├── load-test-plan.jmx            # Stress test JMeter (1000 usuarios)
├── HTTP Request.jmx              # Test simple JMeter
├── start.sh                      # Script de inicio automatizado
├── test-balanceador.sh           # Verifica distribución Round Robin
└── failover-test.sh              # Demostración de tolerancia a fallos
```
