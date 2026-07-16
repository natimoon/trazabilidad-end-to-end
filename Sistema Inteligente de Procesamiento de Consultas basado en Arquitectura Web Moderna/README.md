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
**Ecomart** es un entorno de experimentación de alta fidelidad diseñado para analizar el comportamiento, rendimiento y seguridad de los datos en entornos distribuidos. El proyecto abarca desde el despliegue e infraestructura cloud (Etapa 1) hasta la simulación local de arquitecturas multinodo de alta disponibilidad con balanceo de carga (Etapa 2), e incluye un **Sistema de Fidelización de Clientes** completo (Entrega Final).

---

## 🏗️ Arquitectura General del Sistema

| Capa / Componente | Tecnología | Detalle Técnico |
| :--- | :--- | :--- |
| **Backend** | ![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk) ![Spring](https://img.shields.io/badge/Spring_Boot-3.0-green?logo=springboot) | Virtual Threads, REST APIs, JPA/Hibernate |
| **Balanceador (Etapa 2)** | ![NGINX](https://img.shields.io/badge/NGINX-Reverse__Proxy-009639?logo=nginx) | Round Robin en puerto `8080` |
| **Persistencia** | ![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql) | Motor relacional centralizado |
| **Cloud/Edge (Etapa 1)** | ![Railway](https://img.shields.io/badge/Cloud-Railway-7E33FF?logo=railway) ![Fastly](https://img.shields.io/badge/CDN-Fastly-red?logo=fastly) | Terminación TLS en nodo GRU (Brasil) |
| **Seguridad (Etapa 1)** | ![TLS](https://img.shields.io/badge/Security-TLS_1.3-blue) | Let's Encrypt, cifrado forzado |
| **Pruebas (Etapa 2)** | ![JMeter](https://img.shields.io/badge/Apache-JMeter-D22128?logo=apachejmeter) | Stress test, failover, latencias |
| **Fidelización** | ![Spring](https://img.shields.io/badge/Fidelizacion-Puntos-10b981?logo=springboot) | Bolsas de puntos, canje, niveles, segmentación |

---

## 📊 Módulos del Sistema

### 🚀 Etapa 1 — Cloud & Red externa
* Seguridad TLS 1.3, handshake, certificados Let's Encrypt
* Estudio de enrutamiento BGP desde Paraguay hasta nodos edge
* Pipeline CI/CD automatizado a Railway

### 🛠️ Etapa 2 — Infraestructura Local & Balanceo
* Alta disponibilidad con NGINX + Round Robin (puertos 8081/8082)
* Stress test con JMeter (1000 usuarios concurrentes)
* Failover pasivo ante caída de nodos

### 🏁 Entrega Final — Sistema de Fidelización de Clientes
* **CRUD Clientes** con niveles (Bronce/Plata/Oro/Platino) y referidos
* **Bolsa de Puntos** con asignación por compras y vencimiento programado
* **Uso de Puntos FIFO** con consumo de bolsas más antiguas primero
* **Canje de Productos** desde catálogo sincronizado con IA
* **Segmentación** por nivel, puntos, ciudad, nacionalidad y categoría IA
* **Dashboard analítico** con KPIs en tiempo real
* **Encuestas de Satisfacción** (1-5)
* **Documentación interactiva** Swagger UI

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
| POST | `/api/productos` | Crear producto (auto-categorización IA vía DeepSeek) |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |
| GET | `/generador` | Generar 5 productos ecológicos con IA |
| GET | `/categorizador?producto=` | Clasificar producto |
| GET | `/imagen?prompt=` | Generar imagen con DALL-E |
| GET | `/instancia` | Info de instancia activa |
| GET | `/api/health` | Health check |

### Sistema de Fidelización

#### Clientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/clientes` | Listar todos |
| GET | `/api/clientes/{id}` | Por ID |
| POST | `/api/clientes` | Crear |
| PUT | `/api/clientes/{id}` | Actualizar |
| DELETE | `/api/clientes/{id}` | Eliminar |
| POST | `/api/clientes/importar` | Importación masiva JSON |
| GET | `/api/clientes/segmentar/nivel?nivel=` | Por nivel |
| GET | `/api/clientes/segmentar/puntos?min=&max=` | Por rango de puntos |
| GET | `/api/clientes/segmentar/nacionalidad?nacionalidad=` | Por nacionalidad |
| GET | `/api/clientes/segmentar/ciudad?ciudad=` | Por ciudad |
| GET | `/api/clientes/segmentar/categoria?categoria=` | Por categoría IA de compras |
| GET | `/api/clientes/buscar/nombre?q=` | Por nombre |
| GET | `/api/clientes/buscar/apellido?q=` | Por apellido |
| GET | `/api/clientes/buscar/cumpleanos?mes=&dia=` | Por cumpleaños |
| GET | `/api/clientes/{id}/referidos` | Referidos de un cliente |

#### Puntos y Bolsas
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/puntos/cargar` | Cargar puntos por monto |
| POST | `/api/puntos/usar` | Usar puntos (FIFO) |
| GET | `/api/puntos/equivalencia?monto=` | Calcular equivalencia |
| GET | `/api/puntos/bolsas/cliente/{id}` | Bolsas de un cliente |
| GET | `/api/puntos/bolsas/rango?min=&max=` | Bolsas por rango de saldo |
| GET | `/api/puntos/bolsas/a-vencer?dias=` | Bolsas próximas a vencer |
| GET | `/api/puntos/usos/cliente/{id}` | Historial de uso |
| GET | `/api/puntos/usos/concepto/{id}` | Usos por concepto |
| GET | `/api/puntos/usos/fecha?inicio=&fin=` | Usos por rango de fecha |

#### Compras (Integración con IA)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/compras` | Registrar compra + asignar puntos |
| GET | `/api/compras` | Listar todas |
| GET | `/api/compras/cliente/{id}` | Compras de un cliente |

#### Canje
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos-canje` | Catálogo de canje |
| POST | `/api/productos-canje` | Agregar producto al catálogo |
| PUT | `/api/productos-canje/{id}` | Actualizar producto |
| DELETE | `/api/productos-canje/{id}` | Eliminar producto |
| POST | `/api/productos-canje/sincronizar` | Sync desde catálogo original |
| POST | `/api/canje` | Realizar canje |
| GET | `/api/canje` | Historial de canjes |
| GET | `/api/canje/cliente/{id}` | Canjes por cliente |

#### Configuración
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET/POST | `/api/conceptos-uso` | Conceptos de uso |
| GET/POST | `/api/reglas-puntos` | Reglas de puntos |
| GET/POST | `/api/vencimientos` | Parámetros de vencimiento |

#### Dashboard
| Método | Endpoint |
|--------|----------|
| GET | `/api/dashboard` |

#### Encuestas
| Método | Endpoint |
|--------|----------|
| GET | `/api/encuestas` |
| POST | `/api/encuestas` |
| GET | `/api/encuestas/cliente/{id}` |

---

## 💡 Flujo Completo (demostración)

```
1. Crear producto en AI & Balanceo (se auto-categoriza + se agrega al catálogo de canje)
2. Importar o crear clientes
3. Configurar ReglaPuntos (ej: cada 1000 Gs = 1 punto)
4. Configurar Vencimiento (ej: 365 días)
5. Sincronizar catálogo de canje (opcional)
6. Comprar: cliente compra producto → puntos se asignan automáticamente
7. Segmentar: filtrar clientes por nivel, ciudad, o categoría IA
8. Canjear: cliente canjea puntos por productos del catálogo
9. Dashboard: visualizar KPIs en tiempo real
```

---

## 📁 Estructura del Proyecto

```
├── src/main/java/.../
│   ├── EcomartApplication.java
│   ├── Controller/          (14 controladores REST)
│   │   ├── StatusController.java
│   │   ├── ProductoController.java
│   │   ├── GeneradorDeProductosController.java
│   │   ├── CategorizadorDeProductosController.java
│   │   ├── GeneradorDeImagenesController.java
│   │   ├── ClienteController.java
│   │   ├── CompraController.java
│   │   ├── PuntosController.java
│   │   ├── CanjeController.java
│   │   ├── ProductoCatalogoController.java
│   │   ├── EncuestaController.java
│   │   ├── ConceptoUsoController.java
│   │   ├── ReglaPuntosController.java
│   │   ├── ParametroVencimientoController.java
│   │   └── DashboardController.java
│   ├── service/             (13 servicios)
│   │   ├── StatusService.java
│   │   ├── ProductoService.java
│   │   ├── GeneradorService.java
│   │   ├── CategorizadorService.java
│   │   ├── ImagenService.java
│   │   ├── ClienteService.java
│   │   ├── CompraService.java
│   │   ├── PuntosService.java
│   │   ├── CanjeService.java
│   │   ├── ProductoCatalogoService.java
│   │   ├── EncuestaService.java
│   │   ├── ReglaPuntosService.java
│   │   ├── ConceptoUsoService.java
│   │   ├── ParametroVencimientoService.java
│   │   └── DashboardService.java
│   ├── repository/          (12 repositorios JPA)
│   └── model/               (11 entidades)
├── src/main/resources/static/
│   ├── index.html           (Frontend AI & Balanceo)
│   └── fidelizacion.html    (Frontend Fidelización - 7 pestañas)
├── docker-compose.yml
├── Dockerfile
├── nginx.conf
└── clientes-ejemplo.json
```

---

## 🧪 Cómo Probar con curl

```bash
# Health check
curl https://trazabilidad-end-to-end-production.up.railway.app/api/health

# Crear producto (auto-categorización IA)
curl -X POST https://trazabilidad-end-to-end-production.up.railway.app/api/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Zapatillas running","descripcion":"Deportivas","precio":80000}'

# Crear cliente
curl -X POST https://trazabilidad-end-to-end-production.up.railway.app/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Perez","nroDocumento":"123","ciudad":"Asunción"}'

# Configurar regla de puntos
curl -X POST https://trazabilidad-end-to-end-production.up.railway.app/api/reglas-puntos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"General","montoEquivalencia":1000}'

# Realizar compra (genera puntos automáticamente)
curl -X POST https://trazabilidad-end-to-end-production.up.railway.app/api/compras \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"productoId":1}'

# Dashboard
curl https://trazabilidad-end-to-end-production.up.railway.app/api/dashboard

# Segmentar por categoría IA
curl "https://trazabilidad-end-to-end-production.up.railway.app/api/clientes/segmentar/categoria?categoria=Deportes"
```

---

## 🧪 Pruebas de Stress (JMeter)

Plan de pruebas para 1000 usuarios concurrentes probando:
- `GET /generador` (40%)
- `GET /categorizador?producto=XXX` (30%)
- `GET /imagen?prompt=XXX` (30%)

```bash
jmeter -n -t load-test-plan.jmx -l resultados.csv
```

---

## 📚 KPIs del Dashboard

| KPI | Descripción |
|-----|-------------|
| Total Clientes | Cantidad de clientes registrados |
| Puntos Activos | Suma de saldos de bolsas vigentes |
| Puntos Vencidos | Suma de saldos de bolsas vencidas |
| Canjes Realizados | Cantidad total de canjes |
| Distribución | Bronce / Plata / Oro / Platino |
| Puntuación Encuestas | Promedio de encuestas (1-5) |
| Tasa de Retención | % de clientes con puntos > 0 |
| Próximos a vencer | Clientes con bolsas a vencer en 30 días |
