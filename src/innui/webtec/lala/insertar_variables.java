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
import static innui.html.lala.Decoraciones.actualizar_numeros_linea;
import static innui.html.lala.Decoraciones.k_accion;
import static innui.html.lala.Decoraciones.k_coma;
import static innui.html.lala.Decoraciones.k_ente_nulo;
import static innui.html.lala.Decoraciones.k_html_espacio;
import static innui.html.lala.Decoraciones.k_html_nueva_linea;
import static innui.html.lala.Decoraciones.k_indentado;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_parentesis_abrir;
import static innui.html.lala.Decoraciones.k_parentesis_cerrar;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import static innui.html.lala.Decoraciones.k_puntos_suspensivos;
import static innui.html.lala.Decoraciones.k_retornar;
import static innui.html.lala.Decoraciones.k_subaccion;
import static innui.html.lala.Decoraciones.k_sufijo_span;
import static innui.html.lala.Decoraciones.k_variable;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_espacios_num;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.insertar_nivel_acciones.validar_variable;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import java.net.URL;
import java.util.Map;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class insertar_variables extends A_ejecutores {
    public static String k_nombre_crear_variables_datos_previos = "innui_webtec_lala_crear_variables_datos_previos";
    public static String k_contexto_variables_linea = "contexto_lala_variables_datos_previos";
    public static String k_ruta_crear_variables = "/lala/crear_variables";    

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
        String presentar;
        presentar = (String) objects_mapa.get(k_mapa_autoformularios_presentar);
        boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
        if (boton_enviar_pulsado == null
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
        URL url_accion = null;
        String texto_previo = null;
        String [] parametros_y_variables = { "" };
        String [] variables_linea = { "" };
        String previo_texto = null;
        String posterior_texto = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            if (ret) {
                texto_previo = contexto.leer(k_contexto_previo).dar();
                if (texto_previo == null) {
                    ret = false;
                    error[0] = "No se ha encontrado el texto previo en el contexto. ";
                }
            }
            if (ret) {
                ret = buscar_parametros_y_variables_en_accion(texto_previo, parametros_y_variables, variables_linea, error);
            }
            objects_mapa.put(k_nombre_crear_variables_datos_previos, parametros_y_variables[0]);
            contexto.fondear_con_datos(k_contexto_variables_linea, variables_linea[0]);
            previo_texto = contexto.leer(k_contexto_previo).dar();
            posterior_texto = contexto.leer(k_contexto_posterior).dar();
            objects_mapa.put(k_mapa_texto_previo, previo_texto); 
            objects_mapa.put(k_mapa_texto_posterior, posterior_texto); 
            url_accion = Urls.completar_URL(k_prefijo_url + k_ruta_crear_variables, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_accion, url_accion.toExternalForm());
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
            error[0] = "Error en ejecutar.crear_atributos_semiconstantes. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static boolean buscar_parametros_y_variables_en_accion(String texto, String [] parametros_y_variables, String [] variables_linea, String [] error) {
        boolean ret = true;
        StringReader stringreader = null;
        BufferedReader bufferedreader = null;
        String linea_leida_texto = "";
        String parametros_candidatos_1 = null;
        String parametros_candidatos_2 = null;
        String variables_candidatas_1 = null;
        String variables_candidatas_2 = null;
        List <String> subacciones_lista = new ArrayList();
        int pos;
        try {
            stringreader = new StringReader(texto);
            bufferedreader = new BufferedReader(stringreader);
            while (true) {
                linea_leida_texto = bufferedreader.readLine();
                if (linea_leida_texto == null) {
                    break;
                }
                if (linea_leida_texto.contains(k_accion + k_sufijo_span)) {
                    pos = linea_leida_texto.indexOf(k_accion + k_sufijo_span);
                    pos = pos + k_accion.length() + k_sufijo_span.length();
                    linea_leida_texto = linea_leida_texto.substring(pos);
                    parametros_candidatos_1 = extraer_parametros(linea_leida_texto, error);
                    ret = (parametros_candidatos_1 != null);
                } else if (linea_leida_texto.contains(k_subaccion + k_sufijo_span)) {
                    pos = linea_leida_texto.indexOf(k_subaccion + k_sufijo_span);
                    pos = pos + k_subaccion.length() + k_sufijo_span.length();
                    linea_leida_texto = linea_leida_texto.substring(pos);
                    subacciones_lista.add(linea_leida_texto);
                    parametros_candidatos_2 = extraer_parametros(linea_leida_texto, error);                    
                    ret = (parametros_candidatos_1 != null);
                } else if (linea_leida_texto.contains(k_variable)) {
                    if (variables_candidatas_1 == null) {
                        variables_candidatas_1 = linea_leida_texto;
                    } else if (variables_candidatas_2 == null) {
                        variables_candidatas_2 = linea_leida_texto;
                    } else {
                        ret = false;
                        error[0] = "Lineas de variable repetidas. ";
                    }
                } else if (linea_leida_texto.contains(k_retornar)) { 
                    if (variables_candidatas_2 != null) {
                        variables_candidatas_2 = null;
                    } else {
                        variables_candidatas_1 = null;
                        subacciones_lista.clear();
                    }
                }
                if (ret == false) {
                    break;
                }
            }
            if (ret) {
                if (variables_candidatas_1 != null) {
                    if (parametros_candidatos_1 != null) {
                        parametros_y_variables[0] = parametros_candidatos_1.trim();
                    }
                    variables_candidatas_1 = variables_candidatas_1.replace(k_html_nueva_linea, "");
                    variables_linea[0] = variables_candidatas_1;
                    pos = variables_candidatas_1.indexOf(k_variable + k_sufijo_span);
                    pos = pos + k_variable.length() + k_sufijo_span.length();
                    variables_candidatas_1 = variables_candidatas_1.substring(pos);
                    if (parametros_y_variables[0].trim().isEmpty() == false
                            && variables_candidatas_1.trim().isEmpty() == false) {
                        parametros_y_variables[0] = parametros_y_variables[0] + k_coma;
                    }
                    parametros_y_variables[0] = parametros_y_variables[0] + variables_candidatas_1;
                }
                if (variables_candidatas_2 != null) {
                    if (parametros_candidatos_2 != null) {
                        parametros_y_variables[0] = parametros_y_variables[0] + parametros_candidatos_2.trim();
                    }
                    variables_candidatas_2 = variables_candidatas_2.replace(k_html_nueva_linea, "");
                    variables_linea[0] = variables_candidatas_2;
                    pos = variables_candidatas_2.indexOf(k_variable + k_sufijo_span);
                    pos = pos + k_variable.length();
                    variables_candidatas_2 = variables_candidatas_2.substring(pos);
                    if (parametros_y_variables[0].trim().isEmpty() == false
                            && variables_candidatas_2.trim().isEmpty() == false) {
                        parametros_y_variables[0] = parametros_y_variables[0] + k_coma;
                    }
                    parametros_y_variables[0] = parametros_y_variables[0] + variables_candidatas_2;
                }
                for (String subaccion_texto: subacciones_lista) {
                    pos = subaccion_texto.indexOf(k_parentesis_abrir.trim());
                    if (pos < 0) {
                        ret = false;
                        error[0] = "Subacción sin inicio de bloque de parametros. ";
                        break;
                    }
                    if (parametros_y_variables[0].trim().isEmpty() == false) {
                        parametros_y_variables[0] = parametros_y_variables[0] + k_coma;
                    }
                    parametros_y_variables[0] = parametros_y_variables[0] + subaccion_texto.substring(0, pos);
                }
            }
        } catch (Exception e) {
            error[0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en buscar_parametros_y_variables_en_accion. " + error[0];
            ret = false;
        }
        return ret;
    }
    
    public static String extraer_parametros(String linea, String [] error) {
        boolean ret = true;
        String retorno = null;
        int pos_inicio;
        int pos_fin;
        pos_inicio = linea.indexOf(k_parentesis_abrir.trim());
        if (pos_inicio < 0) {
            ret = false;
            error[0] = "No se encuentra el inicio de los parámetros. ";
        } else {
            pos_inicio = pos_inicio + k_parentesis_abrir.trim().length();
        }
        pos_fin = linea.indexOf(k_parentesis_cerrar.trim());
        if (pos_fin < 0) {
            ret = false;
            error[0] = "No se encuentra el final de los parámetros. ";
        }
        if (ret) {
            retorno = linea.substring(pos_inicio, pos_fin);
            retorno = retorno.replace(k_puntos_suspensivos.trim(), "");
            retorno = retorno.replace(k_html_espacio, "");
        }
        return retorno;
    }

    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        URL url;
        String nueva_linea = "";
        String linea_mapa = "";
        String previo_texto = null;
        String posterior_texto = null;
        textos error_texto = new textos("");
        String texto_final;
        textos final_texto = null;
        textos decorado_texto = null;
        String espacios;
        int espacios_num;
        int i;
        String relleno = "";
        String relleno_corto = "";
        int linea;
        String nombre = "";
        String valor = null;
        String sufijo = null;
        String [] error_local =  { "" };
        String variables_texto = null;
        String buscar_variables_texto;
        try {
            error[0] = "";
            nombre = (String) objects_mapa.get("nombre");
            ret = ret && validar_variable(nombre, error_local);
            error[0] = error[0] + error_local[0];
            error_local[0] = "";
            if (sufijo == null) {
                valor = (String) objects_mapa.get("valor_entero");
                if (valor != null && valor.trim().isEmpty() == false) {
                    try {
                        Integer.valueOf(valor);
                    } catch (Exception e) {
                        ret = false;
                        error[0] = error[0] + "No se introducido un entero válido. ";
                    }
                    sufijo = "en";
                }
            }
            if (sufijo == null) {
                valor = (String) objects_mapa.get("valor_decimal");
                if (valor != null && valor.trim().isEmpty() == false) {
                    valor = valor.replace(",", ".");
                    try {
                        Double.valueOf(valor);
                    } catch (Exception e) {
                        ret = false;
                        error[0] = error[0] + "No se introducido un decimal válido. ";
                    }
                    sufijo = "de";
                }
            }
            if (sufijo == null) {
                valor = (String) objects_mapa.get("bool");
                if (valor != null && valor.trim().isEmpty() == false) {
                    sufijo = "bo";
                }
            }
            if (sufijo == null) {
                valor = k_ente_nulo;
                sufijo = (String) objects_mapa.get("sufijo");
                if (sufijo == null) {
                    sufijo = "";
                }
            }
            if (ret) {
                espacios = (String) objects_mapa.get(k_mapa_espacios_num);
                espacios_num = Integer.valueOf(espacios);
                espacios_num = espacios_num - k_indentado.length();
                i = 0;
                while (true) {
                    if (i >= espacios_num) {
                        break;
                    }
                    relleno_corto = relleno_corto + " ";
                    i = i + 1;
                }
                relleno = relleno_corto + k_indentado;
            }
            if (ret) {
                nueva_linea = relleno + nombre + ".poner" + k_parentesis_abrir + valor + k_parentesis_cerrar + "\n";
                decorado_texto = new textos();
                ret = Decoraciones.decorar_subtexto(nueva_linea, decorado_texto, error_texto);
            }
            if (ret) {
                linea_mapa = (String) objects_mapa.get(k_mapa_linea_numero);
                previo_texto = contexto.leer(k_contexto_previo).dar();
                if (previo_texto == null) {
                    ret = false;
                    error[0] = "No se ha encontrado el texto previo en el contexto. ";
                }
            }
            if (ret) {
                posterior_texto = contexto.leer(k_contexto_posterior).dar();
                if (posterior_texto == null) {
                    ret = false;
                    error[0] = "No se ha encontrado el texto posterior en el contexto. ";
                }
            }
            if (ret) {
                variables_texto = contexto.leer(k_contexto_variables_linea).dar();
                if (variables_texto == null) {
                    ret = false;
                    error[0] = "No se han encontrado las variables declaradas. ";
                }
            }
            if (ret) {
                buscar_variables_texto = variables_texto + k_coma;
                if (buscar_variables_texto.contains(nombre + k_coma)) {
                    ret = false;
                    error[0] = "Ya existe la variable: " + nombre + ". ";
                }
            }
            if (ret) {
                previo_texto = insertar_nombre_variable(nombre, previo_texto, variables_texto, error);
                ret = (previo_texto != null);
            }
            if (ret) {
                texto_final= previo_texto + decorado_texto.dar();
                texto_final= texto_final + posterior_texto;
                final_texto = new textos(texto_final);
                ret = actualizar_numeros_linea(final_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                ret = contexto.modificar(k_contexto_archivo_abierto, final_texto.dar()).es();
            }
            if (ret) {
                linea = Integer.valueOf(linea_mapa);
                linea_mapa = Decoraciones.crear_formato_marca_linea(linea);
                ret = poner_url_ref_a_contenido(k_prefijo_ancla_linea + linea_mapa, objects_mapa, error);
            }
            if (ret) {
                objects_mapa.put(k_mapa_editar_archivos_error, "");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (ret == false) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_variables, k_protocolo_por_defecto, error);
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                objects_mapa.put(k_mapa_autoformularios_presentar, "");
                objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
                ret = poner_redireccion(contexto, url, true, null, error);
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.crear_variables. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static String insertar_nombre_variable(String nombre, String previo_texto, String variables_texto, String [] error) {
        boolean ret = true;
        String retorno = null;
        String [] variables_array;
        String linea = "";
        String solo_variables_texto;
        String inicio_texto;
        int pos;
        pos = variables_texto.indexOf(k_variable + k_sufijo_span);
        pos = pos + k_variable.length() + k_sufijo_span.length();
        inicio_texto = variables_texto.substring(0, pos);
        solo_variables_texto = variables_texto.substring(pos) + k_coma + nombre;
        variables_array = solo_variables_texto.split(k_coma);
        TreeSet <String> treeset;
        treeset = new TreeSet();
        for (String variable: variables_array) {
            variable = variable.trim();
            treeset.add(variable);
        }
        for (String variable: treeset) {
            if (linea.trim().isEmpty() == false) {
                linea = linea + k_coma;
            }
            linea = linea + variable;
        }
        linea = inicio_texto + " "  + linea;
        retorno = previo_texto.replace(variables_texto, linea);
        return retorno;
    }
}
