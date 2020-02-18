/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import static innui.webtec.gui.configuraciones.escribir;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_configuradores extends A_ejecutores {

    /**
     *
     */
    public static String k_mapa_ejemplo_procesar_configuradores_errores = "innui_webtec_lala_configuradores_errores"; //NOI18N

    /**
     *
     */
    public static String k_prefijo_configuraciones = "innui_webtec_lala_configuraciones_";
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios_errores autoformulario_error;
        String innui_webtec_gui_autoformularios_errores;
        Map<String, String> strings_mapa;
        String clave;
        String valor;
        try {
            strings_mapa = new LinkedHashMap();
            for (Entry<String, Object> entry: objects_mapa.entrySet()) {
                clave = entry.getKey();
                if (clave.startsWith(k_prefijo_configuraciones)) {
                    valor = (String) entry.getValue();
                    strings_mapa.put(clave, valor);
                }
            }
            ret = escribir(strings_mapa, objects_mapa, error);
            if (ret == false) {        
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
            } else {
                objects_mapa.put(k_mapa_autoformularios_error, "Configuración guardada correctamente. ");
            }
            autoformulario_error = new autoformularios_errores();
            autoformulario_error.configurar(contexto);
            ret = autoformulario_error.ejecutar(objects_mapa, error);
            if (ret == false) {
                innui_webtec_gui_autoformularios_errores = error[0];
            } else {
                innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
            }
            objects_mapa.put(k_mapa_ejemplo_procesar_configuradores_errores, innui_webtec_gui_autoformularios_errores); //NOI18N
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.procesar_configuradores. " + error[0];
            ret = false;
        }
        return ret;
    }

}
