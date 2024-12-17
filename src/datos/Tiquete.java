package datos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tiquete {
    
    private String nombre;
    private int id;
    private int edad;
    private int tramite; //1-Retiro 2-Deposito 3- Cambio de Divisas
    private int tramite2; //1-Retiro 2-Deposito 3- Cambio de Divisas
    private int tipo; //1-Regular  2-Rapida  3-Preferencial
    private LocalDateTime horaCreacion;
    private LocalDateTime horaAtencion;
    private int cajaAsignada;

    // Constructor
    public Tiquete(String nombre, int id, int edad, int tramite, int tramite2, int tipo) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.tramite = tramite;
        this.tramite2 = tramite2;
        this.tipo = tipo;
        this.horaCreacion = horaCreacion; // Hora de creación, obtiene la hora actual del sistema
        this.horaAtencion = null; // Se asignará cuando el tiquete sea atendido
        this.cajaAsignada = tipo; // Se asignará una caja cuando el tiquete sea atendido
    }

    public Tiquete() {
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public String setNombre(String Nombre) {
        this.nombre = Nombre;
        return nombre;
    }

    public int getId() {
        return id;
    }
    
    public int setId(int id) {
        this.id = id;
        return id;
    }

    public int getEdad() {
        return edad;
    }
    public int setEdad(int edad) {
        this.edad = edad;
        return edad;
    }

    public int getTramite2() {
        return tramite2;
    }

    public void setTramite2(int tramite2) {
        this.tramite2 = tramite2;
    }

    public int getTramite() {
        return tramite;
    }
    public int setTramite(int tramite) {
        this.tramite = tramite;
        return tramite;
    }

    public int getTipo() {
        return tipo;
    }
    public int setTipo(int tipo) {
        this.tipo = tipo;
        return tipo;
    }

    public String getHoraCreacion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return horaCreacion.format(formatter);
    }

    public String getHoraAtencion() {
        return (horaAtencion == null) ? "En espera" : horaAtencion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public int getCajaAsignada() {
        return cajaAsignada;
    }

    public void setHoraAtencion(LocalDateTime horaAtencion) {
        this.horaAtencion = horaAtencion;
    }

    public void setHoraCreacion(LocalDateTime horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public void setCajaAsignada(int cajaAsignada) {
        this.cajaAsignada = cajaAsignada;
    }

    @Override
    public String toString() {
        return "Tiquete [Nombre=" + nombre + ", ID=" + id + ", Edad=" + edad + ", Tramite=" + tramite + ", Tramite2=" + tramite2 +", Tipo=" + tipo
                + ", Hora Creación=" + horaCreacion + ", Hora Atención=" + horaAtencion + ", Caja Asignada=" + cajaAsignada + "]";
    }
}
