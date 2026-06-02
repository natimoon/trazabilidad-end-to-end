#!/bin/bash
set -e

echo "=========================================="
echo "  Ecomart - Entorno de Balanceo de Carga"
echo "  Arquitectura Web | Prof. Rodrigo Benitez"
echo "  Alumna: Natalia Godoy"
echo "=========================================="
echo ""

# Colores
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m'

# Verificar OPENAI_API_KEY
if [ -z "$OPENAI_API_KEY" ]; then
    echo -e "${YELLOW}⚠️  OPENAI_API_KEY no esta definida.${NC}"
    echo -e "   Usala asi: ${CYAN}OPENAI_API_KEY=sk-... ./start.sh${NC}"
    echo -e "   O: ${CYAN}export OPENAI_API_KEY=sk-... && ./start.sh${NC}"
    echo ""
fi

# Verificar Docker
if ! command -v docker &> /dev/null; then
    echo -e "${RED}❌ Docker no esta instalado.${NC}"
    echo "   Instalalo desde https://docs.docker.com/get-docker/"
    exit 1
fi

# Verificar Docker Compose
if ! docker compose version &> /dev/null; then
    echo -e "${YELLOW}⚠️  docker compose no encontrado, intentando docker-compose...${NC}"
    if ! command -v docker-compose &> /dev/null; then
        echo -e "${RED}❌ Docker Compose no esta instalado.${NC}"
        exit 1
    fi
    COMPOSE_CMD="docker-compose"
else
    COMPOSE_CMD="docker compose"
fi

echo -e "${CYAN}🔨 Construyendo imagenes...${NC}"
$COMPOSE_CMD build

echo ""
echo -e "${CYAN}🚀 Iniciando servicios...${NC}"
echo -e "   • ${GREEN}db${NC}         - PostgreSQL 16"
echo -e "   • ${GREEN}app-1${NC}      - Spring Boot (puerto 8081)"
echo -e "   • ${GREEN}app-2${NC}      - Spring Boot (puerto 8082)"
echo -e "   • ${GREEN}nginx${NC}      - Balanceador de Carga (puerto 8080)"
echo ""

$COMPOSE_CMD up -d

echo ""
echo -e "${CYAN}⏳ Esperando que los servicios esten listos...${NC}"

# Esperar a que nginx responda
for i in {1..30}; do
    if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/instancia 2>/dev/null | grep -q "200"; then
        echo -e "${GREEN}✅ Balanceador listo en http://localhost:8080${NC}"
        break
    fi
    sleep 2
    echo -n "."
done
echo ""

echo ""
echo -e "${GREEN}=========================================="
echo "  ✅ TODO LISTO"
echo "==========================================${NC}"
echo ""
echo -e "   📍 Balanceador:    ${CYAN}http://localhost:8080${NC}"
echo -e "   📍 Instancia 1:    ${CYAN}http://localhost:8081${NC}"
echo -e "   📍 Instancia 2:    ${CYAN}http://localhost:8082${NC}"
echo -e "   📍 Swagger UI:     ${CYAN}http://localhost:8080/swagger-ui/index.html${NC}"
echo -e "   📍 Estado NGINX:   ${CYAN}http://localhost:8080/nginx_status${NC}"
echo ""
echo -e "   🧪 Probar balanceo:"
echo -e "      ${YELLOW}for i in {1..10}; do curl -s http://localhost:8080/instancia; echo; sleep 0.5; done${NC}"
echo ""
echo -e "   📊 Ejecutar Stress Test:"
echo -e "      ${YELLOW}jmeter -n -t load-test-plan.jmx -l resultados.csv${NC}"
echo ""
echo -e "   🛑 Para detener: ${YELLOW}$COMPOSE_CMD down${NC}"
echo ""
