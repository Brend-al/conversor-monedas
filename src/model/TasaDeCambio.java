package model;

// Clase de modelo para mapear la respuesta JSON de la API
public class TasaDeCambio {

    private String base_code;
    private String target_code;
    private double conversion_rate;

    // Constructor vacío
    public TasaDeCambio() {}

    // Getters
    public String getBaseCode() {
        return base_code;
    }

    public String getTargetCode() {
        return target_code;
    }

    public double getConversionRate() {
        return conversion_rate;
    }
}
