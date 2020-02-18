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
import static innui.html.lala.Decoraciones.k_indentado;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import static innui.webtec.lala.insertar_lineas.k_mapa_espacios_num;
import static innui.webtec.lala.procesar_abrir_archivos.k_contexto_archivo_abierto;
import java.net.URL;
import java.util.Map;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import static innui.html.lala.Decoraciones.k_sufijo_span;
import static innui.html.lala.Decoraciones.k_variable;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.crear_variables.k_contexto_variables_datos_previos;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.procesar_insertar_nivel_acciones.validar_variable;
import java.util.TreeSet;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_crear_variables extends A_ejecutores {
    public static String k_mapa_procesar_crear_variables = "innui_webtec_lala_procesar_crear_variables";
    public static String k_mapa_procesar_crear_variables_errores = "innui_webtec_lala_insertar_nivel_codigos_errores";
    public static String k_ruta_editar_archivos = "/lala/editar_archivos";
    public static String k_lista_separador = ", ";
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
        try {
            error[0] = "";
            objects_mapa.remove(k_mapa_procesar_crear_variables_errores);
            nombre = (String) objects_mapa.get("nombre");
            ret = ret && validar_variable(nombre, error_local);
            error[0] = error[0] + error_local[0];
            valor = (String) objects_mapa.get("valor_texto");
            if (valor != null && valor.isEmpty() == false) {
                sufijo = "te";
            }
            if (sufijo == null) {
                valor = (String) objects_mapa.get("valor_entero");
                if (valor != null && valor.isEmpty() == false) {
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
                if (valor != null && valor.isEmpty() == false) {
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
                if (valor != null && valor.isEmpty() == false) {
                    sufijo = "bo";
                }
            }
            if (sufijo == null) {
                valor = "ente_nulo";
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
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                autoformulario_error = new autoformularios_errores();
                autoformulario_error.configurar(contexto);
                ret = autoformulario_error.ejecutar(objects_mapa, error);
                innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
                objects_mapa.put(k_mapa_procesar_crear_variables_errores, innui_webtec_gui_autoformularios_errores);
            } else {
                nueva_linea = relleno + valor + " = " + valor + " \n";
                decorado_texto = new textos();
                ret = Decoraciones.decorar_subtexto(nueva_linea, decorado_texto, error_texto);
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
                    if (previo_texto == null) {
                        ret = false;
                        error[0] = "No se ha encontrado el texto posterior en el contexto. ";
                    }
                }
                if (ret) {
                    variables_texto = contexto.fondear_con_datos(k_contexto_variables_datos_previos).dar();
                    if (previo_texto == null) {
                        ret = false;
                        error[0] = "No se han encontrado las variables declaradas. ";
                    }
                }
                if (ret) {
                    variables_texto = variables_texto + k_lista_separador;
                    if (variables_texto.contains(nombre + k_lista_separador)) {
                        ret = false;
                        error[0] = "Ya existe la variable: " + nombre + ". ";
                    }
                }
                if (ret) {
                    variables_texto = variables_texto + nombre;
                    previo_texto = insertar_nombre_variable(previo_texto, variables_texto, error);
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
                    ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
                    objects_mapa.put(k_mapa_procesar_crear_variables, error[0]);
                }
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.procesar_insertar_nivel_codigos. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static String insertar_nombre_variable(String previo_texto, String variables_texto, String [] error) {
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
        solo_variables_texto = variables_texto.substring(pos);
        variables_array = solo_variables_texto.split(k_lista_separador);
        TreeSet <String> treeset;
        treeset = new TreeSet();
        for (String variable: variables_array) {
            variable = variable.trim();
            treeset.add(variable);
        }
        for (String variable: treeset) {
            if (linea.isEmpty() == false) {
                linea = linea + k_lista_separador;
            }
            linea = linea + variable;
        }
        linea = inicio_texto + " "  + linea;
        retorno = previo_texto.replace(variables_texto, linea);
        return retorno;
    }
}
