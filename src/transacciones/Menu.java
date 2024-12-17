/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transacciones;

import datos.Tiquete;
import transacciones.BancoConfig;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showInputDialog;

/**
 *
 * @author adryhd
 */
public class Menu {

    LoginUsuarios login = new LoginUsuarios("usuarios.txt");
    BancoConfig bancoConfig = new BancoConfig("prod.txt");
    
    Cola cajaRapida = new Cola();
    Cola cajaPreferencial = new Cola();
    Cola cajaRegular = new Cola();

  

    public void MenuPrincipal() {
        try {                
             
            // Carga configuracion
            if (!bancoConfig.cargarConfiguracion()) {
                // Si no existe, llama metodo para configurar por primera vez
                bancoConfig.configurarSistema();
            }

            int opc = 0;
            opc = Integer.parseInt(showInputDialog(null, "Seleccione una opción\n"
                    + "\n"
                    + "     1 - Inicio Sesion\n"
                    + "\n"
                    + "     2 - Registrar Usuario\n"
                    + "\n"
                    + "     3 - Salir\n"
                    + "\n"
                    + "\n", "Menu Principal", JOptionPane.WARNING_MESSAGE));

            switch (opc) {
                case 1: {
                    if (login.iniciarSesion()) {
                        cargarColas();
                        MenuSecundario();
                    } else {
                        MenuPrincipal();
                    }
                    break;
                }
                case 2:
                    login.registrarUsuario();
                    MenuPrincipal();
                    break;
                case 3: {
                   // bancoConfig.guardarConfiguracion();
                   // bancoConfig.escribirColaEnArchivo("regular.txt", cajaRegular);
                    //bancoConfig.escribirColaEnArchivo("preferencial.txt", cajaPreferencial);
                    //bancoConfig.escribirColaEnArchivo("rapida.txt", cajaRapida);
                    System.exit(0);
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opcion seleccionada no es válida, verifique e intente nuevamente");
                    MenuPrincipal();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Digite una de las opciones válidas, verifique e intente nuevamente");
            MenuPrincipal();
        }
    }

    public void MenuSecundario() {
        try {
            int opc = 0;

            opc = Integer.parseInt(showInputDialog(null, "Seleccione una opción\n"
                    + "\n"
                    + "     1 - Crear Tiquete\n"
                    + "\n"
                    + "     2 - Reportes\n"
                    + "\n"
                    + "     3 - Tiquete Atendido\n"
                    + "\n"
                    + "     4 - Salir\n"
                    + "\n"
                    + "\n", "Menú de usuarios", JOptionPane.WARNING_MESSAGE));

            switch (opc) {
                case 1: {
                    CrearTiquete();
                    MenuSecundario();
                }
                break;

                case 2:
                    MenuReportes();
                    break;
                case 3:
                    break;
                case 4: {
                    bancoConfig.guardarConfiguracion();
                    System.out.println("Guardando colas");
                    bancoConfig.escribirColaEnArchivo("regular.txt", cajaRegular);
                    bancoConfig.escribirColaEnArchivo("preferencial.txt", cajaPreferencial);
                    bancoConfig.escribirColaEnArchivo("rapida.txt", cajaRapida);
                    MenuPrincipal();
                }
                break;
                default: {
                    JOptionPane.showMessageDialog(null, "Opcion seleccionada no es válida, verifique e intente nuevamente");
                    MenuSecundario();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Digite una de las opciones válidas, verifique e intente nuevamente");
            MenuSecundario();
        }

    }

    public void CrearTiquete() {
        Tiquete tiquete = new Tiquete();
        boolean creado = true;
        int opc = 0;
        int a = 1;
        int reply = 0;
        try {
            do {

                opc = Integer.parseInt(showInputDialog(null, "Seleccione una tipo de Caja\n"
                        + "\n"
                        + "     1 - Regular\n"
                        + "\n"
                        + "     2 - Rápida\n"
                        + "\n"
                        + "     3 - Preferencial\n"
                        + "\n"
                        + "\n", "Crear Tiquete", JOptionPane.WARNING_MESSAGE));
                if ((opc < 1) || (opc > 3)) {
                    JOptionPane.showMessageDialog(null, "Opcion seleccionada no es válida, verifique e intente nuevamente");
                }
            } while ((opc > 3) || (opc < 1));
        } catch (Exception e) {
            creado = false;
            JOptionPane.showMessageDialog(null, "Uno o mas valores son inválidos, verifique e intente nuevamente");
            MenuSecundario();
        }
        tiquete.setTipo(opc);
        opc = 0;
        //System.out.println("ID que viene del config al iniciar " + bancoConfig.getTiqueteID());
        tiquete.setNombre(JOptionPane.showInputDialog(null, "Ingrese su nombre: ", "Crear Tiquete", JOptionPane.WARNING_MESSAGE));
        //System.out.println(tiquete.getNombre());
        tiquete.setId(bancoConfig.getTiqueteID() + 1);
        bancoConfig.setTiqueteID(tiquete.getId());
        tiquete.setEdad(Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese su edad: ", "Crear Tiquete", JOptionPane.WARNING_MESSAGE)));
        do {

            opc = Integer.parseInt(showInputDialog(null, "Seleccione una tipo de Tramite\n"
                    + "\n"
                    + "     1 - Depósito\n"
                    + "\n"
                    + "     2 - Retiro\n"
                    + "\n"
                    + "     3 - Cambio de Divisas\n"
                    + "\n"
                    + "\n", "Crear Tiquete", JOptionPane.WARNING_MESSAGE));
            if ((opc < 1) || (opc > 3)) {
                JOptionPane.showMessageDialog(null, "Opcion seleccionada no es válida, verifique e intente nuevamente");
            }
        } while ((opc > 3) || (opc < 1));
        tiquete.setTramite(opc);
        opc = 0;

        tiquete.setHoraCreacion(LocalDateTime.now());

        if (creado == true) {
            switch (tiquete.getTipo()) {
                case 1: {
                    if (cajaRegular.getSize() == 0) {
                        tiquete.setHoraAtencion(LocalDateTime.now());
                        tiquete.setCajaAsignada(1);
                    } else {
                        tiquete.setCajaAsignada(2);
                          tiquete.setHoraAtencion(LocalDateTime.MIN);
                    }/*
                        for (int i = 0; i < cajaRegular.getSize(); i++) {
                            for (int j = 0; j < bancoConfig.getTotalCajas(); j++) {
                                if (cajaRegular.encontrar(j + 1) == true) {
                                                                                 //logica para encontrar cual caja tiene menos tiquetes aun en desarrollo
                                }
                            }
                        }
                        for (int x = 0; x < bancoConfig.getTotalCajas(); x++) {
                            if (contadorCajas[x] < a) {
                                a = contadorCajas[x];
                                tiquete.setCajaAsignada(x + 1);
                            }
                        }
                    }*/


                    if (tiquete.getTipo() == 1) {
                        reply = JOptionPane.showConfirmDialog(null, "Desea agreagar otro tramite?", "Tramite Adicional", JOptionPane.YES_NO_OPTION);
                    }
                    if (reply == JOptionPane.YES_OPTION) {
                        do {

                            opc = Integer.parseInt(showInputDialog(null, "Seleccione una tipo de Tramite\n"
                                    + "\n"
                                    + "     1 - Depósito\n"
                                    + "\n"
                                    + "     2 - Retiro\n"
                                    + "\n"
                                    + "     3 - Cambio de Divisas\n"
                                    + "\n"
                                    + "\n", "Crear Tiquete", JOptionPane.WARNING_MESSAGE));
                            if ((opc < 1) || (opc > 3)) {
                                JOptionPane.showMessageDialog(null, "Opcion seleccionada no es válida, verifique e intente nuevamente");
                            }
                        } while ((opc > 3) || (opc < 1));
                        tiquete.setTramite2(opc);
                    }
                    cajaRegular.encola(tiquete);
                    JOptionPane.showMessageDialog(null, "El tiquete se ha asignado a la caja " + tiquete.getCajaAsignada() + "\n"
                            + "Caja Tipo Regular\n", "Tiquete Creado con éxito", JOptionPane.WARNING_MESSAGE);

                }

                break;

                case 2: {
                    if (cajaRapida.getSize() == 0) {
                        tiquete.setHoraAtencion(LocalDateTime.now());
                    }
                    tiquete.setCajaAsignada(bancoConfig.getTotalCajas() + 1);
                    cajaRapida.encola(tiquete);
                    JOptionPane.showMessageDialog(null, "El tiquete se ha asignado a la caja " + tiquete.getCajaAsignada() + "\n"
                            + "Caja Tipo Rápida\n", "Tiquete Creado con éxito", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                case 3: {
                    if (cajaPreferencial.getSize() == 0) {
                        tiquete.setHoraAtencion(LocalDateTime.now());
                    }
                    tiquete.setCajaAsignada(bancoConfig.getTotalCajas() + 2);
                    cajaPreferencial.encola(tiquete);
                    JOptionPane.showMessageDialog(null, "El tiquete se ha asignado a la caja " + tiquete.getCajaAsignada() + "\n"
                            + "Caja Tipo Preferencial\n", "Tiquete Creado con éxito", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }

            // System.out.println("ID tiquete creado " + tiquete.getId());
            //System.out.println("ID que viene del config luego de crear el tiquete " + bancoConfig.getTiqueteID());
        }

        System.out.println("El tiquete");
        System.out.println(tiquete);
        System.out.println("Contenido de La cola");
        System.out.println("****************************************");
        System.out.println(cajaRegular.toString());
        System.out.println("****************************************");
        System.out.println(cajaRapida.toString());
        System.out.println("****************************************");
        System.out.println(cajaPreferencial.toString());
        System.out.println("****************************************");
    }

    public void MenuReportes() {
        try {
            int opc = 0;

            opc = Integer.parseInt(showInputDialog(null, "Seleccione una opción\n"
                    + "\n"
                    + "     1 - Informacion del Banco\n"
                    + "\n"
                    + "     2 - Reporte Tiquetes\n"
                    + "\n"
                    + "     3 - Reporte Cajas\n"
                    + "\n"
                    + "     4 - Salir\n"
                    + "\n"
                    + "\n", "Menú de Reportes", JOptionPane.WARNING_MESSAGE));

            switch (opc) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Banco: " + bancoConfig.getNombreBanco()
                            + "\nCajas regulares: " + bancoConfig.getTotalCajas()
                            + "\nCaja preferencial: " + bancoConfig.getPreferencialCaja()
                            + "\nCaja rápida: " + bancoConfig.getRapidaCaja());
                    // + "\nTotal de Cajas " + bancoConfig.getTotalCajas());
                    MenuReportes();
                case 2:
                    MenuReportes();
                    break;
                case 3:
                    MenuReportes();
                    break;
                case 4:
                    MenuSecundario();
                    break;
                default: {
                    JOptionPane.showMessageDialog(null, "Opcion seleccionada no es válida, verifique e intente nuevamente");
                    MenuSecundario();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Digite una de las opciones válidas, verifique e intente nuevamente");
            MenuReportes();
        }
    }
    
    public void cargarColas(){
     //System.out.println("Cola al leer del txt");
                System.out.println("*************************");
                bancoConfig.crearCola("regular.txt", cajaRegular);
                System.out.println(cajaRegular.toString());
                System.out.println("*************************");
                bancoConfig.crearCola("preferencial.txt", cajaPreferencial);
                System.out.println(cajaPreferencial.toString());
                System.out.println("*************************");
                bancoConfig.crearCola("rapida.txt", cajaRapida);
                System.out.println(cajaRapida.toString());
                System.out.println("*************************");}
      
}
