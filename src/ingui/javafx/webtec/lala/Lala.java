/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingui.javafx.webtec.lala;

import ingui.javafx.webtec.Webtec;
import innui.contextos.contextos;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal de ejemplo de Lala
 */
public class Lala extends Webtec {
    /**
     * Atributo para almacenar la url pasada por línea de comando-
     */
    public static String url_linea_comando_texto = ""; //NOI18N
    /**
     * Atributo con la url por defecto si no se pasa una url por la línea de comando.
     */
    public static String k_url_por_defecto_texto = "https://"+Webtec.k_prefijo_url+"/"; //NOI18N
    /**
     * Constructor con un mapeo de patrones de url (sin k_prefijo_url) y las clases que ejecutar con su plantilla, de igual nombre
     * También construye el objeto donde almacenar los datos de contexto.
     */   
    public Lala() {
        contexto = new contextos();
        rutas_mapa = new HashMap () { 
            {
                put("/", "innui.webtec.lala.paginas_principales"); //NOI18N
            }
        };
    }
    /**
     * Sustitución para cargar el icono de la aplicación.
     * @return true si todo es correcto; false si hay error.
     */
    @Override
    public boolean configurar(Stage stage, String [] error) {
        boolean ret = true;
        stage.setTitle("Asistente de Lala");
        ObservableList<Image> observableList = stage.getIcons();
        InputStream inputStream
                = Webtec.class.getResourceAsStream(
                "/recursos/ingui/javafx/webtec/gui/icono_web_carpeta.png"); //NOI18N
        Image image = new Image(inputStream);
        observableList.add(image);
        return ret;
    }
    /**
     * Método con la llamada principal de la aplicación, a la url correspondiente (o la de lína de comando o la definida por defecto)
     * @param error Mensaje de error, si lo hay
     * @return true si tiene éxito, o false si hay error
     */
    @Override
    public boolean iniciar(String [] error) {
        boolean ret = true;
        Map <String, Object> datos_mapa;
        String url_texto = k_url_por_defecto_texto;
        try {
            datos_mapa = new LinkedHashMap();
            if (url_linea_comando_texto != null && url_linea_comando_texto.isEmpty() == false) {
                url_texto = url_linea_comando_texto;
            }
            ret = fXML_webtec_jafController.procesar_url(new URL(url_texto), datos_mapa, error); //NOI18N
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("in/javafx/webtec/gui/in").getString("ERROR EN INICIAR EN GUI_WEBTEC. {0}"), new Object[] {error[0]});
            ret = false;
        }
        if (ret == false) {
            fXML_webtec_jafController.poner_error(error[0]);
        }
        return ret;
    }
    /**
     * Método main que captura la url pasada por línea de comando, o emite un mensaje de ayuda, cuando el número de parámetros es incorrecto.
     * @param args Parámetros pasados en la linea de comando.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args.length == 1) {
                url_linea_comando_texto = args[0];
            } else {
                 System.out.println(Lala.class.getName() 
                         + " "  //NOI18N
                         + java.util.ResourceBundle.getBundle("in/javafx/webtec/gui/in").getString("URL QUE PROCESAR. ")
                         + k_url_por_defecto_texto 
                         + " "  //NOI18N
                         + java.util.ResourceBundle.getBundle("in/javafx/webtec/gui/in").getString("(POR DEFECTO)"));
            }
        }
        launch(Lala.class, args);
    }
    
}
