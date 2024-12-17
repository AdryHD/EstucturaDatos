package transacciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.InputSource;

import javax.swing.JOptionPane;

public class TipoCambio {

    public static void obtenerIndicadoresEconomicos(String indicador, String fechaInicio, String fechaFinal,
            String nombre, String subNiveles, String correo, String token) {
        try {
            // URL con parametros correctos para consultar al servidor del BCCR
            String urlParams = String.format(
                    "/Indicadores/Suscripciones/WS/wsindicadoreseconomicos.asmx/ObtenerIndicadoresEconomicosXML?"
                    + "Indicador=%s&FechaInicio=%s&FechaFinal=%s&Nombre=%s&SubNiveles=%s&CorreoElectronico=%s&Token=%s",
                    URLEncoder.encode(indicador, "UTF-8"),
                    URLEncoder.encode(fechaInicio, "UTF-8"),
                    URLEncoder.encode(fechaFinal, "UTF-8"),
                    URLEncoder.encode(nombre, "UTF-8"),
                    URLEncoder.encode(subNiveles, "UTF-8"),
                    URLEncoder.encode(correo, "UTF-8"),
                    URLEncoder.encode(token, "UTF-8")
            );

           
            String urlCompleta = "https://gee.bccr.fi.cr" + urlParams;
            System.out.println("URL completa: " + urlCompleta);

            // Solicitud con HTTP GET
            URL url = new URL(urlCompleta);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

           //Respuesta del BCCR por consola
                System.out.println("Respuesta del servidor: " + response.toString());

                // Procesar la respuesta XML con metodo parsearXml
                String xml = response.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                parsearXml(xml,indicador);
            } else {
                throw new RuntimeException("HTTP Error: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener los indicadores económicos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void parsearXml(String xml, String indicador) {
        try {
         
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);

            
            NodeList nodeList = doc.getElementsByTagName("NUM_VALOR");
            String tipoDeCambio = "No encontrado";
            if (nodeList.getLength() > 0) {
                tipoDeCambio = nodeList.item(0).getTextContent();
            }

            nodeList = doc.getElementsByTagName("DES_FECHA");
            String fecha = "No encontrada";
            if (nodeList.getLength() > 0) {
                fecha = nodeList.item(0).getTextContent();
            }

            // Dependiendo del indicador, imprime Compra o Venta
            String tipoDeCambioTexto = "";
            if ("317".equals(indicador)) {
                tipoDeCambioTexto = "Tipo de Cambio Compra";
            } else if ("318".equals(indicador)) {
                tipoDeCambioTexto = "Tipo de Cambio Venta";
            }

          
            String mensaje = tipoDeCambioTexto + ": " + tipoDeCambio + "\nFecha: " + fecha;
            JOptionPane.showMessageDialog(null, mensaje);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al procesar el XML: " + e.getMessage());
        }
    }

}
