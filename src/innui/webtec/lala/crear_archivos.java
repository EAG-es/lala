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
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_ruta_seleccionada;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class crear_archivos extends A_ejecutores {
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
        String ruta_seleccionada = null;
        Map<String, String> configuraciones_mapa = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ruta_seleccionada = (String) objects_mapa.get(procesar_crear_archivos.k_nombre_ruta_seleccionada);
                if (ruta_seleccionada == null) {
                    configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
                    ret = (configuraciones_mapa != null);
                    if (ret) {
                        ruta_seleccionada = configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                        if (ruta_seleccionada == null) {
                            error[0] = "No se ha especificado la ruta del proyecto. ";
                            ret = false;
                        }
                    }
                }
            }
            if (ret) {
                if (objects_mapa.containsKey(k_mapa_autoformularios_error) == false) { //NOI18N
                    objects_mapa.put(k_mapa_autoformularios_error, ""); //NOI18N
                }
                autoformulario = new autoformularios();
                autoformulario.configurar(contexto);
                objects_mapa.put(procesar_crear_archivos.k_nombre_ruta_seleccionada, ruta_seleccionada);
                ret = autoformulario.ejecutar(objects_mapa, error);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.crear_archivos" + error[0];
            ret = false;
        }
        return ret;
    }

    
}
