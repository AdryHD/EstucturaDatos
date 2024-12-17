/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_v3;

import transacciones.Menu;

/**
 *
 * @author adryhd
 */
public class Proyecto_V3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       /*  if (!bancoConfig.cargarConfiguracion()) {
            // Si no existe, llama metodo para configurar por primera vez
            bancoConfig.configurarSistema();
        }*/
       Menu Menu = new Menu();
       Menu.MenuPrincipal();
    }
    
}
