/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static ingui.javafx.webtec.lala.Lala.k_deshacer_con_archivo;
import innui.archivos.Utf8;
import innui.contextos.contextos;
import innui.contextos.textos;
import innui.edicion.Deshacer_con_archivos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import java.util.Map;
import innui.webtec.gui.configuraciones;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_archivos.validar_nombre_carpeta;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import java.io.File;
import java.net.URL;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class abrir_archivos extends A_ejecutores {
    public static String k_contexto_archivo_abierto = "contexto_lala_archivo_abierto"; //NOI18N
    public static String k_mapa_archivo_seleccionado = "archivo_seleccionado"; //NOI18N
    public static String k_ruta_abrir_archivos = "/lala/abrir_archivos";
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
        archivo_seleccionado = (String) objects_mapa.get(k_mapa_archivo_seleccionado);
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
        String ruta_seleccionada = null;
        Map<String, String> configuraciones_mapa = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ruta_seleccionada = (String) objects_mapa.get(k_mapa_archivo_seleccionado);
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
            objects_mapa.put(k_mapa_archivo_seleccionado, ruta_seleccionada);
            ret = autoformulario.ejecutar(objects_mapa, error);
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en presentar.abrir_archivos. " + error[0];
            ret = false;
        }
        return ret;
    }
   
    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String boton_enviar_pulsado = null;
        String archivo_seleccionado = null;
        File file;        
        Map<String, String> configuraciones_mapa = null;
        String configuracion_ruta_seleccionada;
        String nombre_archivo;
        String ruta_archivo;
        URL url;
        try {
            configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
            ret = (configuraciones_mapa != null);
            if (ret) {
                archivo_seleccionado = (String) objects_mapa.get(k_mapa_archivo_seleccionado);
                file = new File(archivo_seleccionado);
                boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
                if (boton_enviar_pulsado !=  null || file.isFile()) {
                    boton_enviar_pulsado = "";
                    ret = validar_nombre_archivo(file, error);
                    if (ret) {
                        configuraciones_mapa.put(k_configuraciones_archivo_seleccionado, archivo_seleccionado);
                        ret = configuraciones.escribir(configuraciones_mapa, objects_mapa, error);
                    }
                } else {
                    configuracion_ruta_seleccionada = (String) configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                    nombre_archivo = file.getName();
                    ruta_archivo = file.getParent();
                    ret = validar_nombre_carpeta(configuracion_ruta_seleccionada, ruta_archivo, nombre_archivo, error);
                }
            }
            if (ret) {
                ret = abrir_archivo(contexto, archivo_seleccionado, error);
            }
            if (ret && boton_enviar_pulsado != null) {
                objects_mapa.put(k_mapa_editar_archivos_error, "");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (ret == false || boton_enviar_pulsado == null) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_abrir_archivos, k_protocolo_por_defecto, error);
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
            error[0] = "Error en procesar.abrir_archivos. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static boolean validar_nombre_archivo(File file, String [] error) {
        boolean ret = true;
        String nombre_archivo;
        String nombre_archivo_min;
        String nombre_archivo_min_sin_ext;
        nombre_archivo = file.getName();
        error[0] = "";
        if (nombre_archivo.endsWith(k_extension_lala) == false) {
            error[0] = error[0] + "Nombre de archivo no tiene la extension: " + k_extension_lala + " ";
            ret = false;                        
        } else {
            nombre_archivo_min = nombre_archivo.trim();
            nombre_archivo_min = nombre_archivo_min.toLowerCase();
            if (nombre_archivo_min.equals(nombre_archivo) == false) {
                error[0] = error[0] + "Nombre de archivo no es solo minúsculas. ";
                ret = false;
            }
            nombre_archivo_min_sin_ext = nombre_archivo_min.substring(0, nombre_archivo_min.length() - k_extension_lala.length());
            if (nombre_archivo_min_sin_ext.endsWith("s") == false) {
                error[0] = error[0] + "Nombre de archivo no es plural. ";
                ret = false;
            }
        }
        return ret;
    }    
    
    public static boolean abrir_archivo(contextos contexto, String archivo_seleccionado, String [] error) {
        boolean ret = true;
        String archivo_texto;
        textos decorado_texto;
        textos error_texto;
        archivo_texto = Utf8.leer(archivo_seleccionado, error);
        ret = (archivo_texto != null);
        if (ret) {
            decorado_texto = new textos();
            error_texto = new textos();
            ret = Decoraciones.decorar(archivo_texto, decorado_texto, error_texto);
            if (ret) {
                contexto.fondear_con_datos(k_contexto_archivo_abierto, decorado_texto.dar());
                ret = guardar_cambio(contexto, decorado_texto.dar(), error);
            }
        }
        return ret;
    }

    public static boolean guardar_cambio(contextos contexto, String texto, String [] error) {
        boolean ret = true;
        Deshacer_con_archivos deshacer_con_archivo = null;
        deshacer_con_archivo = contexto.leer(k_deshacer_con_archivo).dar();
        ret = (deshacer_con_archivo != null);
        if (ret) {
            ret = deshacer_con_archivo.guardar_cambio(texto, error);
        } else {
            ret = false;
            error [0] = "No se ha encontrado el objeto que gestiona las operaciones deshacer-rehacer. ";
        }
        return ret;
    }
}
