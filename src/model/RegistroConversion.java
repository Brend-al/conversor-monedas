package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase para registrar cada conversión realizada con marca de tiempo
public class RegistroConversion {
    private final String monedaBase;
    private final String monedaTarget;
    private final double cantidad;
    private final double resultado;
    private final LocalDateTime marcaDeTiempo;

    public RegistroConversion(String monedaBase, String monedaTarget, double cantidad, double resultado) {
        this.monedaBase = monedaBase;
        this.monedaTarget = monedaTarget;
        this.cantidad = cantidad;
        this.resultado = resultado;
        this.marcaDeTiempo = LocalDateTime.now(); // Captura el momento exacto
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %.2f %s -> %.2f %s",
                marcaDeTiempo.format(formatter),
                cantidad, monedaBase, resultado, monedaTarget);
    }
}
