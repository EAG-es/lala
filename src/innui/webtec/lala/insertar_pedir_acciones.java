/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.archivos.Utf8;
import innui.contextos.contextos;
import innui.contextos.textos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.actualizar_numeros_linea;
import static innui.html.lala.Decoraciones.k_accion;
import static innui.html.lala.Decoraciones.k_accion_local;
import static innui.html.lala.Decoraciones.k_coma;
import static innui.html.lala.Decoraciones.k_comentario_doc_inicio;
import static innui.html.lala.Decoraciones.k_comentario_fin;
import static innui.html.lala.Decoraciones.k_comentario_inicio;
import static innui.html.lala.Decoraciones.k_contra;
import static innui.html.lala.Decoraciones.k_fin_bloque_si;
import static innui.html.lala.Decoraciones.k_html_nueva_linea;
import static innui.html.lala.Decoraciones.k_indentado;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_parentesis_abrir;
import static innui.html.lala.Decoraciones.k_parentesis_cerrar;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import static innui.html.lala.Decoraciones.k_si;
import static innui.html.lala.Decoraciones.k_subaccion;
import static innui.html.lala.Decoraciones.k_sufijo_span;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import java.util.Map;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.crear_proyectos.k_prefijo_acciones;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import java.net.URL;
import static innui.webtec.lala.insertar_variables.buscar_parametros_y_variables_en_accion;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_espacios_num;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import static innui.webtec.lala.insertar_nivel_codigos.k_codigo_contra_si_condicion;
import static innui.webtec.lala.insertar_nivel_codigos.k_codigo_si_condicion;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static innui.webtec.lala.insertar_nivel_codigos.k_ruta_insertar_pedir_accion;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class insertar_pedir_acciones extends A_ejecutores {
    public static String k_mapa_buscar_accion_boton = "buscar_accion_boton"; //NOI18N
    public static String k_mapa_buscar_accion = "buscar_accion"; //NOI18N
    public static String k_mapa_acciones = "acciones"; //NOI18N
    public static String k_mapa_buscar_semiconstante_boton = "buscar_semiconstante_boton"; //NOI18N
    public static String k_mapa_acciones_archivo = "acciones_archivo"; //NOI18N
    public static String k_contexto_acciones_archivo = k_mapa_acciones_archivo;
    public static String k_mapa_variables_accion = "variables_accion"; //NOI18N
    public static String k_contexto_variables_accion = k_mapa_variables_accion;
    public static String k_mapa_accion = "innui_webtec_lala_accion"; //NOI18N
    public static String k_mapa_parametro_y_variable = "innui_webtec_lala_parametro_y_variable"; //NOI18N
    public static String k_mapa_instruccion = "innui_webtec_lala_instruccion"; //NOI18N
    public static String k_mapa_tipo_codigo = "tipo_codigo";
    public static String k_prefijo_pedir_accion = "<a class=\"innui_webtec_gui_menu_aplicaciones_a\" href='' onclick='";
    public static String k_nombre_onclick_accion = "innui_html_lala_onclick_pedir_accion (\"accion\", \"";
    public static String k_nombre_onclick_parametro_y_variable = "innui_html_lala_onclick_pedir_accion (\"parametro_y_variable\", \"";
    public static String k_presufijo_pedir_accion = "\"); return false'>";
    public static String k_sufijo_pedir_accion = "</a>";
    public static String k_ruta_pedir_acciones = "/lala/insertar_pedir_acciones";    
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
        String accion;
        String parametro_y_variable;
        String presentar;
        String buscar_accion_boton;
        String tipo_codigo;
        boolean es_procesar = false;
        boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
        accion = (String) objects_mapa.get(k_mapa_accion);
        if (accion == null) {
            accion = "";
        }
        parametro_y_variable = (String) objects_mapa.get(k_mapa_parametro_y_variable);
        if (parametro_y_variable == null) {
            parametro_y_variable = "";
        }
        tipo_codigo = (String) objects_mapa.get(k_mapa_tipo_codigo);
        presentar = (String) objects_mapa.get(k_mapa_autoformularios_presentar);        
        buscar_accion_boton = (String) objects_mapa.get(k_mapa_buscar_accion_boton);
        if ((boton_enviar_pulsado == null
                && accion.trim().isEmpty())
                || buscar_accion_boton != null
                || presentar != null) {
            if (parametro_y_variable.trim().isEmpty() == false
                    && tipo_codigo != null) {
                es_procesar = true;
            }
            if (es_procesar == false) {
                ret = presentar(objects_mapa, error);
            }
        } else {
            es_procesar = true;
        }
        if (es_procesar) {
            ret = procesar(objects_mapa, error);
        }
        return ret;
    }

    public boolean presentar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        autoformularios autoformulario;
        String previo_texto = null;
        String posterior_texto = null;
        String [] parametros_y_variables = { "" };
        String [] variables_linea = { "" };
        String acciones_texto = null;
        URL url;
        String parametros_y_variables_texto;
        String parametro_y_variable;
        String accion;
        String buscar_accion_boton;
        String acciones_encontradas_texto = "";
        String palabras_clave;
        String tipo_codigo;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            if (ret) {
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
                buscar_accion_boton = (String) objects_mapa.get(k_mapa_buscar_accion_boton);
                if (buscar_accion_boton != null) {
                    palabras_clave = (String) objects_mapa.get(k_mapa_buscar_accion);
                    acciones_encontradas_texto = buscar_acciones(contexto, palabras_clave, objects_mapa, error);
                    ret = (acciones_encontradas_texto != null);
                    if (ret) {
                        acciones_encontradas_texto = acciones_encontradas_texto.replace("\n", k_html_nueva_linea + "\n");
                    }
                }
            }
            if (ret) {
                objects_mapa.put(k_mapa_acciones, acciones_encontradas_texto);
                accion = (String) objects_mapa.get(k_mapa_accion);
                if (accion == null || accion.trim().isEmpty()) {
                    acciones_texto = buscar_acciones_en_texto(previo_texto + posterior_texto, error);
                    ret = (acciones_texto != null);
                    if (ret) {
                        acciones_texto = acciones_texto.replace("\n", k_html_nueva_linea + "\n");
                        objects_mapa.put(k_mapa_acciones_archivo, acciones_texto);
                        contexto.fondear_con_datos(k_contexto_acciones_archivo, acciones_texto);
                    }
                } else {
                    acciones_texto = contexto.leer(k_contexto_acciones_archivo).dar();
                    objects_mapa.put(k_mapa_acciones_archivo, acciones_texto);
                }
            }
            if (ret) {
                parametro_y_variable = (String) objects_mapa.get(k_mapa_parametro_y_variable);
                if (parametro_y_variable == null || parametro_y_variable.trim().isEmpty()) {
                    ret = buscar_parametros_y_variables_en_accion(previo_texto, parametros_y_variables, variables_linea, error);
                    if (ret) {
                        ret = preparar_parametros_y_variables_en_accion(parametros_y_variables, error);
                    }
                    if (ret) {
                        parametros_y_variables_texto = parametros_y_variables[0].replace("\n", k_html_nueva_linea + "\n");
                        objects_mapa.put(k_mapa_variables_accion, parametros_y_variables_texto);
                        contexto.fondear_con_datos(k_contexto_variables_accion, parametros_y_variables_texto);
                    }
                } else {
                    parametros_y_variables_texto = contexto.leer(k_contexto_variables_accion).dar();
                    objects_mapa.put(k_mapa_variables_accion, parametros_y_variables_texto);
                    tipo_codigo = (String) objects_mapa.get(k_mapa_tipo_codigo);
                    if (tipo_codigo == null) {
                        ret = false;
                        error[0] = "No se ha indicado un método común para el atributo o variable seleccionada. ";
                    }
                }
            }
            objects_mapa.put(k_mapa_texto_previo, previo_texto); 
            objects_mapa.put(k_mapa_texto_posterior, posterior_texto);                 
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            url = Urls.completar_URL(k_prefijo_url + k_ruta_insertar_pedir_accion, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            ret = autoformulario.ejecutar(objects_mapa, error);
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en presentar.pedir_acciones. " + error[0];
            ret = false;
        }
        return ret;
    }    
    
    public static String buscar_acciones_en_texto(String texto, String [] error) {
        boolean ret = true;
        String retorno = "";
        String [] fragmentos_array;
        int pos;
        int pos_local;
        int fin_linea;
        String subtexto;
        String previo_texto;
        String accion_texto;
        textos desdecorado_texto = null;
        textos error_texto = null;
        String datos_accion;
        desdecorado_texto = new textos();
        error_texto = new textos();
        fragmentos_array = texto.split("/\\*\\*");       
        for (String fragmento: fragmentos_array) {
            pos = fragmento.indexOf(k_accion + k_sufijo_span);
            pos_local = fragmento.indexOf(k_accion_local + k_sufijo_span);
            if (pos_local >= 0) {
                pos = pos_local + k_accion_local.length() + k_sufijo_span.length();
            } else if (pos >= 0) {
                pos = pos + k_accion.length() + k_sufijo_span.length();
            }
            if (pos >= 0) {
                previo_texto = fragmento.substring(0, pos);
                subtexto = fragmento.substring(pos);
                fin_linea = subtexto.indexOf("\n");
                datos_accion = subtexto.substring(0, fin_linea);
                accion_texto = k_prefijo_pedir_accion + k_nombre_onclick_accion + datos_accion + k_presufijo_pedir_accion 
                        + k_comentario_doc_inicio
                        + previo_texto
                        + datos_accion + k_sufijo_pedir_accion + "\n";
                retorno = retorno + accion_texto;
            }
        }
        if (ret) {
            ret = Decoraciones.desdecorar(retorno, desdecorado_texto, error_texto);
            error[0] = error_texto.dar();
        }
        if (ret) {
            retorno = desdecorado_texto.dar();
        } else {
            retorno = null;
        }
        return retorno;
    }
    
    public static boolean preparar_parametros_y_variables_en_accion(String [] parametros_y_variables, String [] error) {
        boolean ret = true;
        String [] fragmentos_array;
        String parametro_y_variable_texto = "";
        textos desdecorado_texto;
        textos error_texto;
        desdecorado_texto = new textos();
        error_texto = new textos();
        ret = Decoraciones.desdecorar(parametros_y_variables[0], desdecorado_texto, error_texto);
        error[0] = error_texto.dar();
        if (ret) {
            parametros_y_variables[0] = desdecorado_texto.dar();
            fragmentos_array = parametros_y_variables[0].split(k_coma.trim());
            for (String fragmento: fragmentos_array) {
                fragmento = fragmento.trim();
                if (parametro_y_variable_texto.trim().isEmpty() == false) {
                    parametro_y_variable_texto = parametro_y_variable_texto + k_coma;
                }
                parametro_y_variable_texto = parametro_y_variable_texto 
                        + k_prefijo_pedir_accion + k_nombre_onclick_parametro_y_variable 
                        + fragmento + k_presufijo_pedir_accion 
                        + fragmento + k_sufijo_pedir_accion;
            }
            parametros_y_variables[0] = parametro_y_variable_texto + k_html_nueva_linea + "\n"; 
        }
        return ret;
    }

    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        URL url;
        int linea;
        String linea_mapa = null;
        String espacios;
        String relleno = "";
        String relleno_corto = "";
        int espacios_num;
        String previo_texto = null;
        String posterior_texto = null;
        String nueva_linea = null;
        textos decorado_texto = null;
        textos final_texto = null;
        textos error_texto = null;
        String accion = null;
        String texto_final = null;
        String parametro_y_variable = null;
        String tipo_codigo = null;
        String texto = "";
        int i;
        String mapa_instruccion;
        int pos;
        String primer_texto;
        String ultimo_texto;
        try {
            accion = (String) objects_mapa.get(k_mapa_accion);
            if (accion == null || accion.trim().isEmpty()) {
                parametro_y_variable = (String) objects_mapa.get(k_mapa_parametro_y_variable);
                if (parametro_y_variable == null || parametro_y_variable.trim().isEmpty()) {
                    ret = false;
                    error[0] = "No hay acción, ni parámetro, ni variable, seleccionados. ";
                }
            }
            if (ret) {
                tipo_codigo = (String) objects_mapa.get(k_mapa_tipo_codigo);
                espacios = (String) objects_mapa.get(k_mapa_espacios_num);
                espacios_num = Integer.valueOf(espacios) - k_indentado.length();
                i = 0;
                while (true) {
                    if (i >= espacios_num) {
                        break;
                    }
                    relleno_corto = relleno_corto + " ";
                    i = i + 1;
                }
                relleno = relleno_corto + k_indentado;
                if (accion != null && accion.trim().isEmpty() == false) {
                    accion = accion.replace(k_parentesis_abrir, k_parentesis_abrir + k_comentario_inicio );
                    accion = accion.replace(k_coma, k_comentario_fin + k_coma + k_comentario_inicio );
                    texto = accion.replace(k_parentesis_cerrar, k_comentario_fin + k_parentesis_cerrar );
                    if (tipo_codigo != null) {
                        pos = texto.lastIndexOf(k_parentesis_cerrar.trim());
                        if (pos < 0) {
                            ret = false;
                            error[0] = "La acción seleccionada no es válida. ";
                        }
                        if (ret) {
                            pos = pos + k_parentesis_cerrar.trim().length();
                            primer_texto = texto.substring(0, pos);
                            ultimo_texto = texto.substring(pos);
                            texto = primer_texto + tipo_codigo + ultimo_texto;
                        }
                    }
                } else {
                    if (tipo_codigo == null) {
                        ret = false;
                        error[0] = "No se ha indicado un método común para el atributo o variable seleccionada. ";
                    }
                    if (ret) {
                        texto = parametro_y_variable + tipo_codigo;
                    }
                }
            }
            if (ret) {
                mapa_instruccion = (String) objects_mapa.get(k_mapa_instruccion);
                if (mapa_instruccion.equals(k_codigo_si_condicion)) {
                    texto = relleno + k_si + texto + "\n"
                            + relleno + k_fin_bloque_si;
                } else if (mapa_instruccion.equals(k_codigo_contra_si_condicion)) {
                    texto = relleno_corto + k_contra + " " + k_si + texto;
                } else {
                    texto = relleno + texto;
                }
                nueva_linea = texto + "\n";
                decorado_texto = new textos();
                error_texto = new textos();
                ret = Decoraciones.decorar_subtexto(nueva_linea, decorado_texto, error_texto);
                error[0] = error_texto.dar();                
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
                url = Urls.completar_URL(k_prefijo_url + k_ruta_pedir_acciones, k_protocolo_por_defecto, error);
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
            error[0] = "Error en procesar.pedir_acciones. " + error[0];
            ret = false;
        }
        return ret;
    }
    
    public static String buscar_acciones(contextos contexto, String palabras_clave, Map<String, Object> objects_mapa, String [] error) {
        boolean ret = true;
        String retorno = null;
        List <String> busquedas_list = new ArrayList();
        Map<String, String> configuraciones_mapa = null;
        String ruta_seleccionada = null;
        File file;
        File [] files_array;
        String encontrado_texto;
        ret = generar_busquedas(palabras_clave, busquedas_list, error);
        if (ret) {
            configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
            ret = (configuraciones_mapa != null);
        }
        if (ret) {
            ruta_seleccionada = configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            file = new File(ruta_seleccionada);
            file = file.getParentFile();
            files_array = file.listFiles(new FileFilter () {
                @Override
                public boolean accept(File pathname) {
                    boolean ret = true;
                    String nombre;
                    nombre = pathname.getName();
                    if (nombre.startsWith(k_prefijo_acciones) == false) {
                        ret = false;
                    }
                    if (ret) {
                        if (nombre.endsWith(k_extension_lala) == false) {
                            ret = false;
                        }
                    }
                    return ret;
                }
            });
            retorno = "";
            for (File file_accion: files_array) {
                encontrado_texto = buscar_acciones_en_archivo(file_accion, busquedas_list, error);
                ret = (encontrado_texto != null);
                if (ret == false) {
                    break;
                }
                retorno = retorno + encontrado_texto;
            }
        }
        return retorno;
    }
    
    public static String buscar_acciones_en_archivo(File file_accion, List <String> busquedas_list, String [] error) {
        boolean ret = true;
        String retorno = null;
        String texto;
        String [] fragmentos_array;
        List <String> fragmentos_lista;
        String texto_donde_buscar;
        Pattern pattern = null;
        Matcher matcher = null;
        String hipervinculo;
        try {
            texto = Utf8.leer(file_accion, error);
            ret = (texto != null);
            if (ret) {
                fragmentos_array = texto.split("/\\*\\*");       
                fragmentos_lista = new ArrayList(Arrays.asList(fragmentos_array));
                if (fragmentos_array.length > 0) {
                    if (fragmentos_lista.get(0).trim().isEmpty()) {
                        fragmentos_lista.remove(0);
                    }
                }
                retorno = "";
                for (String busqueda: busquedas_list) {
                    pattern = Pattern.compile(busqueda);
                    int i = 0;
                    int tam = fragmentos_lista.size();
                    while (true) {
                        if (i >= tam) {
                            break;
                        }
                        texto_donde_buscar = fragmentos_lista.get(i);
                        matcher = pattern.matcher(texto_donde_buscar);
                        if (matcher.find(0)) {
                            hipervinculo = generar_hipervinculo_accion(texto_donde_buscar, error);
                            ret = (hipervinculo != null);
                            if (ret) {
                                retorno = retorno + hipervinculo;
                                fragmentos_lista.remove(i);
                                i = i - 1;
                                tam = tam - 1;
                            }
                        }
                        if (ret == false) {
                            break;
                        }
                        i = i + 1;
                    }
                }
            }
            if (ret = false) {
                retorno = null;
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.buscar_acciones_en_archivo. " + error[0];
            ret = false;
            retorno = null;
        }
        return retorno;
    }

    public static String generar_hipervinculo_accion(String texto, String [] error) {
        String retorno = "";
        boolean ret = true;
        int pos;
        int pos_local;
        int fin_linea;
        String previo_texto;
        String subtexto;
        String datos_accion;
        pos = texto.indexOf(k_accion);
        if (pos < 0) {
            pos = texto.indexOf(k_subaccion);
            if (pos >= 0) {
                pos = pos + k_subaccion.length();
            }
        } else {
            pos_local = texto.indexOf(k_accion_local);
            if (pos_local >= 0) {
                pos = pos_local + k_accion_local.length();
            } else {
                pos = pos + k_accion.length();
            }
        }
        if (pos >= 0) {
            previo_texto = texto.substring(0, pos);
            subtexto = texto.substring(pos);
            fin_linea = subtexto.indexOf("\n");
            datos_accion = subtexto.substring(0, fin_linea);
            retorno = k_prefijo_pedir_accion + k_nombre_onclick_accion + datos_accion + k_presufijo_pedir_accion 
                    + k_comentario_doc_inicio
                    + previo_texto
                    + datos_accion + k_sufijo_pedir_accion + "\n";
        }
        return retorno;
    }

    public static boolean generar_busquedas(String palabras_clave, List <String> busquedas_list, String [] error) {
        boolean ret = true;
        String [] fragmentos_array;
        String grupo_0;
        String grupo_1;
        String grupo_2;
        fragmentos_array = palabras_clave.split(k_coma.trim());
        if (fragmentos_array.length > 3) {
            ret = false;
            error[0] = "La busqueda está limitada a 3 grupos de palabras clave (separados por: ,) ";
        }
        if (ret) {
            if (fragmentos_array.length == 1) {
                grupo_0 = fragmentos_array[0].trim();
                busquedas_list.add("(?m)" + grupo_0);
            } else if (fragmentos_array.length == 2) {
                grupo_0 = fragmentos_array[0].trim();
                grupo_1 = fragmentos_array[1].trim();
                busquedas_list.add("(?m)" + grupo_0 + ".+" + grupo_1);
                busquedas_list.add("(?m)" + grupo_1 + ".+" + grupo_0);
                busquedas_list.add("(?m)" + grupo_0);
                busquedas_list.add("(?m)" + grupo_1);
            } else if (fragmentos_array.length == 3) {
                grupo_0 = fragmentos_array[0].trim();
                grupo_1 = fragmentos_array[1].trim();
                grupo_2 = fragmentos_array[2].trim();
                busquedas_list.add("(?m)" + grupo_0 + ".+" + grupo_1 + ".+" + grupo_2);
                busquedas_list.add("(?m)" + grupo_0 + ".+" + grupo_2 + ".+" + grupo_1);
                busquedas_list.add("(?m)" + grupo_1 + ".+" + grupo_0 + ".+" + grupo_2);
                busquedas_list.add("(?m)" + grupo_1 + ".+" + grupo_2 + ".+" + grupo_0);
                busquedas_list.add("(?m)" + grupo_2 + ".+" + grupo_0 + ".+" + grupo_1);
                busquedas_list.add("(?m)" + grupo_2 + ".+" + grupo_1 + ".+" + grupo_0);
                busquedas_list.add("(?m)" + grupo_0 + ".+" + grupo_1);
                busquedas_list.add("(?m)" + grupo_0 + ".+" + grupo_2);
                busquedas_list.add("(?m)" + grupo_1 + ".+" + grupo_0);
                busquedas_list.add("(?m)" + grupo_1 + ".+" + grupo_2);
                busquedas_list.add("(?m)" + grupo_2 + ".+" + grupo_0);
                busquedas_list.add("(?m)" + grupo_2 + ".+" + grupo_1);
                busquedas_list.add("(?m)" + grupo_0);
                busquedas_list.add("(?m)" + grupo_1);
                busquedas_list.add("(?m)" + grupo_2);
            }
        }
        return ret;
    }

}
