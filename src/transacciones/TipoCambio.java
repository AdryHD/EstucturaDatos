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

    private static final String HOST = "https://gee.bccr.fi.cr/Indicadores/Suscripciones/WS/wsindicadoreseconomicos.asmx/ObtenerIndicadoresEconomicosXML";
    private String url;

    private void setUrl(String indicador, String fechaInicio, String fechaFinal, String nombre, String subNiveles, String correo, String token) {

        String params = "Indicador=" + indicador
                + "&FechaInicio=" + fechaInicio
                + "&FechaFinal=" + fechaFinal
                + "&Nombre=" + nombre
                + "&SubNiveles=" + subNiveles
                + "&CorreoElectronico=" + correo
                + "&Token=" + token;

        this.url = HOST + "?" + params;
    }

    
    public void obtenerIndicadoresEconomicos(String indicador, String fechaInicio, String fechaFinal, String nombre, String subNiveles, String correo, String token) {
        try {
            // Configurar la URL antes de usarla
            setUrl(indicador, fechaInicio, fechaFinal, nombre, subNiveles, correo, token);

            System.out.println("URL generada: " + url);

            // Realizar la solicitud HTTP GET
            URL urlObj = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
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

                // Respuesta del servidor
                String xml = response.toString().replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                System.out.println("Respuesta del servidor: " + xml);

                // Procesar la respuesta XML
                parsearXml(xml, indicador);
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
