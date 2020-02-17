/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static ingui.javafx.webtec.lala.Lala.k_deshacer_con_archivo;
import innui.contextos.contextos;
import innui.edicion.Deshacer_con_archivos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import innui.webtec.Webtec_controlador;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import java.net.URL;
import java.util.Map;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class deshacer_edicion extends A_ejecutores {
    public static String k_mapa_deshacer_boton = "deshacer_boton"; //NOI18N
    public static String k_mapa_rehacer_boton = "rehacer_boton"; //NOI18N
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String texto = null;
        URL url;
        try {
            if (objects_mapa.get(k_mapa_deshacer_boton) != null) {
                texto = deshacer_cambio(contexto, error);
            } else if (objects_mapa.get(k_mapa_rehacer_boton) != null) {
                texto = rehacer_cambio(contexto, error);
            } else {
                ret = false;
                error[0] = "Operación indefinida. ";
            }
            ret = (texto != null);
            if (ret) {
                ret = contexto.modificar(k_contexto_archivo_abierto, texto).es();
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_editar_archivos_error, error[0]); 
            }
            url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
            ret = Webtec_controlador.poner_redireccion(contexto, url, true, null, error);
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.guardar_archivos. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static String deshacer_cambio(contextos contexto, String [] error) {
        boolean ret = true;
        String retorno = null;
        Deshacer_con_archivos deshacer_con_archivo = null;
        deshacer_con_archivo = contexto.leer(k_deshacer_con_archivo).dar();
        ret = (deshacer_con_archivo != null);
        if (ret) {
            retorno = deshacer_con_archivo.deshacer_cambio(error);
        } else {
            ret = false;
            error [0] = "No se ha encontrado el objeto que gestiona las operaciones deshacer-rehacer. ";
            retorno = null;
        }
        return retorno;
    }

    public static String rehacer_cambio(contextos contexto, String [] error) {
        boolean ret = true;
        String retorno = null;
        Deshacer_con_archivos deshacer_con_archivo = null;
        deshacer_con_archivo = contexto.leer(k_deshacer_con_archivo).dar();
        ret = (deshacer_con_archivo != null);
        if (ret) {
            retorno = deshacer_con_archivo.rehacer_cambio(error);
        } else {
            ret = false;
            error [0] = "No se ha encontrado el objeto que gestiona las operaciones deshacer-rehacer. ";
            retorno = null;
        }
        return retorno;
    }

}
