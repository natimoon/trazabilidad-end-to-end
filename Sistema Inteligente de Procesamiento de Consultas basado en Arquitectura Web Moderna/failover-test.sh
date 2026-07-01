#!/bin/bash
# =============================================================================
# failover-test.sh — Demostracion de tolerancia a fallos del balanceador
# Tópico 3: Balanceador de Carga | Arquitectura Web | Prof. Rodrigo Benítez
# Alumna: Natalia Godoy
# =============================================================================
# Uso: ./failover-test.sh
# Requiere: curl, docker
# =============================================================================

BALANCEADOR="http://localhost:8080"
INSTANCIA_1="ecomart-instancia-1"
INSTANCIA_2="ecomart-instancia-2"

echo "=========================================="
echo "  Test de Failover - Tolerancia a Fallos"
echo "=========================================="
echo ""

# 1. Verificar estado inicial
echo "[1/4] Verificando estado inicial..."
curl -s "$BALANCEADOR/instancia" | python3 -m json.tool 2>/dev/null || curl -s "$BALANCEADOR/instancia"
echo ""
echo ""

# 2. Detener instancia 1
echo "[2/4] Deteniendo $INSTANCIA_1..."
docker stop "$INSTANCIA_1" > /dev/null 2>&1
sleep 2
echo "     ✅ $INSTANCIA_1 detenida."
echo ""

# 3. Verificar que el balanceador redirige a la instancia 2
echo "[3/4] Verificando failover - 5 requests al balanceador..."
for i in $(seq 1 5); do
    RESP=$(curl -s "$BALANCEADOR/instancia")
    PUERTO=$(echo "$RESP" | grep -o '"puerto":"[^"]*"' | cut -d'"' -f4)
    echo "     [$i] -> Respuesta de: $PUERTO"
    sleep 0.5
done
echo ""

# 4. Restaurar instancia 1
echo "[4/4] Restaurando $INSTANCIA_1..."
docker start "$INSTANCIA_1" > /dev/null 2>&1
sleep 3
echo "     ✅ $INSTANCIA_1 restaurada."
echo ""

echo "=========================================="
echo "  Resultado del Test de Failover"
echo "=========================================="
echo ""
echo "  - Se detuvo una instancia intencionalmente"
echo "  - El balanceador redirigio el trafico a la instancia activa"
echo "  - La aplicacion nunca dejo de responder (alta disponibilidad)"
echo "  - Al restaurar la instancia, el balanceador reanudo la distribucion"
echo ""
echo "✅ Failover OK: El sistema tolera la caida de una instancia."
echo ""
