/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import innui.webtec.gui.configuraciones;
import static innui.webtec.gui.ejemplo.ejemplo_pagina_principal.poner_cabecera_en_mapa;
import java.util.Map;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class configuradores extends A_ejecutores {

    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios autoformulario;
        try {
            ret = configuraciones.leer(objects_mapa, error);
            if (ret) {
                ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            }
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
            error[0] = "Error en ejecutar.configuradores. " + error[0];
            ret = false;
        }
        return ret;    }
    
}
