/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.textos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.k_comentario_doc_inicio;
import static innui.html.lala.Decoraciones.k_decoracion_fin;
import static innui.html.lala.Decoraciones.k_decoracion_inicio;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import innui.webtec.A_ejecutores;
import innui.webtec.Webtec_controlador;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.crear_proyectos.k_prefijo_acciones;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.insertar_nivel_acciones.componer_ruta_accion;
import static innui.webtec.lala.insertar_nivel_acciones.extraer_ruta_parcial;
import static innui.webtec.lala.insertar_pedir_acciones.buscar_acciones_en_texto;
import static innui.webtec.lala.paginas_principales.leer_configuraciones;
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
        String decorado_texto = "";
        String acciones_texto = "";
        String archivo_acciones_texto = "";
        int pos_inicio = 0;
        int pos_fin = 0;
        textos texto_resultante = null;
        textos error_texto = new textos("");
        String archivo_seleccionado = null;
        String ruta_seleccionada = null;
        String ruta_parcial = null;
        URL url;
        Map<String, String> configuraciones_mapa = null;
        String linea_mapa;
        String nombre = null;
        textos sin_html_texto = null;
        try {
            decorado_texto = contexto.leer(k_contexto_archivo_abierto).dar();
            ret = (decorado_texto != null);
            if (ret) {
                pos_inicio = decorado_texto.indexOf(k_decoracion_inicio);
                if (pos_inicio < 0) {
                    ret = false;
                    error[0] = "No se ha encontrado el inicio del texto que guardar. ";
                } else {
                    pos_inicio = pos_inicio + k_decoracion_inicio.length();
                }
            }
            if (ret) {
                pos_fin = decorado_texto.indexOf(k_decoracion_fin);
                if (pos_fin < 0) {
                    ret = false;
                    error[0] = "No se ha encontrado el final del texto que guardar. ";
                }
            }
            if (ret) {
                decorado_texto = decorado_texto.substring(pos_inicio, pos_fin);
                texto_resultante = new textos();
                ret = Decoraciones.desdecorar(decorado_texto, texto_resultante, error_texto);
            }
            if (ret) {
                configuraciones_mapa = leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
            }
            if (ret) {
                archivo_seleccionado = (String) configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
                file = new File(archivo_seleccionado);
            }
            if (ret) {
                ret = Utf8.escribir(file, texto_resultante.dar(), error);
            }
            if (ret) {
                ruta_seleccionada = (String) configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                file = new File(ruta_seleccionada);
                file = file.getParentFile();
                nombre = k_prefijo_acciones + file.getName() + k_extension_lala;
                nombre = aumentar_ruta(file.getAbsolutePath(), nombre, error);
                ret = (nombre != null);
            } 
            if (ret) {
                acciones_texto = buscar_acciones_en_texto(decorado_texto, error);
                ret = (acciones_texto != null);
            } 
            if (ret) {
                sin_html_texto = new textos ();
                ret = Decoraciones.quitar_etiquetas_html(acciones_texto, sin_html_texto, error_texto);
                error[0] = error_texto.dar();
            } 
            if (ret) {
                acciones_texto = sin_html_texto.dar();
                file = new File(nombre);
                archivo_acciones_texto = Utf8.leer(file, error);
                ret = (archivo_acciones_texto != null);
            } 
            if (ret) {
                ruta_parcial = extraer_ruta_parcial(ruta_seleccionada, archivo_seleccionado, error);
                ret = (ruta_parcial != null);
            }
            if (ret) {
                archivo_acciones_texto = eliminar_acciones_texto_previas(archivo_acciones_texto, ruta_parcial, error);
                ret = (archivo_acciones_texto != null);
            } 
            if (ret) {
                acciones_texto = completar_acciones_texto(acciones_texto, ruta_parcial, error);
                ret = (acciones_texto != null);
            } 
            if (ret) {
                archivo_acciones_texto = archivo_acciones_texto + acciones_texto;
                ret = Utf8.escribir(file, archivo_acciones_texto, error);
            }
            if (ret) {
                linea_mapa = (String) objects_mapa.get(k_mapa_linea_numero);
                ret = poner_url_ref_a_contenido(k_prefijo_ancla_linea + linea_mapa, objects_mapa, error);
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_guardar_archivos, error[0]);
            } else {
                objects_mapa.put(k_mapa_editar_archivos_error, "Archivo guardado, y archivo de acciones del proyecto actualizado . ");
            }
            url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
            ret = Webtec_controlador.poner_redireccion(contexto, url, true, null, error);
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

    public static String eliminar_acciones_texto_previas(String archivo_acciones_texto, String archivo_seleccionado, String [] error) {
        boolean ret = false;
        String retorno = "";
        String [] fragmentos_array = null;
        String divisor;
        divisor = k_comentario_doc_inicio.trim();
        divisor = divisor.replace("*", "\\*");
        fragmentos_array = archivo_acciones_texto.split(divisor);
        for (String fragmento: fragmentos_array) {
            if (fragmento.trim().isEmpty() == false) {
                if (fragmento.contains(archivo_seleccionado) == false) {
                    retorno = retorno + k_comentario_doc_inicio.trim() + fragmento;
                }
            }
        }
        return retorno;
    }
    
    public static String completar_acciones_texto(String acciones_texto, String ruta_parcial_archivo, String [] error)
    {
        boolean ret = true;
        String retorno = "";
        String [] fragmentos_array = null;
        String divisor;
        String ruta_accion;
        int pos;
        divisor = k_comentario_doc_inicio.trim();
        divisor = divisor.replace("*", "\\*");
        fragmentos_array = acciones_texto.split(divisor);
        ruta_accion = componer_ruta_accion(ruta_parcial_archivo, error);
        for (String fragmento: fragmentos_array) {
            if (fragmento.trim().isEmpty() == false) {
                pos = fragmento.lastIndexOf("\n");
                if (pos < 0) {
                    ret = false;
                    error[0] = "No se ha encontrado el salto de línea del fin de la accion. ";
                    break;
                }
                retorno = retorno + k_comentario_doc_inicio.trim() 
                        + fragmento.substring(0, pos) 
                        + ruta_accion
                        + fragmento.substring(pos);
            }
        }
        if (ret == false) {
            retorno = null;
        }
        return retorno;
    }
}
