<div align="center">
  <h1>🌿 Sistema IA Ecomart: Trazabilidad e Infraestructura Distribuida</h1>
  <p><b>Laboratorio Experimental de Arquitecturas Web Modernas</b></p>
  <p><i>Proyecto Integrador Completo (Etapas 1, 2 y Entrega Final)</i></p>
  <p><b>Facultad Politécnica (FP-UNA)</b></p>
  <hr>
  <p>👨‍🏫 <b>Profesor:</b> Rodrigo Benítez</p>
</div>

<p align="center">
  <a href="https://trazabilidad-end-to-end-production.up.railway.app/">🚀 App en Producción</a> •
  <a href="https://trazabilidad-end-to-end-production.up.railway.app/fidelizacion.html">⭐ Fidelización</a> •
  <a href="https://trazabilidad-end-to-end-production.up.railway.app/swagger-ui/index.html">📜 Swagger</a>
</p>

---

## 📋 Tabla de Contenidos

1. [Sobre el Proyecto](#-sobre-el-proyecto-general)
2. [Arquitectura](#-arquitectura-general-del-sistema)
3. [Módulos](#-módulos-del-sistema)
4. [Acceso en Producción](#-acceso-en-producción)
5. [Endpoints](#-endpoints-de-la-api)
6. [Flujo de Demostración](#-flujo-completo-demostración)
7. [Guía de Ejecución Local](#-guía-de-ejecución-local)
8. [Estructura del Proyecto](#-estructura-del-proyecto)
9. [Documentación Detallada](#-documentaci%C3%B3n-detallada)

> 📖 **Para la documentación completa del Sistema de Fidelización** (todos los endpoints, entidades y KPIs), ingresá a la carpeta [`Sistema Inteligente de Procesamiento de Consultas basado en Arquitectura Web Moderna/`](./Sistema%20Inteligente%20de%20Procesamiento%20de%20Consultas%20basado%20en%20Arquitectura%20Web%20Moderna/) y abrí su `README.md`.

---

## 📖 Sobre el Proyecto General

**Ecomart** es un entorno de experimentación de alta fidelidad diseñado para analizar el comportamiento, rendimiento y seguridad de los datos en entornos distribuidos. El proyecto abarca:

| Etapa | Enfoque |
|-------|---------|
| **Etapa 1** | Despliegue cloud, seguridad TLS, CDN, CI/CD |
| **Etapa 2** | Balanceo NGINX, alta disponibilidad, stress testing |
| **Entrega Final** | **Sistema de Fidelización de Clientes** con puntos, canje, niveles, segmentación y dashboard |

---

## 🏗️ Arquitectura General del Sistema

| Capa / Componente | Tecnología |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot 3.0, Virtual Threads |
| **Balanceador (Etapa 2)** | NGINX Reverse Proxy (Round Robin, puerto 8080) |
| **Persistencia** | PostgreSQL |
| **Cloud/Edge (Etapa 1)** | Railway + Fastly CDN (nodo GRU - Brasil) |
| **Seguridad (Etapa 1)** | TLS 1.3, Let's Encrypt |
| **Pruebas (Etapa 2)** | Apache JMeter (1000 usuarios concurrentes) |
| **Fidelización (Entrega Final)** | 11 entidades, 15 servicios, 14 controladores REST |

---

## 📊 Módulos del Sistema

### 🚀 Etapa 1 — Cloud & Red externa
* Verificación de Certificados Digitales, handshake TLS 1.3
* Estudio de enrutamiento BGP desde Paraguay a nodos edge
* Pipeline CI/CD automatizado a Railway

### 🛠️ Etapa 2 — Infraestructura Local & Balanceo
* Alta disponibilidad con NGINX + Round Robin (app-1:8081, app-2:8082)
* Failover pasivo con timeouts configurables
* Stress test con JMeter, percentiles P50/P90/P99

### 🏁 Entrega Final — Sistema de Fidelización de Clientes

| Módulo | Descripción |
|--------|-------------|
| **CRUD Clientes** | ABMC con niveles (Bronce/Plata/Oro/Platino), ciudad, referidos |
| **Bolsa de Puntos** | Asignación automática por compras, vencimiento programado |
| **Uso FIFO** | Consume bolsas más antiguas primero |
| **Canje** | Catálogo de productos sincronizado con IA, canje de puntos |
| **Compras** | Integración con AI & Balanceo, dispara asignación de puntos |
| **Segmentación** | Por nivel, puntos, ciudad, nacionalidad, categoría IA |
| **Dashboard** | KPIs en tiempo real (puntos activos, canjes, distribución) |
| **Encuestas** | Satisfacción 1-5 con comentarios |
| **Configuración** | Reglas de puntos, vencimientos, conceptos de uso |

---

## 🔗 Acceso en Producción

| Recurso | URL |
|---------|-----|
| Página principal | [https://trazabilidad-end-to-end-production.up.railway.app/](https://trazabilidad-end-to-end-production.up.railway.app/) |
| Sistema de Fidelización | [https://trazabilidad-end-to-end-production.up.railway.app/fidelizacion.html](https://trazabilidad-end-to-end-production.up.railway.app/fidelizacion.html) |
| Swagger UI | [https://trazabilidad-end-to-end-production.up.railway.app/swagger-ui/index.html](https://trazabilidad-end-to-end-production.up.railway.app/swagger-ui/index.html) |
| Dashboard API | [https://trazabilidad-end-to-end-production.up.railway.app/api/dashboard](https://trazabilidad-end-to-end-production.up.railway.app/api/dashboard) |

---

## 📡 Endpoints de la API

### AI & Balanceo (Etapas 1-2)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar productos |
| GET | `/api/productos/{id}` | Producto por ID |
| POST | `/api/productos` | Crear (auto-categorización IA) |
| PUT | `/api/productos/{id}` | Actualizar |
| DELETE | `/api/productos/{id}` | Eliminar |
| GET | `/generador` | Generar 5 productos ecológicos |
| GET | `/categorizador?producto=` | Clasificar producto |
| GET | `/imagen?prompt=` | Generar imagen DALL-E |
| GET | `/instancia` | Info de instancia activa |
| GET | `/api/health` | Health check |

### Sistema de Fidelización

#### Clientes
| Método | Endpoint |
|--------|----------|
| GET/POST/DELETE | `/api/clientes` |
| POST | `/api/clientes/importar` |
| GET | `/api/clientes/segmentar/nivel?nivel=` |
| GET | `/api/clientes/segmentar/puntos?min=&max=` |
| GET | `/api/clientes/segmentar/ciudad?ciudad=` |
| GET | `/api/clientes/segmentar/categoria?categoria=` |
| GET | `/api/clientes/{id}/referidos` |

#### Puntos / Bolsas / Compras / Canje
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/puntos/cargar` | Cargar puntos |
| POST | `/api/puntos/usar` | Usar puntos (FIFO) |
| POST | `/api/compras` | Comprar + ganar puntos |
| POST | `/api/canje` | Canjear puntos por producto |
| POST | `/api/productos-canje/sincronizar` | Sync catálogo desde IA |
| GET | `/api/dashboard` | KPIs del sistema |

> 📖 **Ver la documentación completa de todos los endpoints** (incluyendo GET por ID, búsquedas, segmentación, configuraciones y encuestas) en el [`README.md`](./Sistema%20Inteligente%20de%20Procesamiento%20de%20Consultas%20basado%20en%20Arquitectura%20Web%20Moderna/README.md) dentro de la carpeta del módulo.

---

## 💡 Flujo Completo (Demostración)

```
1. Crear producto en AI & Balanceo → se auto-categoriza + agrega al catálogo de canje
2. Crear o importar clientes
3. Configurar ReglaPuntos (ej: cada 1000 Gs = 1 punto)
4. Configurar Vencimiento (ej: 365 días)
5. Sincronizar catálogo de canje
6. Comprar: cliente compra producto → puntos se asignan automáticamente
7. Segmentar: filtrar clientes por nivel, ciudad o categoría IA
8. Canjear: cliente canjea puntos por productos
9. Dashboard: visualizar KPIs
```

---

## 🛠️ Guía de Ejecución Local

```bash
# 1. Clonar
git clone https://github.com/natimoon/trazabilidad-end-to-end.git
cd trazabilidad-end-to-end

# 2. Configurar clave OpenAI
export OPENAI_API_KEY=sk-...

# 3. Iniciar servicios (compila, crea imágenes, levanta contenedores)
cd "Sistema Inteligente de Procesamiento de Consultas basado en Arquitectura Web Moderna"
./start.sh

# 4. Probar balanceador
for i in {1..10}; do curl -s http://localhost:8080/instancia; echo; sleep 0.5; done

# 5. Stress test
jmeter -n -t load-test-plan.jmx -l resultados.csv

# 6. Detener
docker compose down
```

---

## 📁 Estructura del Proyecto

```
📦 trazabilidad-end-to-end (raíz del repo)
├── Sistema Inteligente de Procesamiento de Consultas basado en Arquitectura Web Moderna/
│   ├── src/main/java/.../
│   │   ├── Controller/          (14 controladores REST)
│   │   ├── service/             (15 servicios)
│   │   ├── repository/          (12 repositorios JPA)
│   │   └── model/               (11 entidades)
│   ├── src/main/resources/static/
│   │   ├── index.html           (Frontend AI & Balanceo)
│   │   └── fidelizacion.html    (Frontend Fidelización - 7 pestañas)
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── nginx.conf
│   ├── load-test-plan.jmx
│   └── start.sh
└── README.md                    (este archivo)
```

---

## 🧪 Probar con curl

```bash
# Health check
curl https://trazabilidad-end-to-end-production.up.railway.app/api/health

# Crear producto
curl -X POST https://trazabilidad-end-to-end-production.up.railway.app/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Zapatillas","precio":80000}'

# Dashboard fidelización
curl https://trazabilidad-end-to-end-production.up.railway.app/api/dashboard

# Segmentar por categoría IA
curl "https://trazabilidad-end-to-end-production.up.railway.app/api/clientes/segmentar/categoria?categoria=Deportes"
```

---

## 📄 Documentación Detallada

Para la documentación exhaustiva del **Sistema de Fidelización** con:

- Lista completa de los **40+ archivos** del módulo
- Todos los **endpoints** con métodos, rutas y bodies
- Descripción de cada **entidad, servicio y controlador**
- **KPIs** del dashboard
- **Puntaje** de tópicos adicionales

👉 Ingresá a [`Sistema Inteligente de Procesamiento de Consultas basado en Arquitectura Web Moderna/`](./Sistema%20Inteligente%20de%20Procesamiento%20de%20Consultas%20basado%20en%20Arquitectura%20Web%20Moderna/) y abrí su `README.md`.
