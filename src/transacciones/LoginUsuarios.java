/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transacciones;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author oscarcascante
 */
public class LoginUsuarios {

    private final String usersFile;

    public LoginUsuarios(String usersFile) {
        this.usersFile = usersFile;
        inicializarArchivo();
        
        /*Se llama el metodo de inizializacion para que se 
        cree el file con los usuarios y contraseñas predeterminadas cada vez que se inicie el programa*/
    }


    private void inicializarArchivo() {
        File file = new File(usersFile);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Crear usuarios predeterminados
                writer.write("admin:admin");
                writer.newLine();
                writer.write("usuario1:usuario1");
                writer.newLine();
                JOptionPane.showMessageDialog(null, "Archivo de usuarios creado con predeterminados.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el archivo: " + e.getMessage());
            }
        }
    }

    public boolean iniciarSesion() {
        String usuario = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");

        if (usuario == null || contrasena == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
     
             //Dividir el usuario y contraseña en dos para leerlo individualmente
                String[] parts = line.split(":");               
                if (parts.length == 2 && parts[0].equals(usuario) && parts[1].equals(contrasena)) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. ¡Bienvenido "+usuario+"!");
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
        return false;
    }

    public void registrarUsuario() {
        String usuario = JOptionPane.showInputDialog("Ingrese un nuevo nombre de usuario:");
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                //Revisa si el usuario ya eciste
                if (parts.length == 2 && parts[0].equals(usuario)) {
                    JOptionPane.showMessageDialog(null, "El usuario ya existe.");
                    return;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        }

        String contrasena = JOptionPane.showInputDialog("Ingrese una contraseña:");
        if (contrasena == null) {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true))) {
            //Guarda con el formato usuario:contrasena
            writer.write(usuario + ":" + contrasena);
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
