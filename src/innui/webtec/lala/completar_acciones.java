/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import static innui.archivos.Rutas.aumentar_ruta;
import innui.archivos.Utf8;
import innui.contextos.contextos;
import innui.contextos.textos;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.actualizar_numeros_linea;
import static innui.html.lala.Decoraciones.k_coma;
import static innui.html.lala.Decoraciones.k_comentario_fin;
import static innui.html.lala.Decoraciones.k_comentario_inicio;
import static innui.html.lala.Decoraciones.k_corchete_abrir;
import static innui.html.lala.Decoraciones.k_corchete_cerrar;
import static innui.html.lala.Decoraciones.k_html_nueva_linea;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_parentesis_abrir;
import static innui.html.lala.Decoraciones.k_parentesis_cerrar;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import static innui.html.lala.Decoraciones.k_rompe_linea;
import innui.json.Textos;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.chunk.Procesamientos.k_infijo_in_;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import java.util.Map;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_proyectos.k_carpeta_importable;
import static innui.webtec.lala.crear_proyectos.k_carpeta_innui;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.crear_proyectos.k_prefijo_atributos;
import static innui.webtec.lala.crear_proyectos.k_prefijo_semiconstantes;
import java.net.URL;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.insertar_lineas.k_contexto_linea;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_espacios_num;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static innui.webtec.lala.insertar_pedir_acciones.buscar_acciones;
import static innui.webtec.lala.insertar_pedir_acciones.buscar_acciones_en_texto;
import static innui.webtec.lala.insertar_pedir_acciones.generar_busquedas;
import static innui.webtec.lala.insertar_pedir_acciones.k_contexto_acciones_archivo;
import static innui.webtec.lala.insertar_pedir_acciones.k_contexto_variables_accion;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_accion;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_acciones;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_acciones_archivo;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_buscar_accion;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_buscar_accion_boton;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_parametro_y_variable;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_tipo_codigo;
import static innui.webtec.lala.insertar_pedir_acciones.k_mapa_variables_accion;
import static innui.webtec.lala.insertar_pedir_acciones.k_prefijo_pedir_accion;
import static innui.webtec.lala.insertar_pedir_acciones.k_presufijo_pedir_accion;
import static innui.webtec.lala.insertar_pedir_acciones.k_sufijo_pedir_accion;
import static innui.webtec.lala.insertar_pedir_acciones.preparar_parametros_y_variables_en_accion;
import static innui.webtec.lala.insertar_variables.buscar_parametros_y_variables_en_accion;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import java.util.Locale;
import static innui.webtec.lala.abrir_archivos.guardar_cambio;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class completar_acciones extends A_ejecutores {
    public static String k_nombre_onclick_atributo = "innui_html_lala_onclick_pedir_accion (\"atributo\", \""; //NOI18N
    public static String k_nombre_onclick_semiconstante = "innui_html_lala_onclick_pedir_accion (\"semiconstante\", \""; //NOI18N
    public static String k_ruta_completar_acciones = "/lala/completar_acciones"; //NOI18N
    public static String k_mapa_atributo = "innui_webtec_lala_atributo"; //NOI18N
    public static String k_mapa_semiconstante = "innui_webtec_lala_semiconstante"; //NOI18N
    public static String k_mapa_reemplazar = "innui_webtec_lala_reemplazar"; //NOI18N
    public static String k_mapa_buscar_atributo_boton = "buscar_atributo_boton"; //NOI18N
    public static String k_mapa_buscar_atributo = "buscar_atributo"; //NOI18N
    public static String k_mapa_atributos_encontrados = "atributos"; //NOI18N
    public static String k_mapa_buscar_semiconstante_boton = "buscar_semiconstante_boton"; //NOI18N
    public static String k_mapa_buscar_semiconstante = "buscar_semiconstantes"; //NOI18N
    public static String k_mapa_semiconstantes_encontradas = "semiconstantes"; //NOI18N
    public static String k_mapa_nuevo_parametro_boton = "nuevo_parametro_boton"; //NOI18N
    public static String k_mapa_nuevo_bloque_parametro_boton = "nuevo_bloque_parametro_boton"; //NOI18N
    public static String k_mapa_valor_entero = "valor_entero"; //NOI18N
    public static String k_mapa_valor_decimal = "valor_decimal"; //NOI18N
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
        String atributo;
        String semiconstante;
        String presentar;
        String buscar_accion_boton;
        String buscar_atributo_boton;
        String buscar_semiconstante_boton;
        String nuevo_bloque_parametro_boton;
        String nuevo_parametro_boton;
        boton_enviar_pulsado = (String) objects_mapa.get(k_mapa_autoformularios_enviar);
        accion = (String) objects_mapa.get(k_mapa_accion);
        if (accion == null) {
            accion = "";
        }
        parametro_y_variable = (String) objects_mapa.get(k_mapa_parametro_y_variable);
        if (parametro_y_variable == null) {
            parametro_y_variable = "";
        }
        atributo = (String) objects_mapa.get(k_mapa_atributo);
        if (atributo == null) {
            atributo = "";
        }
        semiconstante = (String) objects_mapa.get(k_mapa_semiconstante);
        if (semiconstante == null) {
            semiconstante = "";
        }
        buscar_accion_boton = (String) objects_mapa.get(k_mapa_buscar_accion_boton);
        buscar_atributo_boton = (String) objects_mapa.get(k_mapa_buscar_atributo_boton);
        buscar_semiconstante_boton = (String) objects_mapa.get(k_mapa_buscar_semiconstante_boton);
        nuevo_bloque_parametro_boton = (String) objects_mapa.get(k_mapa_nuevo_bloque_parametro_boton);
        nuevo_parametro_boton = (String) objects_mapa.get(k_mapa_nuevo_parametro_boton);
        presentar = (String) objects_mapa.get(k_mapa_autoformularios_presentar);        
        if ((boton_enviar_pulsado == null
                && nuevo_bloque_parametro_boton == null
                && nuevo_parametro_boton == null
                && accion.trim().isEmpty()
                && parametro_y_variable.trim().isEmpty()
                && atributo.trim().isEmpty()
                && semiconstante.trim().isEmpty())
                || buscar_accion_boton != null
                || buscar_atributo_boton != null
                || buscar_semiconstante_boton != null
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
        String previo_texto = null;
        String linea_texto = null;
        String posterior_texto = null;
        String [] parametros_y_variables = { "" };
        String [] variables_linea = { "" };
        String acciones_texto = null;
        URL url;
        String parametros_y_variables_texto = null;
        String parametro_y_variable;
        String accion;
        String buscar_accion_boton;
        String acciones_encontradas_texto = "";
        String palabras_clave;
        String buscar_atributo_boton;
        String atributos_encontrados_texto = "";
        String buscar_semiconstante_boton;
        String semiconstantes_encontradas_texto = "";
        textos linea_desdecorada_texto = new textos();
        textos error_texto = new textos();
        int pos_inicial = 0;
        int pos_final = 0;
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
                linea_texto = contexto.leer(k_contexto_linea).dar();
                if (linea_texto == null) {
                    ret = false;
                    error[0] = "No se ha encontrado la linea que procesar en el contexto. ";
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
                ret = Decoraciones.desdecorar(linea_texto, linea_desdecorada_texto, error_texto);
            }
            if (ret) {
                linea_texto = linea_desdecorada_texto.dar();
                pos_inicial = linea_texto.indexOf(k_comentario_inicio.trim());
                if (pos_inicial < 0) {
                    if (linea_texto.contains(k_parentesis_cerrar.trim()) == false) {
                        ret = false;
                        error[0] = "No hay parámetro que completar. ";
                    }
                }
            }
            if (ret) {
                pos_final  = linea_texto.indexOf(k_comentario_fin.trim());
                if (pos_final < 0) {
                    if (linea_texto.contains(k_parentesis_cerrar.trim()) == false) {
                        ret = false;
                        error[0] = "No hay parámetro que completar. ";
                    }
                }
            }
            if (ret) {
                pos_final = pos_final + k_comentario_fin.trim().length();
                linea_texto = linea_texto.substring(pos_inicial, pos_final);
                objects_mapa.put(k_mapa_reemplazar, linea_texto);
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
                        contexto.fondear_con_datos(k_contexto_acciones_archivo, acciones_texto);
                    }
                } else {
                    acciones_texto = contexto.leer(k_contexto_acciones_archivo).dar();
                }
            }
            if (ret) {
                objects_mapa.put(k_mapa_acciones_archivo, acciones_texto);
                parametro_y_variable = (String) objects_mapa.get(k_mapa_parametro_y_variable);
                if (parametro_y_variable == null || parametro_y_variable.trim().isEmpty()) {
                    ret = buscar_parametros_y_variables_en_accion(previo_texto, parametros_y_variables, variables_linea, error);
                    if (ret) {
                        ret = preparar_parametros_y_variables_en_accion(parametros_y_variables, error);
                    }
                    if (ret) {
                        parametros_y_variables_texto = parametros_y_variables[0].replace("\n", k_html_nueva_linea + "\n");
                        contexto.fondear_con_datos(k_contexto_variables_accion, parametros_y_variables_texto);
                    }
                } else {
                    parametros_y_variables_texto = contexto.leer(k_contexto_variables_accion).dar();
                }
            }
            if (ret) {
                objects_mapa.put(k_mapa_variables_accion, parametros_y_variables_texto);
                buscar_atributo_boton = (String) objects_mapa.get(k_mapa_buscar_atributo_boton);
                if (buscar_atributo_boton != null) {
                    palabras_clave = (String) objects_mapa.get(k_mapa_buscar_atributo);
                    atributos_encontrados_texto = buscar_atributo_y_semiconstante(true, contexto, palabras_clave, objects_mapa, error);
                    ret = (atributos_encontrados_texto != null);
                }
            }
            if (ret) {
                objects_mapa.put(k_mapa_atributos_encontrados, atributos_encontrados_texto);
                buscar_semiconstante_boton = (String) objects_mapa.get(k_mapa_buscar_semiconstante_boton);
                if (buscar_semiconstante_boton != null) {
                    palabras_clave = (String) objects_mapa.get(k_mapa_buscar_atributo);
                    semiconstantes_encontradas_texto = buscar_atributo_y_semiconstante(false, contexto, palabras_clave, objects_mapa, error);
                    ret = (semiconstantes_encontradas_texto != null);
                }
            }
            if (ret) {
                objects_mapa.put(k_mapa_semiconstantes_encontradas, semiconstantes_encontradas_texto);
            }
            objects_mapa.put(k_mapa_texto_previo, previo_texto); 
            objects_mapa.put(k_mapa_texto_posterior, posterior_texto);                 
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            url = Urls.completar_URL(k_prefijo_url + k_ruta_completar_acciones, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
            autoformulario = new autoformularios();
            autoformulario.configurar(contexto);
            ret = autoformulario.ejecutar(objects_mapa, error);
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en presentar.completar_acciones. " + error[0];
            ret = false;
        }
        return ret;
    }   
    
    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        URL url;
        int linea;
        String linea_mapa = null;
        String previo_texto = null;
        String linea_texto = null;
        String linea_texto_desdecorada = null;
        String posterior_texto = null;
        textos decorado_texto = null;
        textos desdecorado_texto = null;
        textos final_texto = null;
        textos error_texto = null;
        String accion = null;
        String texto_final = null;
        String parametro_y_variable = null;
        String tipo_codigo = null;
        String texto = "";
        int i;
        int pos;
        String primer_texto;
        String ultimo_texto;
        String atributo;
        String semiconstante;
        boolean sin_datos = true;
        String reemplazar = null;
        String nuevo_parametro_boton = null;
        String nuevo_bloque_parametro_boton = null;
        String espacios;
        int espacios_num;
        String relleno = "";
        String inicio_texto;
        String fin_texto;
        String valor_entero;
        String valor_decimal;
        boolean es_operacion_tras_parentesis = false;
        try {
            accion = (String) objects_mapa.get(k_mapa_accion);
            if (accion != null && accion.trim().isEmpty() == false) {
                sin_datos = false;
            }
            parametro_y_variable = (String) objects_mapa.get(k_mapa_parametro_y_variable);
            if (parametro_y_variable != null && parametro_y_variable.trim().isEmpty() == false) {
                sin_datos = false;
            }
            atributo  = (String) objects_mapa.get(k_mapa_atributo);
            if (atributo != null && atributo.trim().isEmpty() == false) {
                sin_datos = false;
                texto = atributo;
            }
            semiconstante  = (String) objects_mapa.get(k_mapa_semiconstante);
            if (semiconstante != null && semiconstante.trim().isEmpty() == false) {
                sin_datos = false;
                texto = semiconstante;
            }
            valor_entero  = (String) objects_mapa.get(k_mapa_valor_entero);
            if (valor_entero != null && valor_entero.trim().isEmpty() == false) {
                sin_datos = false;
                try {
                    texto = Integer.valueOf(valor_entero).toString();
                } catch (Exception e) {
                    ret = false;
                    error[0] = "Entero no válido. ";
                }
            }
            valor_decimal  = (String) objects_mapa.get(k_mapa_valor_decimal);
            if (valor_decimal != null && valor_decimal.trim().isEmpty() == false) {
                sin_datos = false;
                try {
                    valor_decimal = valor_decimal.replace(",", ".");
                    texto = Double.valueOf(valor_decimal).toString();
                } catch (Exception e) {
                    ret = false;
                    error[0] = "Fomato decimal no válido. ";
                }
            }
            nuevo_bloque_parametro_boton = (String) objects_mapa.get(k_mapa_nuevo_bloque_parametro_boton);
            nuevo_parametro_boton = (String) objects_mapa.get(k_mapa_nuevo_parametro_boton);
            tipo_codigo = (String) objects_mapa.get(k_mapa_tipo_codigo);
            if (sin_datos) {
                if (tipo_codigo != null
                  || nuevo_bloque_parametro_boton != null
                  || nuevo_parametro_boton != null) {
                    es_operacion_tras_parentesis = true;
                } else {
                    ret = false;
                    error[0] = "No hay datos para procesar la petición. ";
                }
            }            
            if (ret) {
                if ((atributo == null || atributo.trim().isEmpty())
                 && (semiconstante == null || semiconstante.trim().isEmpty())
                 && (valor_entero == null || valor_entero.trim().isEmpty())
                 && (valor_decimal == null || valor_decimal.trim().isEmpty())) {
                    espacios = (String) objects_mapa.get(k_mapa_espacios_num);
                    espacios_num = Integer.valueOf(espacios);
                    i = 0;
                    while (true) {
                        if (i >= espacios_num) {
                            break;
                        }
                        relleno = relleno + " ";
                        i = i + 1;
                    }
                    if (accion != null && accion.trim().isEmpty() == false) {
                        accion = accion.replace(k_parentesis_abrir, k_parentesis_abrir + k_comentario_inicio );
                        accion = accion.replace(k_coma, k_comentario_fin + k_coma + k_comentario_inicio );
                        texto = k_rompe_linea + "\n" 
                                + relleno + k_rompe_linea 
                                + accion.replace(k_parentesis_cerrar, k_comentario_fin + k_parentesis_cerrar );
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
                                if (ultimo_texto.trim().isEmpty() == false) {
                                    ultimo_texto = ultimo_texto + k_rompe_linea + "\n" 
                                        + relleno + k_rompe_linea;
                                }
                                texto = primer_texto  
                                    + k_rompe_linea + "\n" 
                                    + relleno + k_rompe_linea 
                                    + tipo_codigo
                                    + k_rompe_linea + "\n"
                                    + relleno + k_rompe_linea
                                    + ultimo_texto;
                            } 
                        } else {
                            texto = texto + k_rompe_linea + "\n" 
                                + relleno + k_rompe_linea;
                        }
                    } else {
                        if (parametro_y_variable != null) {
                            if (tipo_codigo != null) {
                                texto = k_rompe_linea + "\n" 
                                    + relleno + k_rompe_linea 
                                    + parametro_y_variable + tipo_codigo
                                    + k_rompe_linea + "\n"
                                    + relleno + k_rompe_linea;
                            } else {
                                texto = parametro_y_variable;
                            }
                        }
                    }
                }
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
                linea_texto = contexto.leer(k_contexto_linea).dar();
                if (linea_texto == null) {
                    ret = false;
                    error[0] = "No se ha encontrado la linea que procesar en el contexto. ";
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
                texto_final = previo_texto.substring(0, previo_texto.length() - linea_texto.length());
                desdecorado_texto = new textos();
                error_texto = new textos();
                ret = Decoraciones.desdecorar(linea_texto, desdecorado_texto, error_texto);
                error[0] = error_texto.dar();
                if (ret) {
                    linea_texto_desdecorada = desdecorado_texto.dar();
                    decorado_texto = new textos();
                }
                if (es_operacion_tras_parentesis == false) {
                    reemplazar = (String) objects_mapa.get(k_mapa_reemplazar);
                    reemplazar = reemplazar.replace("*", "\\*");
                    texto = texto.replace("\\", "\\\\");
                    reemplazar = linea_texto_desdecorada.replaceFirst(reemplazar, texto);
                } else {
                    pos = linea_texto_desdecorada.lastIndexOf(k_parentesis_cerrar.trim());
                    if (pos < 0) {
                        ret = false;
                        error[0] = "No se encuentra el cierre del paso de parámetros. ";
                    }
                    if (ret) {
                        if (nuevo_bloque_parametro_boton != null) {
                            pos = pos + k_parentesis_cerrar.trim().length();
                            texto = k_parentesis_abrir + k_comentario_inicio + k_comentario_fin.trim() + k_parentesis_cerrar;
                        } else if (nuevo_parametro_boton != null) {
                            texto = k_coma.trim() + k_comentario_inicio + k_comentario_fin;
                        } else if (tipo_codigo != null) {
                            pos = pos + k_parentesis_cerrar.trim().length();
                            texto = k_rompe_linea + "\n" 
                                + relleno + k_rompe_linea 
                                + tipo_codigo;
                        } else {
                            ret = false;
                            error[0] = "No hay operación válida definida. ";
                        }
                        if (ret) {
                            inicio_texto = linea_texto_desdecorada.substring(0, pos);
                            fin_texto = linea_texto_desdecorada.substring(pos);
                            reemplazar = inicio_texto + texto + fin_texto;
                        }
                    }
                }
            }
            if (ret) {
                ret = Decoraciones.decorar_subtexto(reemplazar, decorado_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                texto_final= texto_final + decorado_texto.dar();
                texto_final= texto_final + posterior_texto;
                final_texto = new textos(texto_final);
                ret = actualizar_numeros_linea(final_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                ret = contexto.modificar(k_contexto_archivo_abierto, final_texto.dar()).es();
            }
            if (ret) {
                ret = guardar_cambio(contexto, final_texto.dar(), error);
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
                url = Urls.completar_URL(k_prefijo_url + k_ruta_completar_acciones, k_protocolo_por_defecto, error);
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
            error[0] = "Error en procesar.completar_acciones. " + error[0];
            ret = false;
        }
        return ret;
    }
        
    public static String buscar_atributo_y_semiconstante(boolean es_buscar_atributo, contextos contexto, String palabras_clave, Map<String, Object> objects_mapa, String [] error) {
        boolean ret = true;
        String retorno = null;
        List <String> busquedas_list = new ArrayList();
        Map<String, String> configuraciones_mapa = null;
        String ruta_seleccionada = null;
        File file;
        File [] files_array;
        String encontrado_texto = null;
        Locale locale;
        String lenguaje;
        List <String> ya_buscados_lista;
        String nombre;
        int pos;
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
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_carpeta_innui, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_carpeta_importable, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            file = new File(ruta_seleccionada);
            files_array = file.listFiles(new FileFilter () {
                @Override
                public boolean accept(File pathname) {
                    boolean ret = true;
                    String nombre;
                    nombre = pathname.getName();
                    if (es_buscar_atributo) {
                        if (nombre.startsWith(k_prefijo_atributos) == false) {
                            ret = false;
                        }
                    } else {
                        if (nombre.startsWith(k_prefijo_semiconstantes) == false) {
                            ret = false;
                        }
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
            locale = Locale.getDefault();
            lenguaje = k_infijo_in_+ locale.getLanguage();
            ya_buscados_lista = new ArrayList();
            for (File file_nodo: files_array) {
                if (es_buscar_atributo) {
                    encontrado_texto = buscar_atributos_y_semiconstantes_en_archivo(es_buscar_atributo, file_nodo, busquedas_list, error);
                } else {
                    nombre = file_nodo.getName();
                    if (nombre.contains(lenguaje)) {
                        pos = nombre.indexOf(k_infijo_in_);
                        if (pos < 0) {
                            ret = false;
                            error[0] = "Nombre de archivo de semiconstantes sin indicador de idioma " + k_infijo_in_ + ". ";
                        }
                        if (ret) {
                            nombre = nombre.substring(0, pos);
                            ya_buscados_lista.add(nombre);
                            encontrado_texto = buscar_atributos_y_semiconstantes_en_archivo(es_buscar_atributo, file_nodo, busquedas_list, error);
                        }
                    }
                }
                ret = (encontrado_texto != null);
                if (ret == false) {
                    break;
                }
                retorno = retorno + encontrado_texto;
            }
            if (es_buscar_atributo == false) {
                for (File file_nodo: files_array) {
                    nombre = file_nodo.getName();
                    if (nombre.contains(lenguaje) == false) {
                        pos = nombre.indexOf(k_infijo_in_);
                        if (pos < 0) {
                            ret = false;
                            error[0] = "Nombre de archivo de semiconstantes sin indicador de idioma " + k_infijo_in_ + ". ";
                        }
                        if (ret) {
                            nombre = nombre.substring(0, pos);
                            if (ya_buscados_lista.contains(nombre) == false) {
                                encontrado_texto = buscar_atributos_y_semiconstantes_en_archivo(es_buscar_atributo, file_nodo, busquedas_list, error);
                            }
                        }
                    }
                    ret = (encontrado_texto != null);
                    if (ret == false) {
                        break;
                    }
                    retorno = retorno + encontrado_texto;
                }
            }
        }
        return retorno;
    }
    
    public static String buscar_atributos_y_semiconstantes_en_archivo(boolean es_buscar_atributo, File file_accion, List <String> busquedas_list, String [] error) {
        boolean ret = true;
        String retorno = null;
        String texto;
        String texto_donde_buscar;
        Pattern pattern = null;
        Matcher matcher = null;
        String hipervinculo;
        Map <String, String> [] json_mapas_array = null;
        List <Entry<String, String>> entrys_lista;
        Entry<String, String> entry;
        try {
            texto = Utf8.leer(file_accion, error);
            ret = (texto != null);
            if (ret) {
                if (texto.trim().isEmpty()) {
                    texto = "{}";
                }
                json_mapas_array = Textos.leer(texto, error);
                ret = (json_mapas_array != null);
            }
            if (ret) {
                entrys_lista = new ArrayList(json_mapas_array[0].entrySet());
                retorno = "";
                for (String busqueda: busquedas_list) {
                    pattern = Pattern.compile(busqueda);
                    int i = 0;
                    int tam = json_mapas_array[0].size();
                    while (true) {
                        if (i >= tam) {
                            break;
                        }
                        entry = entrys_lista.get(i);
                        texto_donde_buscar = entry.getKey();
                        matcher = pattern.matcher(texto_donde_buscar);
                        if (matcher.find(0)) {
                            hipervinculo = generar_hipervinculo_atributo_y_semiconstante(es_buscar_atributo, texto_donde_buscar, entry.getValue(), error);
                            ret = (hipervinculo != null);
                            if (ret) {
                                if (retorno.trim().isEmpty() == false) {
                                    retorno = retorno + k_coma;
                                }
                                retorno = retorno + hipervinculo;
                                entrys_lista.remove(i);
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
            error[0] = "Error en procesar.buscar_atributos_en_archivo. " + error[0];
            ret = false;
            retorno = null;
        }
        return retorno;
    }

    public static String generar_hipervinculo_atributo_y_semiconstante(boolean es_buscar_atributo, String clave, String valor, String [] error) {
        String retorno = "";
        boolean ret = true;
        retorno = k_prefijo_pedir_accion;
        if (es_buscar_atributo) {
            retorno = retorno + k_nombre_onclick_atributo;
        } else {
            retorno = retorno + k_nombre_onclick_semiconstante;
        }
        retorno = retorno + clave + k_presufijo_pedir_accion ;
        if (es_buscar_atributo) {
            retorno = retorno + clave;
        } else {
            retorno = retorno + clave + k_corchete_abrir + valor + k_corchete_cerrar;
        }
        retorno = retorno + k_sufijo_pedir_accion + "\n";
        return retorno;
    }

}
