/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import java.util.Map;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class abrir_proyectos extends A_ejecutores {
    public static String k_nombre_ruta_seleccionada = "ruta_seleccionada";
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
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                if (objects_mapa.containsKey(k_mapa_autoformularios_error) == false) { //NOI18N
                    objects_mapa.put(k_mapa_autoformularios_error, ""); //NOI18N
                }
                autoformulario = new autoformularios();
                autoformulario.configurar(contexto);
                ret = autoformulario.ejecutar(objects_mapa, error);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.abrir_proyectos";
            ret = false;
        }
        return ret;
    }

    
}
