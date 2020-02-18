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
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import innui.webtec.gui.configuraciones;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.procesar_abrir_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.procesar_abrir_archivos.validar_nombre_archivo;
import java.io.File;
import java.util.Map;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_ruta_seleccionada;
import java.net.URL;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_crear_archivos extends A_ejecutores {
    public static String k_nombre_ruta_seleccionada = "ruta_seleccionada"; //NOI18N
    public static String k_nombre_crear_carpeta = "crear_carpeta"; //NOI18N
    public static String k_nombre_crear_carpeta_boton = "crear_carpeta_boton"; //NOI18N
    public static String k_nombre_nombre_archivo = "nombre_archivo"; //NOI18N
    public static String k_mapa_procesar_crear_archivos_errores = "innui_webtec_lala_procesar_crear_archivos_errores"; //NOI18N
    public static String k_mapa_procesar_crear_archivos = "innui_webtec_lala_procesar_crear_archivos"; //NOI18N
    public static String k_extension_lala = ".la"; //NOI18N
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
        String nombre_archivo = "";
        String ruta_seleccionada;
        String configuracion_ruta_seleccionada;
        String nuevo_archivo = "";
        String crear_carpeta_boton;
        String [] error_local = { "" };
        File file;        
        Map<String, String> configuraciones_mapa = null;
        URL url;
        try {
            configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
            ret = (configuraciones_mapa != null);
            if (ret) {
                objects_mapa.remove(k_mapa_procesar_crear_archivos_errores);
                crear_carpeta_boton = (String) objects_mapa.get(k_nombre_crear_carpeta_boton);
                if (crear_carpeta_boton !=  null) {
                    configuracion_ruta_seleccionada = (String) configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                    ruta_seleccionada = (String) objects_mapa.get(k_nombre_ruta_seleccionada);
                    nombre_archivo = (String) objects_mapa.get(k_nombre_crear_carpeta);
                    ret = validar_nombre_carpeta(configuracion_ruta_seleccionada, ruta_seleccionada, nombre_archivo, error);
                    if (ret) {
                        nombre_archivo = aumentar_ruta(ruta_seleccionada, nombre_archivo, error);
                        ret = (nombre_archivo != null);
                    }
                    if (ret) {
                        file = new File(nombre_archivo);
                        if (file.exists() == false) {
                            file.mkdir();
                        }
                        objects_mapa.put(k_nombre_ruta_seleccionada, nombre_archivo);
                    }
                } else {
                    boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
                    if (boton_enviar_pulsado !=  null) {
                        ruta_seleccionada = (String) objects_mapa.get(k_nombre_ruta_seleccionada);
                        file = new File(ruta_seleccionada);
                        error[0] = "";
                        if (file.isDirectory() == false) {
                            error[0] = error[0] + "Debe seleccionar una carpeta. ";
                            ret = false;
                        }
                        nombre_archivo  = (String) objects_mapa.get(k_nombre_nombre_archivo);
                        if (ret) {
                            nuevo_archivo = aumentar_ruta(ruta_seleccionada, nombre_archivo + k_extension_lala, error);
                            ret = (nuevo_archivo != null);
                        }
                        if (ret) {
                            file = new File(nuevo_archivo);
                            ret = ret && validar_nombre_archivo(file, error_local);
                            error[0] = error[0] + error_local[0];
                        }
                        if (ret) {
                            configuraciones_mapa.put(k_configuraciones_archivo_seleccionado, nuevo_archivo);
                            ret = configuraciones.escribir(configuraciones_mapa, objects_mapa, error);
                        }
                        if (ret) {
                            file = new File(nuevo_archivo);
                            if (file.exists() == false) {
                                file.createNewFile();
                            } else {
                                error[0] = "Ya existe un arhivo con ese nombre. ";
                                ret = false;
                            }
                        }
                    }
                }
            }
            if (ret == false || boton_enviar_pulsado == null) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                autoformulario_error = new autoformularios_errores();
                autoformulario_error.configurar(contexto);
                ret = autoformulario_error.ejecutar(objects_mapa, error);
                innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
                objects_mapa.put(k_mapa_procesar_crear_archivos_errores, innui_webtec_gui_autoformularios_errores);
            } else {
                objects_mapa.put(k_mapa_editar_archivos_error, "");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
                if (ret == false) {
                    objects_mapa.put(k_mapa_procesar_crear_archivos, error[0]);
                }
            } 
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.procesar_crear_archivos. ";
            ret = false;
        }
        return ret;
    }

    public static boolean validar_nombre_carpeta(String ruta_contenedora, String ruta_carpeta, String nombre_carpeta, String [] error) {
        boolean ret = true;
        String nombre_carpeta_min;
        if (ruta_carpeta.startsWith(ruta_contenedora) == false) {
            error[0] = "La carpeta debe estar dentro del proyecto. ";
            ret = false;
        }
        if (ret) {
            if (nombre_carpeta.isEmpty()) {
                error[0] = error[0] + "Nombre de carpeta no válida. ";
                ret = false;                        
            } else {
                nombre_carpeta_min = nombre_carpeta.trim();
                nombre_carpeta_min = nombre_carpeta_min.toLowerCase();
                if (nombre_carpeta_min.equals(nombre_carpeta) == false) {
                    error[0] = error[0] + "Nombre de carpeta no es solo minúsculas. ";
                    ret = false;
                }
                if (nombre_carpeta_min.endsWith("s")) {
                    error[0] = error[0] + "Nombre de carpeta es plural. ";
                    ret = false;
                }
            }
        }
        return ret;
    }
}
