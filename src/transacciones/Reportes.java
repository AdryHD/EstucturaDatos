package transacciones;

import datos.Tiquete;
import javax.swing.JOptionPane;

public class Reportes {

    public long tiempoAtencion(Cola c) {
        Cola temp = new Cola();
        temp = c; // Assuming `copyFrom` duplicates the queue without altering the original.

        long totalTiempo = 0;

        while (temp.getSize() > 0) {
            Tiquete t = temp.getNodo(); // Retrieves and removes the first node from the queue.
            if (t != null && t.getHoraCreacion() != null && t.getHoraAtencion() != null) {
                long creacion = java.time.LocalDateTime.parse(t.getHoraCreacion()).toEpochSecond(java.time.ZoneOffset.UTC);
                long atencion = java.time.LocalDateTime.parse(t.getHoraAtencion()).toEpochSecond(java.time.ZoneOffset.UTC);
                totalTiempo += (atencion - creacion);
            }
        }
        System.out.println("colas luego de revisar el tiempo");
        System.out.println(c.toString());
        System.out.println(temp.toString());
        return totalTiempo;
    }

}
