package conversor;

import com.google.gson.Gson;
import model.RegistroConversion;
import model.TasaDeCambio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConversorApp {


	// 1. Obtiene la clave de las variables de entorno del sistema
	private static final String API_KEY = System.getenv("EXCHANGE_RATE_API_KEY");

	// 2. Comprueba si la clave es nula o vacía al inicio
	static {
		if (API_KEY == null || API_KEY.trim().isEmpty()) {
			System.err.println("FATAL: La clave API 'EXCHANGE_RATE_API_KEY' no está configurada en las variables de entorno.");
			System.exit(1); // Termina la aplicación si la clave no existe
		}
	}


    // URL base
    private static final String BASE_URL =
            "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";

    private final HttpClient cliente = HttpClient.newHttpClient();
    private final Scanner scanner = new Scanner(System.in);
    private final List<RegistroConversion> historial = new ArrayList<>();

    // Método llamado desde main.java
    public void iniciar() {
        mostrarMenu();
    }

    // Lógica de Conexión y Obtención de Tasa
    // ----------------------------------------------------------------------

    public TasaDeCambio obtenerTasa(String monedaBase, String monedaTarget) {

        String urlConsulta = BASE_URL + monedaBase + "/" + monedaTarget;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlConsulta))
                .GET()
                .build();

        try {
            HttpResponse<String> response = cliente
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                // Deserialización automática del JSON
                TasaDeCambio tasa = gson.fromJson(response.body(), TasaDeCambio.class);
                return tasa;
            } else {
                System.err.println(" Error al consultar la API. Código: " + response.statusCode());
                System.err.println("Mensaje de error: " + response.body());
                return null;
            }

        } catch (IOException e) {
            System.err.println(" Error de I/O (red/conexión) al solicitar la tasa: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            System.err.println(" La solicitud fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }
    }

    // ----------------------------------------------------------------------
    // Lógica de Interacción con el Usuario (Fases 8, 10)
    // ----------------------------------------------------------------------

    private void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n**************************************************");
            System.out.println("  👋 Sea bienvenido/a al Conversor de Moneda 💵");
            System.out.println("**************************************************");
            System.out.println("Seleccione la conversión que desea realizar:");
            System.out.println("1) Dólar (USD) >> Peso Argentino (ARS)");
            System.out.println("2) Peso Argentino (ARS) >> Dólar (USD)");
            System.out.println("3) Dólar (USD) >> Real Brasileño (BRL)");
            System.out.println("4) Real Brasileño (BRL) >> Dólar (USD)");
            System.out.println("5) Dólar (USD) >> Peso Colombiano (COP)");
            System.out.println("6) Peso Chileno (CLP) >> Dólar (USD)");
            System.out.println("7) Conversión Personalizada (Códigos)");
            System.out.println("8) Mostrar Historial");
            System.out.println("9) Salir");
            System.out.println("--------------------------------------------------");
            System.out.print("Elija una opción válida (1-9): ");

            try {
                String entrada = scanner.nextLine();
                opcion = Integer.parseInt(entrada);

                if (opcion >= 1 && opcion <= 6) {
                    ejecutarConversion(opcion);
                } else if (opcion == 7) {
                    conversionPersonalizada();
                } else if (opcion == 8) {
                    mostrarHistorial();
                } else if (opcion == 9) {
                    System.out.println("Gracias por usar el conversor. ¡Hasta pronto! 👋");
                } else {
                    System.err.println(" Opción no válida. Por favor, ingrese un número entre 1 y 9.");
                }

            } catch (NumberFormatException e) {
                System.err.println(" Entrada inválida. Por favor, ingrese solo el número de la opción.");
                opcion = 0;
            }
        } while (opcion != 9);

        scanner.close();
    }

    private void ejecutarConversion(int opcion) {
        String monedaBase = "";
        String monedaTarget = "";

        switch (opcion) {
            case 1: monedaBase = "USD"; monedaTarget = "ARS"; break;
            case 2: monedaBase = "ARS"; monedaTarget = "USD"; break;
            case 3: monedaBase = "USD"; monedaTarget = "BRL"; break;
            case 4: monedaBase = "BRL"; monedaTarget = "USD"; break;
            case 5: monedaBase = "USD"; monedaTarget = "COP"; break;
            case 6: monedaBase = "CLP"; monedaTarget = "USD"; break;
        }

        pedirCantidadYConvertir(monedaBase, monedaTarget);
    }

    private void conversionPersonalizada() {
        System.out.print("Ingrese el código de la moneda BASE (ej: USD): ");
        String base = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese el código de la moneda TARGET (ej: EUR): ");
        String target = scanner.nextLine().toUpperCase();

        pedirCantidadYConvertir(base, target);
    }

    public void mostrarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("\n--- El historial está vacío. Realice una conversión primero. ---");
            return;
        }

        System.out.println("\n======== 📜 Historial de Conversiones 📜 ========");
        historial.forEach(System.out::println);
        System.out.println("==================================================");
    }

    // ----------------------------------------------------------------------
    // Lógica de Conversión y Cálculo (Fase 9)
    // ----------------------------------------------------------------------

    private void pedirCantidadYConvertir(String monedaBase, String monedaTarget) {

        System.out.print("\n➡️ Ingrese la cantidad de " + monedaBase + " a convertir: ");

        try {
            double cantidad = Double.parseDouble(scanner.nextLine());

            if (cantidad <= 0) {
                System.err.println(" La cantidad a convertir debe ser un valor positivo.");
                return;
            }

            System.out.println("Cargando tasa de cambio...");
            TasaDeCambio tasaObtenida = obtenerTasa(monedaBase, monedaTarget);

            if (tasaObtenida != null) {

                double tasa = tasaObtenida.getConversionRate();
                double resultado = cantidad * tasa;

                // Guarda en el historial
                RegistroConversion nuevoRegistro = new RegistroConversion(monedaBase, monedaTarget, cantidad, resultado);
                historial.add(nuevoRegistro);

                // Muestra el resultado final
                System.out.println("\n--------------------------------------------------");
                System.out.printf("El valor de %.2f %s equivale a %.2f %s%n",
                        cantidad, monedaBase, resultado, monedaTarget);
                System.out.println("--------------------------------------------------");

            } else {
                System.err.println(" No se pudo completar la conversión.");
            }

        } catch (NumberFormatException e) {
            System.err.println(" Entrada inválida. Por favor, ingrese un número válido (ej: 100.50).");
        }
    }
}
