package transacciones;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class Grafo {

    // Representacion del grafo: Nodo principal -> Lista de productos complementarios
    private Map<String, List<String>> grafo;

    public Grafo() {
        grafo = new HashMap<>();
        inicializarGrafo();
    }

    // Inicializar el grafo con los tramites y productos complementarios
    private void inicializarGrafo() {
        agregarArista("Depositos", "Seguros");
        agregarArista("Retiros", "Retiro sin tarjeta");
        agregarArista("Cambio_De_Divisas", "Cambio a moneda digital");
    }

    // Agregar una relacion (arista) entre un tramite y un producto complementario
    public void agregarArista(String tramite, String producto) {
        grafo.putIfAbsent(tramite, new LinkedList<>());
        grafo.get(tramite).add(producto);
    }

    // Obtener los productos complementarios para un tramite dado
    public List<String> obtenerProductos(String tramite) {
        return grafo.getOrDefault(tramite, new LinkedList<>());
    }

    // Mostrar el grafo (para pruebas)
    public void mostrarGrafo() {
        for (Map.Entry<String, List<String>> entry : grafo.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
