package transacciones;

import datos.Tiquete;
import java.io.*;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class BancoConfig {

    private String nombreBanco;
    private int totalCajas;
    private int preferencialCaja;
    private int rapidaCaja;
    private int tiqueteID;
    private final String configFile;
    //   private final String tiquetFile;

    //Constructor para crear ruta del archivo de configuracion 
    public BancoConfig(String configFile) {
        this.configFile = configFile;
    }

    //Getters y setter para poder mostrar configuracion inicial en el menu principal
    public String getNombreBanco() {
        return nombreBanco;
    }

    public int getTotalCajas() {
        return totalCajas;
    }

    public int getPreferencialCaja() {
        return preferencialCaja;
    }

    public int getRapidaCaja() {
        return rapidaCaja;
    }

    public int getTiqueteID() {
        return tiqueteID;
    }

    public void setTiqueteID(int tiqueteID) {
        this.tiqueteID = tiqueteID;
    }

    //Configurar el sistema por primera vez
    public void configurarSistema() {
        do {
            nombreBanco = JOptionPane.showInputDialog("Ingrese el nombre del banco:");

            if (nombreBanco != null && !nombreBanco.isEmpty()) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "El nombre del banco no puede estar vacío.");
            }

        } while (nombreBanco == null || nombreBanco.isEmpty());

        do {
            try {
                totalCajas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número total de cajas (mínimo 3):\n"
                        + "*Tome en cuenta que 1 caja sera preferencial, 1 caja rápida y el resto cajas regulares*"));
                if (totalCajas < 3) {
                    JOptionPane.showMessageDialog(null, "Debe haber al menos 3 cajas.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
                totalCajas = 0;
            }
        } while (totalCajas < 3);

        totalCajas -= 2; //Resta 1 caja preferencial y 1 rapida, las que queden son las regulares
        preferencialCaja = 1;
        rapidaCaja = 1;
        tiqueteID = 0;

        guardarConfiguracion();
    }

    public void guardarConfiguracion() {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(configFile))) {
            output.write(nombreBanco + "\n");
            output.write(totalCajas + "\n");
            output.write(preferencialCaja + "\n");
            output.write(rapidaCaja + "\n");
            output.write(tiqueteID + "\n");
            //JOptionPane.showMessageDialog(null, "Configuración guardada exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración: " + e.getMessage());
        }
    }

    public boolean cargarConfiguracion() {
        File file = new File(configFile);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            nombreBanco = reader.readLine();
            totalCajas = Integer.parseInt(reader.readLine());
            preferencialCaja = Integer.parseInt(reader.readLine());
            rapidaCaja = Integer.parseInt(reader.readLine());
            tiqueteID = Integer.parseInt(reader.readLine());
            return true;
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la configuración: " + e.getMessage());
            return false;
        }
    }

    public void escribirColaEnArchivo(String nombreArchivo, Cola cola) {
         File file = new File(nombreArchivo);
         file.delete();
        //System.out.println(cola.toString());
        if (cola.getSize() > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {

               // System.out.println("Tamano de la cola: " + cola.getSize());
                while (cola.getSize() > 0) {
                    writer.write("**************Nuevo Tiquete********************");
                    writer.newLine();
                    writer.write(cola.getNodo() + "\n");
                    writer.newLine();
                    writer.write("***********************************************");
                    writer.newLine();
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al escribir en el archivo " + nombreArchivo + ": " + e.getMessage());
            }
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
                writer.write("**************No hay tiquetes********************");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al escribir en el archivo " + nombreArchivo + ": " + e.getMessage());
            }
        }
    }


public void crearCola(String nombreArchivo, Cola cola) {
  //  System.out.println("Iniciando crearCola " + nombreArchivo);

    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains("[") && line.contains("]")) {
                int startIndex = line.indexOf('[') + 1;
                int endIndex = line.indexOf(']');
                if (startIndex < endIndex) {
                    String content = line.substring(startIndex, endIndex);

               
                    String nombre = "";
                    int id = 0, edad = 0, tramite = 0, tramite2 = 0, tipo = 0, cajaAsignada = 0;
                    LocalDateTime horaCreacion = null;
                    LocalDateTime horaAtencion = null;

                    
                    int currentIndex = 0;
                    while (currentIndex < content.length()) {
                        int equalsIndex = content.indexOf('=', currentIndex);
                        if (equalsIndex == -1) break;

                      
                        int keyStart = currentIndex;
                        int keyEnd = equalsIndex;
                        String key = content.substring(keyStart, keyEnd).trim();

                      
                        int valueStart = equalsIndex + 1;
                        int commaIndex = content.indexOf(',', valueStart);
                        if (commaIndex == -1) {
                            commaIndex = content.length(); 
                        }
                        String value = content.substring(valueStart, commaIndex).trim();

                       
                        switch (key) {
                            case "Nombre":
                                nombre = value;
                                break;
                            case "ID":
                                id = Integer.parseInt(value);
                                break;
                            case "Edad":
                                edad = Integer.parseInt(value);
                                break;
                            case "Tramite":
                                tramite = Integer.parseInt(value);
                                break;
                            case "Tramite2":
                                tramite2 = Integer.parseInt(value);
                                break;
                            case "Tipo":
                                tipo = Integer.parseInt(value);
                                break;
                            case "Hora Creación":
                                if (!value.isEmpty()) {
                                   horaCreacion = LocalDateTime.parse(value);
                                }
                                break;
                            case "Hora Atención":
                                if (!value.isEmpty()) {
                                    horaAtencion = LocalDateTime.parse(value);
                                }
                                break;
                            case "Caja Asignada":
                                cajaAsignada = Integer.parseInt(value);
                                break;
                            default:
                                System.err.println("Campo desconocido: " + key);
                        }

                        // Update index for the next iteration
                        currentIndex = commaIndex + 1;
                    }

                  
                    Tiquete tiquete = new Tiquete();
                    tiquete.setNombre(nombre);
                    tiquete.setId(id);
                    tiquete.setEdad(edad);
                    tiquete.setTramite(tramite);
                    tiquete.setTramite2(tramite2);
                    tiquete.setTipo(tipo);
                    tiquete.setHoraCreacion(horaCreacion);
                    tiquete.setHoraAtencion(horaAtencion);
                    tiquete.setCajaAsignada(cajaAsignada);

                    //System.out.println("Agregando el tiquete a la cola: " + tiquete);
                    cola.encola(tiquete);
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error leyendo el archivo: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Error procesando los datos: " + e.getMessage());
    }
}


}
