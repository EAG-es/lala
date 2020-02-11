/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import java.net.URL;
import java.util.Map;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class insertar_subacciones extends A_ejecutores { 
    public static String k_insertar_nivel_acciones = "/lala/insertar_nivel_acciones";
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios autoformulario;
        URL url_accion = null;
        String previo_texto = null;
        String posterior_texto = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            previo_texto = contexto.leer(k_contexto_previo).dar();
            posterior_texto = contexto.leer(k_contexto_posterior).dar();
            objects_mapa.put(k_mapa_texto_previo, previo_texto); 
            objects_mapa.put(k_mapa_texto_posterior, posterior_texto);                 
            url_accion = Urls.completar_URL(k_prefijo_url + k_insertar_nivel_acciones, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_accion, url_accion.toExternalForm());
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            ret = autoformulario.ejecutar(objects_mapa, error);
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.insertar_subacciones. " + error[0];
            ret = false;
        }
        return ret;
    }

}
