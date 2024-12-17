package transacciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class TipoCambio {
    
     private int indicador = 0; //317: Compra, 318: Venta
  private String tcFechaInicio;
  private String tcFechaFinal;
  private final String tcNombre = "TEC";
  private final String tnSubNiveles = "N";
  private final String HOST = "http://indicadoreseconomicos.bccr.fi.cr/indicadoreseconomicos/WebServices/wsindicadoreseconomicos.asmx/ObtenerIndicadoresEconomicosXML";
  private String url;
  private final String VALUE_TAG = "NUM_VALOR";
  /*
  public TipoCambio(){
    setFecha();
  }
  /*
  
  /**
   * Devuelve el valor de <strong>COMPRA</strong> USD del BCCR
   * @return <code>Double</code> con el valor del precio de compra.
   */
 /* public double getCompra(){
    setCompra();
    
    double valor = Double.parseDouble(getValue());
    return valor;
  }
  
  /**
   * Devuelve el valor de <strong>VENTA</strong> USD del BCCR
   * @return <code>Double</code> con el valor del precio de venta.
   */
  /*public double getVenta(){
    setVenta();
    
    double valor = Double.parseDouble(getValue());
    return valor;
  }
/*
  private String getValue(){
    try {
      setUrl(); //Set del Url con los Parámetros
      
      //Obtiene y Parsea el XML
      String data = GetMethod.getHTML(url);
      XmlParser xml = new XmlParser(data);
      
      //Retorna el valor del tag
      return xml.getValue(VALUE_TAG);
    } catch (Exception e) {
      System.out.println("Error al obtener el valor del BCCR.");
      return "0";
    }
  }
  
  private void setUrl(){
    String params = "tcIndicador="+indicador+"&tcFechaInicio="+tcFechaInicio+"&tcFechaFinal="+tcFechaFinal+"&tcNombre="+tcNombre+"&tnSubNiveles="+tnSubNiveles;
    this.url = HOST+"?"+params;
  }
  
  private void setFecha(){
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    this.tcFechaInicio = sdf.format(date);
    this.tcFechaFinal = tcFechaInicio;
  }
  
  private void setCompra(){
    this.indicador = 317;
  }
  
  private void setVenta(){
    this.indicador = 318;
  }
  protected static String getHTML(String urlToRead) throws Exception {
    StringBuilder result = new StringBuilder();
    URL url = new URL(urlToRead);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    
    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
    while ((line = rd.readLine()) != null) {
       result.append(line);
    }
    rd.close();
    return result.toString();
  }*/
}
