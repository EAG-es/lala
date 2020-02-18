/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import static innui.html.lala.Decoraciones.k_html_nueva_linea;
import static innui.html.lala.Decoraciones.k_retornar;
import static innui.html.lala.Decoraciones.k_variable;
import innui.webtec.A_ejecutores;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import java.net.URL;
import java.util.Map;
import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class crear_variables extends A_ejecutores {
    public static String k_procesar_crear_variables = "/lala/procesar_crear_variables";
    public static String k_nombre_crear_variables_datos_previos = "innui_webtec_lala_crear_variables_datos_previos";
    public static String k_contexto_variables_datos_previos = "contexto_lala_variables_datos_previos";

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
        String texto = null;
        String texto_previo = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                texto_previo = contexto.leer(k_contexto_previo).dar();
                if (texto_previo == null) {
                    ret = false;
                    error[0] = "No se ha encontrado el texto previo en el contexto. ";
                }
            }
            if (ret) {
                texto = buscar_variables_accion(texto_previo, error);
                ret = (texto != null);
            }
            if (ret) {
                objects_mapa.put(k_nombre_crear_variables_datos_previos, texto);
                contexto.fondear_con_datos(k_contexto_variables_datos_previos, texto);
                url_accion = Urls.completar_URL(k_prefijo_url + k_procesar_crear_variables, k_protocolo_por_defecto, error);
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

    public static String buscar_variables_accion(String texto, String [] error) {
        boolean ret = true;
        String retorno = null;
        StringReader stringreader = null;
        BufferedReader bufferedreader = null;
        String linea_leida_texto = "";
        String linea_candidata_1 = null;
        String linea_candidata_2 = null;
        int pos;
        try {
            stringreader = new StringReader(texto);
            bufferedreader = new BufferedReader(stringreader);
            while (true) {
                linea_leida_texto = bufferedreader.readLine();
                if (linea_leida_texto == null) {
                    break;
                }
                if (linea_leida_texto.contains(k_variable)) {
                    if (linea_candidata_1 == null) {
                        linea_candidata_1 = linea_leida_texto;
                    } else if (linea_candidata_2 == null) {
                        linea_candidata_2 = linea_leida_texto;
                    } else {
                        ret = false;
                        error[0] = "Lineas de variable repetidas. ";
                        break;
                    }
                } else if (linea_leida_texto.contains(k_retornar)) { 
                    if (linea_candidata_2 != null) {
                        linea_candidata_2 = null;
                    } else {
                        linea_candidata_1 = null;
                    }
                }
            }
            if (linea_candidata_1 != null) {
                retorno = linea_candidata_1;
            }
            if (linea_candidata_2 != null) {
                pos = linea_candidata_2.indexOf(k_variable);
                pos = pos + k_variable.length();
                linea_candidata_2 = linea_candidata_2.substring(pos);
                retorno = retorno + linea_candidata_2;
            }
            retorno = retorno.replace(k_html_nueva_linea, "");
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en extraer_linea. " + error[0];
            ret = false;
        }
        return retorno;
    }
}
