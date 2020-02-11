/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.html.lala;

import innui.contextos.enteros;
import innui.contextos.i_eles;
import innui.contextos.textos;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emilio
 */
public class Decoraciones {
    public static String k_mapa_linea_numero = "innui_html_lala_linea_numero";
    public static String k_nombre_onclick_linea = "innui_html_lala_onclick_linea";
    public static String k_prefijo_ancla_linea = "linea_";
    public static String k_prefijo_linea = "<a class=\"innui_webtec_gui_menu_aplicaciones_a\" id='" + k_prefijo_ancla_linea;
    public static String k_infijo_linea = "' href='' onclick='" + k_nombre_onclick_linea + "(this, \"linea\", \"";
    public static String k_presufijo_linea = "\"); return false'>";
    public static String k_sufijo_linea = "</a>";
    public static String k_prefijo_span_azul = "<span style='color:blue;'>";
    public static String k_prefijo_span_gris = "<span style='color:gray;'>";
    public static String k_sufijo_span = "</span>";
    public static String k_html_nueva_linea = "<br>";
    public static String k_html_espacio = "&nbsp;";
    public static String k_indentado = "    ";
    public static String k_indentado_sustitucion = "&%$·";
    public static String k_parentesis_abrir = " ( ";
    public static String k_parentesis_cerrar = " ) ";
    public static String k_corchete_abrir = " [ ";
    public static String k_corchete_cerrar = " ] ";
    public static String k_puntos_suspensivos = " ... ";
    public static String k_rompe_linea = " \\ ";
    public static String k_coma = " , ";
    public static String k_punto = " . ";
    public static String k_decoracion_inicio = "<!--decoracion-->\n";
    public static String k_decoracion_fin = "<!--/decoracion-->\n";
    public static String k_accion = " accion ";
    public static String k_accion_local = " accion local ";
    public static String k_variable = " variable ";
    public static String k_subaccion = " subaccion ";
    public static String k_comentario_doc_inicio = " /** ";
    public static String k_comentario_inicio = " /* ";
    public static String k_comentario_marca_linea = " * ";
    public static String k_comentario_fin = " */ ";
    public static String k_comentario_linea = " // ";
    public static String k_documentacion_param = " @param ";
    public static String k_si = " si ";
    public static String k_contra = " contra ";
    public static String k_fin_bloque_si = " /si ";
    public static String k_repetir = " repetir ";
    public static String k_fin_bloque_repetir = " /repetir ";
    public static String k_salir = " salir ";
    public static String k_tratable = " tratable ";
    public static String k_captura = " captura ";
    public static String k_finalmente = " finalmente ";
    public static String k_fin_bloque_tratable = " /tratable ";
    public static String k_retornar = " retornar ";
    public static String k_verdad = " verdad ";
    public static String k_falso = " falso ";
    public static String k_ente_nulo = " ente_nulo ";
    public static String k_no_lala = "no_lala";
    public static String k_fin_bloque_no_lala = "/no_lala";
    public static List<String> palabras_clave_accion_lista = new ArrayList () {{
        add(k_accion);
        add(k_accion_local);
        add(k_retornar);
    }};
    public static List<String> palabras_clave_codigo_lista = new ArrayList () {{
        add(k_variable);
        add(k_subaccion);
        add(k_retornar);
        add(k_si);
        add(k_contra);
        add(k_fin_bloque_si);
        add(k_repetir);
        add(k_fin_bloque_repetir);
        add(k_salir);
        add(k_tratable);
        add(k_captura);
        add(k_finalmente);
        add(k_fin_bloque_tratable);
        add(k_no_lala);
        add(k_fin_bloque_no_lala);
    }};
    public static List<String> palabras_clave_interna_lista = new ArrayList () {{
        add(k_verdad);
        add(k_falso);
        add(k_documentacion_param);
        add(k_ente_nulo);
    }};
        
    public static boolean decorar(String texto, textos decorado_texto, textos error, i_eles ... contextos_array) {
        boolean ret = true;
        String resultado_texto = "";
        String inicio_texto = "";
        String fin_texto = "";
        ret = decorar_subtexto(texto, decorado_texto, error, contextos_array);
        if (ret) {
            inicio_texto = k_decoracion_inicio;
            fin_texto = k_decoracion_fin;
            resultado_texto = inicio_texto + decorado_texto.dar() + fin_texto;
            decorado_texto.poner(resultado_texto);
        }
        return ret;
    }
    
