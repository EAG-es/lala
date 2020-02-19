/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static ingui.javafx.webtec.lala.Lala.k_deshacer_con_archivo;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.contextos.contextos;
import innui.edicion.Deshacer_con_archivos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_cancelacion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import java.util.Map;
import innui.webtec.gui.configuraciones;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.abrir_archivos.validar_nombre_archivo;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import java.io.File;
import java.net.URL;
import static innui.webtec.lala.abrir_archivos.guardar_cambio;
import static innui.webtec.lala.paginas_principales.k_ruta_paginas_principales;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class crear_archivos extends A_ejecutores {
    public static String k_mapa_crear_carpeta_boton = "crear_carpeta_boton"; //NOI18N
    public static String k_mapa_ruta_seleccionada = "ruta_seleccionada"; //NOI18N
    public static String k_mapa_crear_carpeta = "crear_carpeta"; //NOI18N
    public static String k_mapa_nombre_archivo = "nombre_archivo"; //NOI18N
    public static String k_extension_lala = ".la"; //NOI18N
    public static String k_ruta_crear_archivos = "/lala/crear_archivos";        
    
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
        String crear_carpeta_boton;
        String ruta_seleccionada;
        String presentar;
        presentar = (String) objects_mapa.get(k_mapa_autoformularios_presentar);
        boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
        crear_carpeta_boton = (String) objects_mapa.get(k_mapa_crear_carpeta_boton);
        ruta_seleccionada = (String) objects_mapa.get(k_mapa_ruta_seleccionada);
        if ((boton_enviar_pulsado == null
                && crear_carpeta_boton == null
                && ruta_seleccionada == null)
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
        String ruta_seleccionada = null;
        Map<String, String> configuraciones_mapa = null;
        URL url_cancelacion;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ruta_seleccionada = (String) objects_mapa.get(k_mapa_ruta_seleccionada);
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
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            objects_mapa.put(k_mapa_ruta_seleccionada, ruta_seleccionada);
            url_cancelacion = Urls.completar_URL(k_prefijo_url + k_ruta_paginas_principales, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_cancelacion, url_cancelacion.toExternalForm());
            ret = autoformulario.ejecutar(objects_mapa, error);
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en presentar.crear_archivos" + error[0];
            ret = false;
        }
        return ret;
    }

    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
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
                crear_carpeta_boton = (String) objects_mapa.get(k_mapa_crear_carpeta_boton);
                if (crear_carpeta_boton !=  null) {
                    configuracion_ruta_seleccionada = (String) configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                    ruta_seleccionada = (String) objects_mapa.get(k_mapa_ruta_seleccionada);
                    nombre_archivo = (String) objects_mapa.get(k_mapa_crear_carpeta);
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
                        objects_mapa.put(k_mapa_ruta_seleccionada, nombre_archivo);
                    }
                } else {
                    boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
                    if (boton_enviar_pulsado !=  null) {
                        ruta_seleccionada = (String) objects_mapa.get(k_mapa_ruta_seleccionada);
                        file = new File(ruta_seleccionada);
                        error[0] = "";
                        if (file.isDirectory() == false) {
                            error[0] = error[0] + "Debe seleccionar una carpeta. ";
                            ret = false;
                        }
                        nombre_archivo  = (String) objects_mapa.get(k_mapa_nombre_archivo);
                        if (ret) {
                            nuevo_archivo = aumentar_ruta(ruta_seleccionada, nombre_archivo + k_extension_lala, error);
                            ret = (nuevo_archivo != null);
                        }
                        if (ret) {
                            file = new File(nuevo_archivo);
                            ret = ret && validar_nombre_archivo(file, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                        if (ret) {
                            configuraciones_mapa.put(k_configuraciones_archivo_seleccionado, nuevo_archivo);
                            ret = configuraciones.escribir(configuraciones_mapa, objects_mapa, error);
                        }
                        if (ret) {
                            contexto.modificar(k_contexto_archivo_abierto, "");
                            file = new File(nuevo_archivo);
                            if (file.exists() == false) {
                                file.createNewFile();
                            } else {
                                error[0] = "Ya existe un arhivo con ese nombre. ";
                                ret = false;
                            }
                        }
                        if (ret) {
                            ret = iniciar_deshacer(contexto, error);
                        }
                    }
                }
            }
            if (ret && boton_enviar_pulsado != null) {
                objects_mapa.put(k_mapa_editar_archivos_error, "");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (ret == false || boton_enviar_pulsado == null) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_archivos, k_protocolo_por_defecto, error);
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
            error[0] = "Error en procesar.crear_archivos. " + error [0];
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
            if (nombre_carpeta.trim().isEmpty()) {
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
    
    public static boolean iniciar_deshacer(contextos contexto, String [] error) {
        boolean ret = true;
        Deshacer_con_archivos deshacer_con_archivo = null;
        deshacer_con_archivo = contexto.leer(k_deshacer_con_archivo).dar();
        ret = (deshacer_con_archivo != null);
        if (ret) {
            ret = deshacer_con_archivo.iniciar(error);
        } else {
            ret = false;
            error [0] = "No se ha encontrado el objeto que gestiona las operaciones deshacer-rehacer. ";
        }
        return ret;
    }
}
