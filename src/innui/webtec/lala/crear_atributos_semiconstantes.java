/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.contextos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import static innui.webtec.chunk.Procesamientos.k_infijo_in_;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import static innui.webtec.lala.procesar_crear_archivos.k_extension_lala;
import static innui.webtec.lala.procesar_crear_proyectos.k_carpeta_innui;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_ruta_seleccionada;
import java.io.File;
import java.net.URL;
import java.util.Map;
import static innui.webtec.lala.procesar_crear_proyectos.k_carpeta_importable;
import static innui.webtec.lala.procesar_crear_proyectos.k_prefijo_atributos;
import static innui.webtec.lala.procesar_crear_proyectos.k_prefijo_semiconstantes;
import java.util.Locale;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class crear_atributos_semiconstantes extends A_ejecutores {
    public static String k_procesar_crear_atributos_semiconstantes = "/lala/procesar_crear_atributos_semiconstantes";
    public static String k_nombre_crear_atributos_semiconstantes_atributos = "innui_webtec_lala_crear_atributos_semiconstantes_datos_previos";
    public static String k_nombre_crear_semiconstantes = "innui_webtec_lala_crear_semiconstantes";
    public static String k_nombre_crear_atributos = "innui_webtec_lala_crear_atributos";
    public static String k_contexto_importables_atributos = "contexto_lala_importables_atributos";
    public static String k_contexto_importables_atributos_ruta = "contexto_lala_importables_atributos_ruta";
    public static String k_contexto_importables_semiconstantes = "contexto_lala_importables_semiconstantes";
    public static String k_contexto_importables_semiconstantes_ruta = "contexto_lala_importables_semiconstantes_ruta";

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
        URL url_accion = null;
        String previo_texto = null;
        String posterior_texto = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            if (ret) {
                ret = leer_rutas_importables(contexto, objects_mapa, error);
            }
            if (ret) {
                previo_texto = contexto.leer(k_contexto_previo).dar();
                posterior_texto = contexto.leer(k_contexto_posterior).dar();
                objects_mapa.put(k_mapa_texto_previo, previo_texto); 
                objects_mapa.put(k_mapa_texto_posterior, posterior_texto);                 
                url_accion = Urls.completar_URL(k_prefijo_url + k_procesar_crear_atributos_semiconstantes, k_protocolo_por_defecto, error);
                objects_mapa.put(k_mapa_autoformularios_accion, url_accion.toExternalForm());
                if (objects_mapa.containsKey(k_mapa_autoformularios_error) == false) { //NOI18N
                    objects_mapa.put(k_mapa_autoformularios_error, ""); //NOI18N
                }
                autoformulario = new autoformularios();
                autoformulario.configurar(contexto);
                ret = autoformulario.ejecutar(objects_mapa, error);
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.crear_atributos_semiconstantes. " + error[0];
            ret = false;
        }
        return ret;
    }
        
    public static boolean leer_rutas_importables(contextos contexto, Map<String, Object> objects_mapa, String [] error) {
        boolean ret = true;
        String ruta_seleccionada = null;
        Map<String, String> configuraciones_mapa = null;
        String nombre_proyecto = null;
        File file = null;
        String ruta_importable = null;
        String texto = null;
        Locale locale;
        String idioma;
        configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
        ret = (configuraciones_mapa != null);
        if (ret) {
            ruta_seleccionada = configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            file = new File(ruta_seleccionada);
            file = file.getParentFile();
            nombre_proyecto = file.getName();
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_carpeta_innui, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_carpeta_importable, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            ruta_importable = aumentar_ruta(ruta_seleccionada, k_prefijo_atributos + nombre_proyecto + k_extension_lala, error);
            ret = (ruta_importable != null);
        }
        if (ret) {
            file = new File(ruta_importable);
            if (file.exists() == false) {
                ret = false;
                error[0] = "No se encuentra el archivo: " + ruta_importable;
            }
        }
        if (ret) {
            texto = Utf8.leer(file, error);
            ret = (texto != null);
        }
        if (ret) {
            objects_mapa.put(k_nombre_crear_atributos, texto);
            contexto.fondear_con_datos(k_contexto_importables_atributos, texto);
            contexto.fondear_con_datos(k_contexto_importables_atributos_ruta, ruta_importable);
        }
        if (ret) {
            locale = Locale.getDefault();
            idioma = locale.getLanguage();
            ruta_importable = aumentar_ruta(ruta_seleccionada, k_prefijo_semiconstantes + k_infijo_in_ + idioma + "_" + nombre_proyecto + k_extension_lala, error);
            ret = (ruta_importable != null);
        }
        if (ret) {
            file = new File(ruta_importable);
            if (file.exists() == false) {
                ret = false;
                error[0] = "No se encuentra el archivo: " + ruta_importable;
            }
        }
        if (ret) {
            texto = Utf8.leer(file, error);
            ret = (texto != null);
        }
        if (ret) {
            objects_mapa.put(k_nombre_crear_semiconstantes, texto);
            contexto.fondear_con_datos(k_contexto_importables_semiconstantes, texto);
            contexto.fondear_con_datos(k_contexto_importables_semiconstantes_ruta, ruta_importable);
        }
        return ret;
    }

}
