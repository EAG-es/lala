/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.contextos.contextos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import innui.webtec.gui.configuraciones;
import innui.webtec.gui.menu_aplicaciones;
import innui.webtec.gui.menu_contextuales;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.procesar_abrir_archivos.k_ruta_editar_archivos;
import java.util.LinkedHashMap;
import java.util.Map;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_ruta_seleccionada;
import java.net.URL;

/**
 * Clase de ejemplo de la página principal de una aplicación, solo con un menú y la traducción a Inglés
 */
public class paginas_principales extends A_ejecutores {
    public static String k_nombre_configuracion = "configuracion";
    public static String k_nombre_paginas_principales = "innui_webtec_lala_paginas_principales";
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        Map<String, String> configuraciones_mapa = null;
        String ruta_seleccionada;
        String archivo_seleccionado;
        URL url;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                configuraciones_mapa = leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
            }
            if (ret) {
                ruta_seleccionada = (String) configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                if (ruta_seleccionada == null || ruta_seleccionada.isEmpty()) {
                    objects_mapa.put(k_nombre_paginas_principales, "No hay proyecto abierto. ");
                } else {
                    archivo_seleccionado = (String) configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
                    if (archivo_seleccionado == null || archivo_seleccionado.isEmpty()) {
                        objects_mapa.put(k_nombre_paginas_principales, "No hay archivo abierto. ");
                    } else {
                        objects_mapa.put(k_mapa_editar_archivos_error, "");
                        url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                        ret = poner_redireccion(contexto, url, true, null, error);
                    }
                }
            }
            if (ret == false) {
                objects_mapa.put(k_nombre_paginas_principales, error[0]);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.lala_pagina_principal. " + error[0];
            ret = false;
        }
        return ret;
    }
    /**
     * Método estático con el código necesario para cargar en el mapa los datos del menú de aplicaciones, y del selector de idioma
     * @param contexto Contexto de la aplicación
     * @param objects_mapa Mapa con los datos que utilizar, más los datos resultantes.
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
    public static boolean poner_cabecera_en_mapa(contextos contexto, Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        menu_aplicaciones menu_aplicacion;
        idiomas idioma;
        menu_aplicacion = new menu_aplicaciones();
        menu_aplicacion.configurar(contexto);
        ret = menu_aplicacion.ejecutar(objects_mapa, error);
        if (ret) {
            idioma = new idiomas();
            idioma.configurar(contexto);
            ret = idioma.ejecutar(objects_mapa, error);
        }
        return ret;
    }
    /**
     * Método estático con el código necesario para cargar en el mapa los datos del menú de aplicaciones, y del selector de idioma
     * @param contexto Contexto de la aplicación
     * @param objects_mapa Mapa con los datos que utilizar, más los datos resultantes.
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */
    public static boolean poner_menu_contextual_en_mapa(contextos contexto, Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        menu_contextuales menu_contextual;
        menu_contextual = new menu_contextuales();
        menu_contextual.configurar(contexto);
        ret = menu_contextual.ejecutar(objects_mapa, error);
        return ret;
    }
    /**
     * Obtener la configuración de la aplicación
     * @param contexto Información del contexto de la aplicación
     * @param objects_mapa Mapa con los datos necesarios para leer las configuraciones
     * @param error En la posición 0, mensaje de error; si lo hay
     * @return Retorna un mapa con los datos de configuración
     */
    public static Map<String, String> leer_configuraciones(contextos contexto, Map<String, Object> objects_mapa, String[] error) {
        Map<String, String> configuraciones_mapa;
        boolean ret = true;
        configuraciones_mapa = contexto.leer(k_nombre_configuracion).dar();
        if (configuraciones_mapa == null) {
            configuraciones_mapa = new LinkedHashMap();
            ret = configuraciones.leer(objects_mapa, configuraciones_mapa, error);
            if (ret) {
                contexto.superponer(k_nombre_configuracion, configuraciones_mapa);
            } else {
                configuraciones_mapa = null;
            }
        }
        return configuraciones_mapa;
    }
}
