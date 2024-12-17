package transacciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class Reportes {

    public void mostrarHistorial(String archivoHistorial) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoHistorial))) {
            StringBuilder historial = new StringBuilder("Historial de Tiquetes Atendidos:\n");
            HashMap<Integer, String> tiquetesUnicos = new HashMap<>(); // Usar ID como clave para evitar duplicados
            String linea;

            while ((linea = reader.readLine()) != null) {
                // Extraer datos relevantes del tiquete usando un formato conocido
                String[] datos = linea.replace("Tiquete{", "").replace("}", "").split(", ");
                String nombre = "No especificado";
                String edad = "No especificado";
                String tramite = "No especificado";
                String tipo = "No especificado";
                String horaCreacion = "No especificado";
                String horaAtencion = "No especificado";
                int id = -1;

                for (String dato : datos) {
                    if (dato.startsWith("id=")) id = Integer.parseInt(dato.split("=")[1]);
                    if (dato.startsWith("nombre=")) nombre = dato.split("=")[1];
                    if (dato.startsWith("edad=")) edad = dato.split("=")[1];
                    if (dato.startsWith("tramite1=")) tramite = dato.split("=")[1];
                    if (dato.startsWith("tipo=")) tipo = dato.split("=")[1];
                    if (dato.startsWith("horaCreacion=")) horaCreacion = dato.split("=")[1];
                    if (dato.startsWith("horaAtencion=")) horaAtencion = dato.split("=")[1];
                }

                // Construir el registro del tiquete
                String tiquete = String.format("Nombre: %s, Edad: %s, Trámite: %s, Tipo: %s, Hora Creación: %s, Hora Atención: %s",
                        nombre, edad, tramite, tipo, horaCreacion, horaAtencion);

                // Reemplazar si ya existe un tiquete con el mismo ID pero sin hora de atención
                if (id != -1 && (!tiquetesUnicos.containsKey(id) || !horaAtencion.equals("No especificado"))) {
                    tiquetesUnicos.put(id, tiquete);
                }
            }

            // Agregar los tiquetes únicos al historial
            for (String tiquete : tiquetesUnicos.values()) {
                historial.append(tiquete).append("\n");
            }

            JOptionPane.showMessageDialog(null, historial.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de historial: " + e.getMessage());
        }
    }

    public void mostrarReporteColas(List<Cola> colas) {
        if (colas == null || colas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay datos de colas disponibles.");
            return;
        }
        StringBuilder reporte = new StringBuilder("Reporte de Tiquetes en Colas:\n");
        for (int i = 0; i < colas.size(); i++) {
            reporte.append("Cola ").append(i + 1).append(": ").append(colas.get(i).getSize()).append(" tiquetes\n");
        }
        JOptionPane.showMessageDialog(null, reporte.toString());
    }

    public int contarTotalPersonas(String nombreArchivo) {
        int totalPersonas = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            while (reader.readLine() != null) {
                totalPersonas++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return -1;
        }

        return totalPersonas;
    }

    public int contarPersonasDelante(String nombreArchivo, int idCliente) {
        int contador = 0;
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                // Buscar el ID del cliente en cada linea
                if (linea.contains("id=" + idCliente)) {
                    encontrado = true;
                    break;
                }
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return -1;
        }

        if (!encontrado) {
            System.out.println("El cliente con ID " + idCliente + " no esta en la cola.");
            return -1;
        }

        return contador;
    }

    public int compararCajas(int numCaja1, int numCaja2, int totalPersonas1, int totalPersonas2) {

        if (totalPersonas1 == totalPersonas2) {
            return numCaja1;
        } else if (totalPersonas1 > totalPersonas2) {
            return numCaja2;
        } else {
            return numCaja1;
        }

    }

    // NUEVOS METODOS AÑADIDOS
public void cajaMayorClientesPorArchivos(String archivoPreferencial, String archivoRapida, String archivoRegular) {
    Map<String, Integer> cajaContador = new HashMap<>();

    // Contar clientes de cada archivo
    cajaContador.put("Caja Preferencial", contarClientesEnArchivo(archivoPreferencial));
    cajaContador.put("Caja Rápida", contarClientesEnArchivo(archivoRapida));
    cajaContador.put("Caja Regular", contarClientesEnArchivo(archivoRegular));

    // Encontrar la caja con más clientes atendidos
    Map.Entry<String, Integer> cajaMayor = cajaContador.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);

    // Mostrar resultados
    if (cajaMayor != null) {
        JOptionPane.showMessageDialog(null, "La caja con mayor clientes atendidos es: " 
                + cajaMayor.getKey() + " con " + cajaMayor.getValue() + " clientes.");
    } else {
        JOptionPane.showMessageDialog(null, "No se encontraron datos de clientes en las cajas.");
    }
}

// Método auxiliar para contar clientes en un archivo
private int contarClientesEnArchivo(String nombreArchivo) {
    int totalClientes = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
        while (reader.readLine() != null) {
            totalClientes++;
        }
    } catch (IOException e) {
        System.out.println("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage());
    }
    return totalClientes;
}



    public void tiempoPromedioAtencion(String archivoHistorial) {
        long totalTiempo = 0;
        int totalTiquetes = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoHistorial))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("horaCreacion=") && linea.contains("horaAtencion=")) {
                    String horaCreacion = linea.split("horaCreacion=")[1].split(",")[0];
                    String horaAtencion = linea.split("horaAtencion=")[1].split(",")[0];
                    if (!horaAtencion.equals("null")) {
                        long creacion = java.time.LocalDateTime.parse(horaCreacion).toEpochSecond(java.time.ZoneOffset.UTC);
                        long atencion = java.time.LocalDateTime.parse(horaAtencion).toEpochSecond(java.time.ZoneOffset.UTC);
                        totalTiempo += (atencion - creacion);
                        totalTiquetes++;
                    }
                }
            }
            if (totalTiquetes > 0) {
                long promedio = totalTiempo / totalTiquetes;
                JOptionPane.showMessageDialog(null, "Tiempo promedio de atención: " + promedio + " segundos.");
            } else {
                JOptionPane.showMessageDialog(null, "No hay tiquetes atendidos para calcular el promedio.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al calcular el tiempo promedio: " + e.getMessage());
        }
    }

    void cajaMayorClientesPorArchivos(String historialtxt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