    public static boolean decorar_subtexto(String texto, textos decorado_texto, textos error, i_eles ... contextos_array) {
        boolean ret = true;
        StringReader stringreader;
        BufferedReader bufferedreader;
        String linea_texto;
        int linea = 0;
        String resultado_leer_texto = "";
        String resultado_texto = "";
        try {
            resultado_leer_texto = texto;
            resultado_leer_texto = resultado_leer_texto.replace("\r", "");
            resultado_leer_texto = resultado_leer_texto.replaceAll("\\n\\s*\\n", "\n");
            stringreader = new StringReader(resultado_leer_texto);
            bufferedreader = new BufferedReader(stringreader);
            while (true) {
                linea = linea + 1;
                linea_texto = bufferedreader.readLine();
                if (linea_texto == null) {
                    break;
                }
                if (linea_texto.contains(k_comentario_linea)) {
                    linea_texto = linea_texto.replace(k_comentario_linea, k_prefijo_span_gris + k_comentario_linea);
                    linea_texto = linea_texto + k_sufijo_span;
                }
                linea_texto = crear_marca_linea(linea, error) + linea_texto;
                resultado_texto = resultado_texto + linea_texto + k_html_nueva_linea + "\n";
            }
            if (linea == 1) { // Archivo vacio
                linea_texto = crear_marca_linea(linea, error);
                ret = (linea_texto != null);
                if (ret) {
                    resultado_texto = resultado_texto + linea_texto + k_html_nueva_linea + "\n";
                }
            }
            if (ret) {
                for (String palabra_clave: palabras_clave_accion_lista) {
                    resultado_texto = resultado_texto.replace(k_sufijo_linea + palabra_clave, k_sufijo_linea + k_prefijo_span_azul + palabra_clave + k_sufijo_span);
                }
                for (String palabra_clave: palabras_clave_codigo_lista) {
                    resultado_texto = resultado_texto.replace(k_indentado + palabra_clave, k_indentado + k_prefijo_span_azul + palabra_clave + k_sufijo_span);
                }
                for (String palabra_clave: palabras_clave_interna_lista) {
                    resultado_texto = resultado_texto.replace(palabra_clave, k_prefijo_span_azul + palabra_clave + k_sufijo_span);
                }
                resultado_texto = resultado_texto.replace(k_comentario_doc_inicio, k_prefijo_span_gris + k_comentario_doc_inicio);
                resultado_texto = resultado_texto.replace(k_comentario_inicio, k_prefijo_span_gris + k_comentario_inicio);
                resultado_texto = resultado_texto.replace(k_comentario_fin, k_comentario_fin + k_sufijo_span);
                resultado_texto = resultado_texto.replaceAll("\\t", " " + k_html_espacio + k_html_espacio + " ");
                resultado_texto = resultado_texto.replaceAll("\\s\\s\\s\\s", " " + k_html_espacio+k_html_espacio + " ");
                resultado_texto = resultado_texto.replaceAll("\\s\\s", k_html_espacio + " ");
                decorado_texto.poner(resultado_texto);
            }
        } catch (Exception e) {
            error.poner(e.getMessage());
            if (error.es_nulo()) {
                error.poner(""); //NOI18N
            }
            error.poner("Error en decorar. " + error.dar());
            ret = false;
        }
        return ret;
    }

    public static boolean extraer_linea(int linea, String texto, textos previo_texto, textos linea_texto, textos posterior_texto, textos error) {
        boolean ret = true;
        String marca_linea;
        StringReader stringreader = null;
        BufferedReader bufferedreader = null;
        String linea_leida_texto = "";
        String previo = "";
        String posterior = "";
        try {
            marca_linea = crear_marca_linea(linea, error);
            ret = (marca_linea != null);
            if (ret) {
                stringreader = new StringReader(texto);
                bufferedreader = new BufferedReader(stringreader);
                previo_texto.poner("");
                while (true) {
                    linea_leida_texto = bufferedreader.readLine();
                    if (linea_leida_texto == null) {
                        ret = false;
                        error.poner("No se ha encontrado la linea indicada. ");
                        break;
                    }
                    if (linea_leida_texto.startsWith(marca_linea)) {
                        linea_leida_texto = linea_leida_texto + "\n";
                        break;
                    }
                    previo = previo + linea_leida_texto + "\n";
                }
            }
            if (ret) {
                previo_texto.poner(previo);
                linea_texto.poner(linea_leida_texto);
                while (true) {
                    linea_leida_texto = bufferedreader.readLine();
                    if (linea_leida_texto == null) {
                        break;
                    }
                    posterior = posterior + linea_leida_texto + "\n";
                }
                posterior_texto.poner(posterior);
            }
        } catch (Exception e) {
            error.poner(e.getMessage());
            if (error.es_nulo()) {
                error.poner(""); //NOI18N
            }
            error.poner("Error en extraer_linea. " + error.dar());
            ret = false;
        }
        return ret;
    }

    public static String crear_formato_marca_linea(int linea) {
        return String.format("%03d", linea);
    }
    
    public static String crear_marca_linea(int linea, textos error) {
        return crear_marca_linea(crear_formato_marca_linea(linea), error);
    }
    
    public static String crear_marca_linea(String linea_texto, textos error) {
        return k_prefijo_linea + linea_texto 
            + k_infijo_linea + linea_texto 
            + k_presufijo_linea + linea_texto
            + k_sufijo_linea;
    }
    
