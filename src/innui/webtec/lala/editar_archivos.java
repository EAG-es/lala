/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import innui.webtec.A_ejecutores;
import static innui.webtec.lala.abrir_archivos.abrir_archivo;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import java.io.File;
import java.util.Map;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class editar_archivos extends A_ejecutores {
    public static String k_mapa_editar_archivos_nombre = "innui_webtec_lala_editar_archivos_nombre";
    public static String k_mapa_editar_archivos_error = "innui_webtec_lala_editar_archivos_error";
    public static String k_mapa_editar_archivos = "innui_webtec_lala_editar_archivos";
    public static String k_ruta_editar_archivos = "/lala/editar_archivos";    
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
        String archivo_seleccionado = null;
        Map<String, String> configuraciones_mapa = null;
        File file = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            if (ret) {
                configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
                if (ret) {
                    archivo_seleccionado = configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
                    texto = contexto.leer(k_contexto_archivo_abierto).dar();
                    if (texto == null) {
                        ret = abrir_archivo(contexto, archivo_seleccionado, error);
                        if (ret) {
                            texto = contexto.leer(k_contexto_archivo_abierto).dar();
                        }
                    }
                }
                if (ret) {
                    objects_mapa.put(k_mapa_editar_archivos, texto); //NOI18N
                    file = new File(archivo_seleccionado);
                    objects_mapa.put(k_mapa_editar_archivos_nombre, file.getName()); //NOI18N
                }
                if (ret == false) {
                    objects_mapa.put(k_mapa_editar_archivos, error[0]); //NOI18N
                }
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.editar_archivos. " + error [0];
            ret = false;
        }
        return ret;
    }

}
