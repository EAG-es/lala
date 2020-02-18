/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static innui.archivos.Rutas.aumentar_ruta;
import innui.webtec.A_ejecutores;
import static innui.webtec.chunk.Procesamientos.k_infijo_in_;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import innui.webtec.gui.configuraciones;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.procesar_crear_archivos.k_extension_lala;
import java.io.File;
import java.util.Map;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_crear_proyectos extends A_ejecutores {
    public static String k_configuraciones_ruta_seleccionada = "ruta_seleccionada";
    public static String k_configuraciones_archivo_seleccionado = "archivo_seleccionado";
    public static String k_nombre_nombre_proyecto = "nombre_proyecto";    
    public static String k_mapa_procesar_crear_proyectos_errores = "innui_webtec_lala_procesar_crear_proyectos_errores"; //NOI18N
    public static String k_mapa_procesar_crear_proyectos = "innui_webtec_lala_procesar_crear_proyectos"; //NOI18N
    public static String k_carpeta_lala = "lala"; //NOI18N
    public static String k_carpeta_resultado = "resultados"; //NOI18N
    public static String k_carpeta_java = "java"; //NOI18N
    public static String k_carpeta_php = "php"; //NOI18N
    public static String k_carpeta_innui = "innui"; //NOI18N
    public static String k_carpeta_importable = "importable"; //NOI18N
    public static String k_prefijo_semiconstantes = "semiconstantes"; //NOI18N
    public static String k_prefijo_atributos = "atributos_"; //NOI18N
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
        String innui_webtec_gui_autoformularios_errores = "";
        String boton_enviar_pulsado = null;
        String nombre_proyecto = null;
        String ruta_seleccionada = null;
        String nueva_carpeta = "";
        String nuevo_archivo = "";
        String nombre_proyecto_min;
        File file;        
        Map<String, String> configuraciones_mapa = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ruta_seleccionada = (String) objects_mapa.get(k_configuraciones_ruta_seleccionada);
                file = new File(ruta_seleccionada);
                error[0] = "";
                if (file.isDirectory() == false) {
                    error[0] = error[0] + "Debe seleccionar una carpeta. ";
                    ret = false;
                }
                nombre_proyecto  = (String) objects_mapa.get(k_nombre_nombre_proyecto);
                if (nombre_proyecto.isEmpty()) {
                    error[0] = error[0] + "Nombre de proyecto no válido. ";
                    ret = false;                        
                } else {
                    nombre_proyecto_min = nombre_proyecto.trim();
                    nombre_proyecto_min = nombre_proyecto_min.toLowerCase();
                    if (nombre_proyecto_min.equals(nombre_proyecto) == false) {
                        error[0] = error[0] + "Nombre de proyecto no es solo minúsculas. ";
                        ret = false;
                    }
                    if (nombre_proyecto_min.endsWith("s")) {
                        error[0] = error[0] + "Nombre de proyecto es plural. ";
                        ret = false;
                    }
                }
            }
            if (ret) {
                boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
                if (boton_enviar_pulsado !=  null) {
                    configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
                    ret = (configuraciones_mapa != null);
                    if (ret) {
                        nueva_carpeta = aumentar_ruta(ruta_seleccionada, nombre_proyecto, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, k_carpeta_lala, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        configuraciones_mapa.put(k_configuraciones_ruta_seleccionada, nueva_carpeta);
                        configuraciones_mapa.put(k_configuraciones_archivo_seleccionado, "");
                        ret = configuraciones.escribir(configuraciones_mapa, objects_mapa, error);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, k_carpeta_innui, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, k_carpeta_importable, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        nuevo_archivo = aumentar_ruta(nueva_carpeta, k_prefijo_semiconstantes + k_infijo_in_ + "es" + "_" +  nombre_proyecto + k_extension_lala, error);
                        ret = (nuevo_archivo != null);
                    }
                    if (ret) {
                        file = new File(nuevo_archivo);
                        if (file.exists() == false) {
                            file.createNewFile();
                        }
                        nuevo_archivo = aumentar_ruta(nueva_carpeta, k_prefijo_semiconstantes + k_infijo_in_ + "en" + "_" + nombre_proyecto + k_extension_lala, error);
                        ret = (nuevo_archivo != null);
                    }
                    if (ret) {
                        file = new File(nuevo_archivo);
                        if (file.exists() == false) {
                            file.createNewFile();
                        }
                        nuevo_archivo = aumentar_ruta(nueva_carpeta, k_prefijo_atributos + nombre_proyecto + k_extension_lala, error);
                        ret = (nuevo_archivo != null);
                    }
                    if (ret) {
                        file = new File(nuevo_archivo);
                        if (file.exists() == false) {
                            file.createNewFile();
                        }
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, "..", error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, "..", error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, "..", error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, k_carpeta_resultado, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, k_carpeta_java, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, "..", error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        nueva_carpeta = aumentar_ruta(nueva_carpeta, k_carpeta_php, error);
                        ret = (nueva_carpeta != null);
                    }
                    if (ret) {
                        file = new File(nueva_carpeta);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                    }
                }
            }
            objects_mapa.remove(k_mapa_procesar_crear_proyectos_errores);
            if (ret == false || boton_enviar_pulsado == null) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                autoformulario_error = new autoformularios_errores();
                autoformulario_error.configurar(contexto);
                ret = autoformulario_error.ejecutar(objects_mapa, error);
                innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
                objects_mapa.put(k_mapa_procesar_crear_proyectos_errores, innui_webtec_gui_autoformularios_errores);
            } else {
                ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
                if (ret == false) {
                    objects_mapa.put(k_mapa_procesar_crear_proyectos, error[0]);
                }
            } 
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.procesar_crear_proyectos. ";
            ret = false;
        }
        return ret;
    }

}