    public static boolean desdecorar(String texto, textos desdecorado_texto, textos error, i_eles ... contextos_array) {
        boolean ret = true;
//        int linea = 0;
        String resultado_texto = "";
//        StringReader stringreader;
//        BufferedReader bufferedreader;
//        String linea_texto;
        textos intermedio_texto;
        try {
            resultado_texto = texto;
//            stringreader = new StringReader(resultado_texto);
//            bufferedreader = new BufferedReader(stringreader);
//            resultado_texto = "";
//            while (true) {
//                linea = linea + 1;
//                linea_texto = bufferedreader.readLine();
//                if (linea_texto == null) {
//                    break;
//                }
//                if (linea_texto.contains(k_comentario_linea)) {
//                    linea_texto = linea_texto.replace(k_prefijo_span_gris + k_comentario_linea, k_comentario_linea);
//                    linea_texto = linea_texto.replace(k_sufijo_span + k_html_nueva_linea, "");
//                }
//                resultado_texto = resultado_texto + linea_texto + "\n";
//            }
            intermedio_texto = new textos(resultado_texto);
            ret = quitar_numeros_linea(intermedio_texto, error);
            if (ret) {
                resultado_texto = intermedio_texto.dar();
                resultado_texto = resultado_texto.replace(k_html_espacio, " ");
                resultado_texto = resultado_texto.replace(k_indentado, k_indentado_sustitucion);
                resultado_texto = resultado_texto.replaceAll("  ", " ");
                resultado_texto = resultado_texto.replace(k_indentado_sustitucion, k_indentado);
                resultado_texto = resultado_texto.replace(k_html_nueva_linea, "");
                resultado_texto = resultado_texto.replace(k_prefijo_span_azul, "");
                resultado_texto = resultado_texto.replace(k_sufijo_span, "");
                resultado_texto = resultado_texto.replace(k_prefijo_span_gris, "");
                resultado_texto = resultado_texto.replace(k_prefijo_span_gris, "");
                desdecorado_texto.poner(resultado_texto);
            }
        } catch (Exception e) {
            error.poner(e.getMessage());
            if (error.es_nulo()) {
                error.poner(""); //NOI18N
            }
            error.poner("Error en desdecorar. " + error.dar());
            ret = false;
        }
        return ret;
    }

    public static boolean quitar_numeros_linea(textos texto, textos error) {
        boolean ret = true;
        String digitos;
        String patron_linea_texto;
        digitos = "\\d+";
        patron_linea_texto = crear_marca_linea(digitos, error);
        ret = (patron_linea_texto != null);
        if (ret) {
            patron_linea_texto = patron_linea_texto.replace("(", "\\(");
            patron_linea_texto = patron_linea_texto.replace(")", "\\)");
            // Activar modo multilinea, y usar el inicio de línea.
            patron_linea_texto = "(?m)^" + patron_linea_texto;
            texto.replaceAll(patron_linea_texto, "");
        }
        return ret;
    }

    public static boolean actualizar_numeros_linea(textos texto, textos error) {
        boolean ret = true;
        int linea = 0;
        String resultado_texto = "";
        StringReader stringreader;
        BufferedReader bufferedreader;
        String linea_texto;
        String digitos;
        String patron_linea_texto;
        String nueva_linea_texto;
        try {
            digitos = "\\d+";
            patron_linea_texto = crear_marca_linea(digitos, error);
            ret = (patron_linea_texto != null);
            if (ret) {
                patron_linea_texto = patron_linea_texto.replace("(", "\\(");
                patron_linea_texto = patron_linea_texto.replace(")", "\\)");
                patron_linea_texto = "^" + patron_linea_texto;
            }
            resultado_texto = texto.dar();
            stringreader = new StringReader(resultado_texto);
            bufferedreader = new BufferedReader(stringreader);
            resultado_texto = "";
            while (true) {
                linea_texto = bufferedreader.readLine();
                if (linea_texto == null) {
                    break;
                }
                if (linea_texto.startsWith(k_prefijo_linea)) {
                    linea = linea + 1;
                    nueva_linea_texto = crear_marca_linea(linea, error);
                    linea_texto = linea_texto.replaceAll(patron_linea_texto, nueva_linea_texto);
                }
                resultado_texto = resultado_texto + linea_texto + "\n";
            }
            texto.poner(resultado_texto);
        } catch (Exception e) {
            error.poner(e.getMessage());
            if (error.es_nulo()) {
                error.poner(""); //NOI18N
            }
            error.poner("Error en actualizar_numeros_linea. " + error.dar());
            ret = false;
        }
        return ret;
    }
    
    public static boolean obtener_espacios_inicio(String linea_texto, enteros espacios_num, textos error) {
        boolean ret = true;
        textos texto = new textos (linea_texto);
        ret = Decoraciones.quitar_numeros_linea(texto, error);
        int i = 0;
        while (true) {
            if (texto.startsWith(" ")) {
                i = i + 1;
                texto.substring(" ".length());
            } else if (texto.startsWith(k_html_espacio)) {
                i = i + 1;
                texto.substring(k_html_espacio.length());
            } else {
                break;
            }
        }
        espacios_num.poner(i);
        return ret;
    }
    
    public static boolean quitar_etiquetas_html(String texto, textos texto_sin_etiquetas, textos error) {
        boolean ret = true;
        String patron = "<[^>]+>";
        texto = texto.replaceAll(patron, "");
        texto_sin_etiquetas.poner(texto);
        return ret;
    }
}
