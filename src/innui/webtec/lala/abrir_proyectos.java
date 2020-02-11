/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import java.util.Map;
import innui.webtec.gui.configuraciones;
import static innui.webtec.lala.crear_proyectos.k_carpeta_java;
import static innui.webtec.lala.crear_proyectos.k_carpeta_php;
import static innui.webtec.lala.crear_proyectos.k_carpeta_resultado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.paginas_principales.k_ruta_paginas_principales;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import java.io.File;
import java.net.URL;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class abrir_proyectos extends A_ejecutores {
    public static String k_nombre_ruta_seleccionada = "ruta_seleccionada";
    public static String k_ruta_abrir_proyectos = "/lala/abrir_proyectos";    
    public static String k_mapa_ruta_seleccionada = "ruta_seleccionada"; //NOI18N

    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String boton_enviar_pulsado;
        String archivo_seleccionado;
        String presentar;
        presentar = (String) objects_mapa.get(k_mapa_autoformularios_presentar);
        boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
        archivo_seleccionado = (String) objects_mapa.get(k_mapa_ruta_seleccionada);
        if ((boton_enviar_pulsado == null
                && archivo_seleccionado == null)
                || presentar != null) {
            ret = presentar(objects_mapa, error);
        } else {
            ret = procesar(objects_mapa, error);
        }
        return ret;
    }
    
    public boolean presentar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios autoformulario;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            ret = autoformulario.ejecutar(objects_mapa, error);
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en presentar.abrir_proyectos. " + error[0];
            ret = false;
        }
        return ret;
    }

    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String boton_enviar_pulsado;
        String nombre_proyecto;
        String ruta_seleccionada;
        String existente_carpeta = "";
        String nombre_proyecto_min;
        File file;        
        Map<String, String> configuraciones_mapa = null;
        URL url;
        try {
            boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
            if (boton_enviar_pulsado !=  null) {
                ruta_seleccionada = (String) objects_mapa.get(k_configuraciones_ruta_seleccionada);
                file = new File(ruta_seleccionada);
                error[0] = "";
                if (file.isDirectory() == false) {
                    error[0] = error[0] + "Debe seleccionar una carpeta. ";
                }
                nombre_proyecto  = file.getName();
                nombre_proyecto_min = nombre_proyecto.trim();
                nombre_proyecto_min = nombre_proyecto_min.toLowerCase();
                if (nombre_proyecto_min.equals(nombre_proyecto) == false) {
                    error[0] = error[0] + "Nombre de proyecto no es solo minúsculas. ";
                }
                if (nombre_proyecto_min.endsWith("s")) {
                    error[0] = error[0] + "Nombre de proyecto es plural. ";
                }
                configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
                if (ret) {
                    configuraciones_mapa.put(k_configuraciones_ruta_seleccionada, ruta_seleccionada);
                    ret = configuraciones.escribir(configuraciones_mapa, objects_mapa, error);
                }
                if (ret) {
                    existente_carpeta = aumentar_ruta(ruta_seleccionada, k_carpeta_resultado, error);
                    ret = (existente_carpeta != null);
                }
                if (ret) {
                    file = new File(existente_carpeta);
                    if (file.exists() == false) {
                        error[0] = error[0] + "Falta la carpeta: " + k_carpeta_resultado + " ";
                    }
                    existente_carpeta = aumentar_ruta(existente_carpeta, k_carpeta_java, error);
                    ret = (existente_carpeta != null);
                }
                if (ret) {
                    file = new File(existente_carpeta);
                    if (file.exists() == false) {
                        error[0] = error[0] + "Falta la carpeta: " + k_carpeta_resultado + File.separator + k_carpeta_java + " ";
                    }
                    existente_carpeta = aumentar_ruta(existente_carpeta, "..", error);
                    existente_carpeta = aumentar_ruta(existente_carpeta, k_carpeta_php, error);
                    ret = (existente_carpeta != null);
                }
                if (ret) {
                    file = new File(existente_carpeta);
                    if (file.exists() == false) {
                        error[0] = error[0] + "Falta la carpeta: " + k_carpeta_resultado + File.separator + k_carpeta_php + " ";
                    }
                    ret = error[0].trim().isEmpty();
                }
            }
            if (ret && boton_enviar_pulsado != null) {
                objects_mapa.put(k_mapa_editar_archivos_error, "");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_paginas_principales, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (ret == false || boton_enviar_pulsado == null) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_abrir_proyectos, k_protocolo_por_defecto, error);
                if (ret == false) {
                    objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
                }
                objects_mapa.put(k_mapa_autoformularios_presentar, "");
                objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
                ret = poner_redireccion(contexto, url, true, null, error);
            } 
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.abrir_proyectos. " + error [0];
            ret = false;
        }
        return ret;
    }
    
}
