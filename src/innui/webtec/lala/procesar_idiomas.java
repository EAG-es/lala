/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import innui.webtec.A_ejecutores;
import innui.webtec.String_webtec_controlador;
import static innui.webtec.Webtec_controlador.leer_penultimo_historial;
import static innui.webtec.Webtec_controlador.quitar_ultimo_historial;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import static innui.webtec.Webtec_controlador.dejar_mapa_minimo;
import static innui.webtec.Webtec_controlador.leer_tam_historial;
import static innui.webtec.Webtec_controlador.reducir_historial;
import static innui.webtec.lala.idiomas.k_menu_in_idioma;

/**
 * Ejemplo de clase qeu atiende a la url de acción de solicitud de un cambio de idioma.
 */
public class procesar_idiomas extends A_ejecutores {

    /**
     *
     */
    public static String k_lala_procesar_idiomas = "innui_webtec_lala_procesar_idiomas"; //NOI18N
    
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */     
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String_webtec_controlador string_webtec_controlador = null;
        String idioma = "";
        URL url_plantilla_previa = null;      
        Map<String, Object> objects_mapa_local = null;
        Locale nuevo_locale;
        String previa_url_texto = ""; //NOI18N
        int historial_tam = 0;
        try {
            if (ret) {
                previa_url_texto = leer_penultimo_historial(contexto, error);
                ret = (previa_url_texto != null);
            }
            if (ret) {
                ret = quitar_ultimo_historial(contexto, error);// Interfiere con la repetición de la penúltima url llamada (a veces consulta con la antepenúltima)
            }
            if (ret) {
                idioma = (String) objects_mapa.get(k_menu_in_idioma);
                nuevo_locale = new Locale(idioma);
                Locale.setDefault(nuevo_locale);
                url_plantilla_previa = new URL(previa_url_texto);
                string_webtec_controlador = new String_webtec_controlador();
                objects_mapa_local = new LinkedHashMap();
                objects_mapa_local.putAll(objects_mapa);                               
                ret = dejar_mapa_minimo(objects_mapa_local, error);
            }                
            if (ret) {
                ret = string_webtec_controlador.configurar(contexto, true, error);
            }                
            if (ret) {
                historial_tam = leer_tam_historial(contexto, error);
                ret = string_webtec_controlador.procesar_url(url_plantilla_previa, objects_mapa_local, error);
            }
            if (ret) {
                ret = reducir_historial(contexto, historial_tam, error);
            }
            if (ret) {
                objects_mapa.put(k_lala_procesar_idiomas, string_webtec_controlador.contenido);
            } else {
                objects_mapa.put(k_lala_procesar_idiomas, error[0]);
            }               
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error al ejecutar.procesar_idiomas. " + error[0];
            ret = false;
        }
        return ret;
    }

}
