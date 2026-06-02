#!/bin/bash

echo "=== TEST DE BALANCEO DE CARGA ==="
echo "Haciendo 20 peticiones a http://localhost:8080/instancia"
echo ""

contador8081=0
contador8082=0

for i in {1..20}
do
    respuesta=$(curl -s http://localhost:8080/instancia)
    puerto=$(echo "$respuesta" | grep -o '"puerto":"[^"]*"' | cut -d'"' -f4)
    echo "Peticion $i: puerto=$puerto | $respuesta"

    if [ "$puerto" = "8081" ]; then
        ((contador8081++))
    elif [ "$puerto" = "8082" ]; then
        ((contador8082++))
    fi

    sleep 0.5
done

echo ""
echo "=== RESULTADOS ==="
echo "Instancia 1 (puerto 8081): $contador8081 peticiones"
echo "Instancia 2 (puerto 8082): $contador8082 peticiones"
total=$((contador8081 + contador8082))
echo "Total: $total peticiones"

echo ""
if [ $total -gt 0 ]; then
    pct1=$((contador8081 * 100 / total))
    pct2=$((contador8082 * 100 / total))
    echo "Distribucion:"
    echo "  8081: $pct1%"
    echo "  8082: $pct2%"

    if [ $pct1 -ge 40 ] && [ $pct1 -le 60 ]; then
        echo ""
        echo "✅ BALANCEO CORRECTO: Round Robin distribuye equitativamente"
    else
        echo ""
        echo "⚠️ REVISAR: La distribucion no es equitativa"
    fi
else
    echo "❌ No se recibieron respuestas. Verifica que el balanceador este corriendo en http://localhost:8080"
fi