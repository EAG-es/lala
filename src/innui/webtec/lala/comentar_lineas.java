/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.contextos.textos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.k_comentario_linea;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import static innui.html.lala.Decoraciones.k_sufijo_linea;
import innui.webtec.A_ejecutores;
import innui.webtec.Webtec_controlador;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import java.net.URL;
import java.util.Map;
import static innui.webtec.lala.abrir_archivos.guardar_cambio;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class comentar_lineas extends A_ejecutores {
    public static String k_mapa_comentar_lineas = "innui_webtec_lala_comentar_lineas"; //NOI18N
    public static String k_mapa_comentar_lineas_operacion = "innui_webtec_lala_comentar_lineas_operacion"; //NOI18N
    public static String k_operacion_borrar = "borrar"; //NOI18N
    public static String k_operacion_descomentar = "descomentar"; //NOI18N
    public static String k_operacion_comentar = "comentar"; //NOI18N
    public static String k_ruta_comentar_lineas = "/lala/comentar_lineas";   
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String texto = null;
        textos previo_texto = null;
        textos linea_texto = null;
        textos posterior_texto = null;
        textos error_texto = new textos(""); 
        String linea_mapa = null;
        int linea;        
        URL url;
        String texto_final = null;
        String texto_linea = null;
        String operacion = null;
        try {
            texto = contexto.leer(k_contexto_archivo_abierto).dar();
            ret = (texto != null);
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
                texto_linea = linea_texto.dar();
                operacion = (String) objects_mapa.get(k_mapa_comentar_lineas_operacion);
                if (operacion == null || operacion.equals(k_operacion_comentar)) {
                    texto_linea = comentar_linea(texto_linea, error);
                } else if (operacion.equals(k_operacion_borrar)) {
                    texto_linea = "";
                } else if (operacion.equals(k_operacion_descomentar)) {
                    texto_linea = descomentar_linea(texto_linea, error);
                } else {
                    ret = false;
                    error[0] = "Operacion no soportada desde comentar_lineas. ";
                }
            }
            if (ret) {
                texto_final= previo_texto + texto_linea + posterior_texto;
                ret = contexto.modificar(k_contexto_archivo_abierto, texto_final).es();
            }
            if (ret) {
                ret = guardar_cambio(contexto, texto_final, error);
            }
            if (ret) {
                ret = poner_url_ref_a_contenido(k_prefijo_ancla_linea + linea_mapa, objects_mapa, error);
            }
            if (ret) {
                objects_mapa.put(k_mapa_editar_archivos_error, error[0]);
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = Webtec_controlador.poner_redireccion(contexto, url, true, null, error);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.comentar_lineas. " + error[0];
            ret = false;
        }
        return ret;
    }
    
    public static String comentar_linea(String linea, String [] error) {
        boolean ret = true;
        String retorno = null;
        String resto_linea_texto;
        String inicio_texto;
        int pos;
        pos = linea.indexOf(k_sufijo_linea);
        pos = pos + k_sufijo_linea.length();
        inicio_texto = linea.substring(0, pos);
        resto_linea_texto = linea.substring(pos);
        retorno = inicio_texto + k_comentario_linea + resto_linea_texto;
        return retorno;
    }
    
    public static String descomentar_linea(String linea, String [] error) {
        boolean ret = true;
        String retorno = null;
        String resto_linea_texto;
        String inicio_texto;
        int pos;
        pos = linea.indexOf(k_sufijo_linea + k_comentario_linea);
        if (pos >= 0) {
            pos = pos + k_sufijo_linea.length();
            inicio_texto = linea.substring(0, pos);
            pos = pos + k_comentario_linea.length();
            resto_linea_texto = linea.substring(pos);
            retorno = inicio_texto + resto_linea_texto;
        } else {
            retorno = linea;
        }
        return retorno;
    }
}
