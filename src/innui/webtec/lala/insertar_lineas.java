/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.contextos.enteros;
import innui.contextos.textos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.k_accion;
import static innui.html.lala.Decoraciones.k_accion_local;
import static innui.html.lala.Decoraciones.k_captura;
import static innui.html.lala.Decoraciones.k_contra;
import static innui.html.lala.Decoraciones.k_finalmente;
import static innui.html.lala.Decoraciones.k_indentado;
import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import java.net.URL;
import java.util.Map;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_parentesis_cerrar;
import static innui.html.lala.Decoraciones.k_repetir;
import static innui.html.lala.Decoraciones.k_rompe_linea;
import static innui.html.lala.Decoraciones.k_si;
import static innui.html.lala.Decoraciones.k_tratable;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_cancelacion;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class insertar_lineas extends A_ejecutores {
    public static String k_mapa_texto_previo = "innui_webtec_lala_previo"; //NOI18N
    public static String k_contexto_previo = k_mapa_texto_previo;
    public static String k_contexto_linea = "innui_webtec_lala_linea"; //NOI18N
    public static String k_mapa_texto_posterior = "innui_webtec_lala_posterior";  //NOI18N
    public static String k_contexto_posterior = k_mapa_texto_posterior;
    public static String k_mapa_espacios_num = "innui_webtec_lala_espacios_num";
    public static String k_ruta_insertar_nivel_acciones = "/lala/insertar_nivel_acciones";
    public static String k_ruta_insertar_nivel_codigos = "/lala/insertar_nivel_codigos";
    public static String k_ruta_insertar_lineas = "/lala/insertar_lineas";
    public static String k_mapa_completar_accion_boton = "completar_accion_boton";
    public static String k_ruta_completar_acciones = "/lala/completar_acciones";
    public static String k_mapa_editar_lineas_boton = "editar_lineas_boton";
    public static String k_ruta_editar_lineas = "/lala/editar_lineas";
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String texto = "";
        textos previo_texto = null;
        textos linea_texto = null;
        textos posterior_texto = null;
        textos error_texto = new textos("");
        String linea_mapa;
        int linea;
        autoformularios autoformulario;
        enteros espacios_num = null;
        URL url_accion = null;
        URL url_cancelacion = null;
        String texto_previo;
        String completar_linea_boton = null;
        String editar_lineas_boton = null;
        int pos_rompe_linea;
        int pos_parentesis_cerrar;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            if (ret) {
                texto = contexto.leer(k_contexto_archivo_abierto).dar();
                ret = (texto != null);
            }
            if (ret) {
                linea_mapa = (String) objects_mapa.get(k_mapa_linea_numero);
                linea = Integer.valueOf(linea_mapa);
                previo_texto = new textos();
                linea_texto = new textos();
                posterior_texto = new textos();
                ret = Decoraciones.extraer_linea(linea, texto, previo_texto, linea_texto, posterior_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                espacios_num = new enteros(0);
                completar_linea_boton = (String) objects_mapa.get(k_mapa_completar_accion_boton);
                editar_lineas_boton = (String) objects_mapa.get(k_mapa_editar_lineas_boton);
                ret = Decoraciones.obtener_espacios_inicio(linea_texto.dar(), espacios_num, error_texto);
                error[0] = error_texto.dar();
                if (ret) {
                    if (completar_linea_boton == null
                            && editar_lineas_boton == null) {
                        pos_rompe_linea = linea_texto.lastIndexOf(k_rompe_linea);
                        if (pos_rompe_linea >= 0) {
                            pos_parentesis_cerrar = linea_texto.lastIndexOf(k_parentesis_cerrar);
                            if (pos_parentesis_cerrar >= 0) {
                                if (pos_parentesis_cerrar < pos_rompe_linea) {
                                    ret = false;
                                    error[0] = "No se puede insertar linea si está dividida. ";
                                }
                            }
                        }
                        if (ret) {
                            if (espacios_num.dar() == 0) {
                                if (linea_texto.contains(k_accion) == false
                                 && linea_texto.contains(k_accion_local) == false) {
                                    url_accion = Urls.completar_URL(k_prefijo_url + k_ruta_insertar_nivel_acciones, k_protocolo_por_defecto, error);
                                } else {
                                    ret = false;
                                    error[0] = "La línea no se ajusta al formato de código lala. ";
                                }
                            } else {
                                if (linea_texto.contains(k_repetir)
                                        || linea_texto.contains(k_si)
                                        || linea_texto.contains(k_contra)
                                        || linea_texto.contains(k_tratable)
                                        || linea_texto.contains(k_captura)
                                        || linea_texto.contains(k_finalmente)) {
                                    espacios_num.poner(espacios_num.dar() + k_indentado.length());
                                } else {
                                    espacios_num.poner(espacios_num.dar());
                                }
                                url_accion = Urls.completar_URL(k_prefijo_url + k_ruta_insertar_nivel_codigos, k_protocolo_por_defecto, error);
                            }
                        }
                    } else if (completar_linea_boton != null) {
                        url_accion = Urls.completar_URL(k_prefijo_url + k_ruta_completar_acciones, k_protocolo_por_defecto, error);
                    } else if (editar_lineas_boton != null) {
                        url_accion = Urls.completar_URL(k_prefijo_url + k_ruta_editar_lineas, k_protocolo_por_defecto, error);
                    }
                } else {
                    url_accion = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                }
            }
            objects_mapa.put(k_mapa_espacios_num, String.valueOf(espacios_num.dar()));
            objects_mapa.put(k_mapa_autoformularios_accion, url_accion.toExternalForm());
            url_cancelacion = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_cancelacion, url_cancelacion.toExternalForm());
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            if (completar_linea_boton == null
                    && editar_lineas_boton == null) {
                autoformulario = new autoformularios();
                autoformulario.configurar(contexto);
                ret = autoformulario.ejecutar(objects_mapa, error);
            } else {
                ret = poner_redireccion(contexto, url_accion, true, null, error);                
            }
            if (ret) {
                texto_previo = previo_texto.dar() + linea_texto.dar();
                contexto.fondear_con_datos(k_contexto_previo, texto_previo);
                contexto.fondear_con_datos(k_contexto_linea, linea_texto.dar());
                contexto.fondear_con_datos(k_contexto_posterior, posterior_texto.dar());
                objects_mapa.put(k_mapa_texto_previo, texto_previo); 
                objects_mapa.put(k_mapa_texto_posterior, posterior_texto.dar()); 
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.insertar_lineas. " + error[0];
            ret = false;
        }
        return ret;
    }

}
