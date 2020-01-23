/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import static innui.webtec.Webtec_controlador.dejar_mapa_minimo;
import static innui.webtec.gui.menu_aplicaciones.k_mapa_extras;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;

/**
 * Clase de ejemplo que genera el código HTML necesario para ofrecer un cambio de idioma en una aplicación
 */
public class idiomas extends A_ejecutores {

    /**
     *
     */
    public static String k_accion_de_cambio_de_idioma = "https://innui/webtec/lala/procesar_idiomas";  //NOI18N

    /**
     *
     */
    public static String k_menu_in_idioma = "innui_webtec_lala_idioma"; //NOI18N
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
        Map<String, Object> objects_mapa_local = null;
        String innui_webtec_gui_autoformularios = ""; //NOI18N
        Locale locale;
        String lenguaje;
        try {
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            objects_mapa_local = new LinkedHashMap();
            objects_mapa_local.putAll(objects_mapa);
            ret = dejar_mapa_minimo(objects_mapa_local, error);
            if (ret) {
                locale = Locale.getDefault();
                lenguaje = locale.getLanguage();
                objects_mapa_local.put(k_menu_in_idioma, lenguaje);
                objects_mapa_local.put(k_mapa_autoformularios_accion, k_accion_de_cambio_de_idioma);
                ret = autoformulario.ejecutar(objects_mapa_local, error);
                if (ret) { 
                    innui_webtec_gui_autoformularios = (String) objects_mapa_local.get(k_mapa_autoformularios); //NOI18N
                    objects_mapa.put(k_mapa_extras, innui_webtec_gui_autoformularios); 
                } else {
                    objects_mapa.put(k_mapa_extras, error[0]);
                }
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_extras, error[0]); //NOI18N
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.idiomas. " + error[0];
            ret = false;
        }
        return ret;
    }
    
}
