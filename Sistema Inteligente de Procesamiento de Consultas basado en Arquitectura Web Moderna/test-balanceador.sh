#!/bin/bash
# =============================================================================
# test-balanceador.sh — Verifica la distribucion Round Robin del balanceador
# Tópico 3: Balanceador de Carga | Arquitectura Web | Prof. Rodrigo Benítez
# Alumna: Natalia Godoy
# =============================================================================
# Uso: ./test-balanceador.sh
# Requiere: curl, jq (opcional para salida formateada)
# =============================================================================

BALANCEADOR="http://localhost:8080"
TOTAL_REQS=20
declare -A CONTADOR

echo "=========================================="
echo "  Test de Balanceo - Round Robin"
echo "  $TOTAL_REQS requests a $BALANCEADOR/instancia"
echo "=========================================="
echo ""

for i in $(seq 1 $TOTAL_REQS); do
    RESP=$(curl -s "$BALANCEADOR/instancia")
    PUERTO=$(echo "$RESP" | grep -o '"puerto":"[^"]*"' | cut -d'"' -f4)

    if [ -z "$PUERTO" ]; then
        PUERTO="SIN_RESPUESTA"
    fi

    CONTADOR[$PUERTO]=$(( ${CONTADOR[$PUERTO]} + 1 ))
    echo "[$i] -> Instancia : $PUERTO"
    sleep 0.3
done

echo ""
echo "=========================================="
echo "  Resumen de distribucion"
echo "=========================================="
for inst in "${!CONTADOR[@]}"; do
    PORC=$(( ${CONTADOR[$inst]} * 100 / TOTAL_REQS ))
    echo "  $inst : ${CONTADOR[$inst]} requests ($PORC%)"
done
echo ""
echo "  Total: $TOTAL_REQS requests"
echo "=========================================="

# Verificar que ambas instancias recibieron al menos 1 request
if [ ${#CONTADOR[@]} -lt 2 ]; then
    echo ""
    echo "⚠️  SOLO UNA instancia respondio. Verificar que ambas esten activas."
    echo "   Ejecuta: docker ps | grep ecomart"
    exit 1
else
    echo ""
    echo "✅ Balanceo OK: Ambas instancias recibieron trafico."
fi
