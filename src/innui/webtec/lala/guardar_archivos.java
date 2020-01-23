/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.archivos.Utf8;
import innui.contextos.textos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.k_decoracion_fin;
import static innui.html.lala.Decoraciones.k_decoracion_inicio;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import innui.webtec.A_ejecutores;
import innui.webtec.Webtec_controlador;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.paginas_principales.leer_configuraciones;
import static innui.webtec.lala.procesar_abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.procesar_abrir_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_archivo_seleccionado;
import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class guardar_archivos extends A_ejecutores {
    public static String k_mapa_guardar_archivos = "innui_webtec_lala_guardar_archivos"; //NOI18N
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        File file = null;
        String contenido = "";
        int pos_inicio = 0;
        int pos_fin = 0;
        textos texto_resultante = null;
        textos error_texto = new textos("");
        String archivo_seleccionado;
        URL url;
        Map<String, String> configuraciones_mapa = null;
        String linea_mapa;
        try {
            contenido = contexto.leer(k_contexto_archivo_abierto).dar();
            ret = (contenido != null);
            if (ret) {
                pos_inicio = contenido.indexOf(k_decoracion_inicio);
                if (pos_inicio < 0) {
                    ret = false;
                    error[0] = "No se ha encontrado el inicio del texto que guardar. ";
                } else {
                    pos_inicio = pos_inicio + k_decoracion_inicio.length();
                }
            }
            if (ret) {
                pos_fin = contenido.indexOf(k_decoracion_fin);
                if (pos_fin < 0) {
                    ret = false;
                    error[0] = "No se ha encontrado el final del texto que guardar. ";
                }
            }
            if (ret) {
                contenido = contenido.substring(pos_inicio, pos_fin);
                texto_resultante = new textos();
                ret = Decoraciones.desdecorar(contenido, texto_resultante, error_texto);
            }
            if (ret) {
                configuraciones_mapa = leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
            }
            if (ret) {
                archivo_seleccionado = (String) configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
                file = new File(archivo_seleccionado);
                ret = (file != null);
            }
            if (ret) {
                ret = Utf8.escribir(file, texto_resultante.dar(), error);
            }
            if (ret) {
                linea_mapa = (String) objects_mapa.get(k_mapa_linea_numero);
                ret = poner_url_ref_a_contenido(k_prefijo_ancla_linea + linea_mapa, objects_mapa, error);
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_guardar_archivos, error[0]);
            } else {
                objects_mapa.put(k_mapa_editar_archivos_error, "Archivo guardado. ");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = Webtec_controlador.poner_redireccion(contexto, url, true, null, error);
            }
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
    
}
