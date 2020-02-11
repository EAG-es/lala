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
import static innui.html.lala.Decoraciones.k_accion;
import static innui.html.lala.Decoraciones.k_accion_local;
import static innui.html.lala.Decoraciones.k_fin_bloque_tratable;
import static innui.html.lala.Decoraciones.k_finalmente;
import static innui.html.lala.Decoraciones.k_indentado;
import static innui.html.lala.Decoraciones.k_retornar;
import static innui.html.lala.Decoraciones.k_tratable;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.html.lala.Decoraciones.k_captura;
import static innui.html.lala.Decoraciones.k_coma;
import static innui.html.lala.Decoraciones.k_comentario_fin;
import static innui.html.lala.Decoraciones.k_comentario_marca_linea;
import static innui.html.lala.Decoraciones.k_corchete_abrir;
import static innui.html.lala.Decoraciones.k_corchete_cerrar;
import static innui.html.lala.Decoraciones.k_documentacion_param;
import static innui.html.lala.Decoraciones.k_parentesis_abrir;
import static innui.html.lala.Decoraciones.k_parentesis_cerrar;
import static innui.html.lala.Decoraciones.k_puntos_suspensivos;
import static innui.html.lala.Decoraciones.k_subaccion;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.html.lala.Decoraciones.k_variable;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_proyectos.k_carpeta_lala;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.crear_proyectos.k_prefijo_acciones;
import static innui.webtec.lala.insertar_lineas.k_mapa_espacios_num;
import static innui.webtec.lala.insertar_lineas.k_ruta_insertar_lineas;
import java.io.File;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static innui.html.lala.Decoraciones.k_comentario_doc_inicio;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class insertar_nivel_acciones extends A_ejecutores {
    public static String k_ruta_editar_archivos = "/lala/editar_archivos";
    public static String k_ruta_insertar_nivel_acciones = "/lala/insertar_nivel_acciones";
    public static String k_operacion_accion = "accion";
    public static String k_operacion_accion_local = "accion_local";
    public static String k_operacion_subaccion = "subaccion";
    
    /**
     * Modifica o añade datos que le van a llegar a la plantilla asociada
     * @param objects_mapa datos con nombre que están disponibles
     * @param error mensaje de error, si lo hay.
     * @return true si tiene éxito, false si hay algún error
     */ 
    @Override
    public boolean ejecutar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String texto;
        String verbo = null;
        URL url;
        String objetivo = "";
        String parametro;
        boolean es_parametro_vacio = false;
        List <String> parametros_list = new ArrayList();
        String nueva_linea = "";
        String linea_mapa = "";
        String previo_texto = null;
        String posterior_texto = null;
        textos error_texto = new textos("");
        String texto_final;
        textos final_texto = null;
        String tipo_accion;
        textos decorado_texto = null;
        String [] error_local = { "" };
        int linea;
        String comentario = null;
        boolean es_solo_comentario = false;
        String espacios;
        int espacios_num;
        int i;
        String relleno = "";
        String firma_accion = "";
        String comentario_y_firma_accion;
        String nombre_accion;
        try {
            error[0] = "";
            tipo_accion = (String) objects_mapa.get("tipo_accion");
            espacios = (String) objects_mapa.get(k_mapa_espacios_num);
            espacios_num = Integer.valueOf(espacios);
            if (tipo_accion.equals(k_operacion_subaccion)) {
                if (espacios_num == 0) {
                    ret = false;
                    error[0] = "No se permite: subaccion, fuera de: accion o accion local. ";
                }
            } else {
                if (espacios_num != 0) {
                    ret = false;
                    error[0] = "No se permiten: accion o accion local, dentro de bloques de código. ";
                }
            }
            if (ret) {
                i = 0;
                while (true) {
                    if (i >= espacios_num) {
                        break;
                    }
                    relleno = relleno + " ";
                    i = i + 1;
                }                
                verbo = (String) objects_mapa.get("verbo");
                verbo = verbo.trim();
                ret = validar_verbo(verbo, error_local);
                error[0] = error[0] + error_local[0];
                error_local[0] = "";
                comentario = (String) objects_mapa.get("comentario");
                if (verbo.trim().isEmpty() && comentario.trim().isEmpty() == false) {
                    es_solo_comentario = true;
                }
                comentario = relleno + k_comentario_doc_inicio + "\n"
                  + relleno + k_comentario_marca_linea + comentario.trim() + "\n";
                if (es_solo_comentario == false) {
                    objetivo = (String) objects_mapa.get("objetivo");
                    objetivo = objetivo.trim();
                    ret = ret && validar_objetivo(objetivo, error_local);
                    error[0] = error[0] + error_local[0];
                    error_local[0] = "";
                    parametro = (String) objects_mapa.get("parametro_1");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        parametros_list.add(parametro);
                        comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                        ret = ret && validar_variable(parametro, error_local);
                        error[0] = error[0] + error_local[0];
                        error_local[0] = "";
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_2");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_3");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_4");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_5");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_6");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    }
                    parametro = (String) objects_mapa.get("parametro_7");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_8");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_9");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_local);
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_10");
                    if (parametro != null && parametro.trim().isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            ret = ret && validar_variable(parametro, error_local);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            error[0] = error[0] + error_local[0];
                            error_local[0] = "";
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                }
            }
            if (ret) {
                if (comentario.trim().isEmpty() == false) {
                    comentario = comentario + relleno + k_comentario_fin + "\n";
                }
                texto = contexto.leer(k_contexto_archivo_abierto).dar();
                ret = (texto != null);
                if (es_solo_comentario == false) {
                    if (ret) {
                        if (tipo_accion.equals(k_operacion_accion)) {
                            firma_accion = relleno + k_accion;
                        } else if (tipo_accion.equals(k_operacion_accion_local)) {
                            firma_accion = relleno + k_accion_local;
                        } else if (tipo_accion.equals(k_operacion_subaccion)) {
                            firma_accion = relleno + k_subaccion;
                        } else {
                            ret = false;
                            error[0] = "Tipo de acción desconocido. ";
                        }
                    }
                    if (ret) {
                        if (tipo_accion.equals(k_operacion_subaccion) == false) {
                            nombre_accion = verbo + " " + objetivo;
                            firma_accion = firma_accion + nombre_accion;
                            firma_accion = firma_accion + k_indentado + k_parentesis_abrir;
                            for (String nodo: parametros_list) {
                                if (firma_accion.endsWith(k_parentesis_abrir) == false) {
                                    firma_accion = firma_accion + k_coma;
                                }
                                firma_accion = firma_accion + nodo;
                            }
                            firma_accion = firma_accion + k_puntos_suspensivos + k_parentesis_cerrar + "\n";
                            comentario_y_firma_accion = comentario + firma_accion;
                            nueva_linea = comentario_y_firma_accion + relleno + k_indentado + k_variable + "\n";
                            nueva_linea = nueva_linea + relleno + k_indentado + k_tratable + "\n";
                            nueva_linea = nueva_linea + relleno + k_indentado + k_captura + "\n";
                            nueva_linea = nueva_linea + relleno + k_indentado + k_finalmente + "\n";
                            nueva_linea = nueva_linea + relleno + k_indentado + k_fin_bloque_tratable + "\n";
                            nueva_linea = nueva_linea + relleno + k_retornar + "\n";
                            ret = escribir_archivo_acciones(contexto, objects_mapa, nombre_accion, comentario_y_firma_accion, error);
                        }
                    }
                } else {
                    nueva_linea = comentario;
                }
            }
            if (ret) {
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
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
                    }
            if (ret == false) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_insertar_lineas, k_protocolo_por_defecto, error);
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
                ret = poner_redireccion(contexto, url, true, null, error);
            } 
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en ejecutar.insertar_nivel_acciones. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static boolean validar_variable(String variable, String [] error) {
        boolean ret = true;
        String minusculas;
        String patron;
        error[0] = "";
        if (variable == null || variable.trim().isEmpty()) {
            ret = false;
            error[0] = "Nombre no indicado. ";
        } else {
            patron = "[a-zñáéíóúç_][a-zñáéíóúç0-9_ ]*";
            if (variable.matches(patron) == false) {
                ret = false;
                error[0] = error[0] + variable + ": no cumple el patrón: " + patron + ". ";
            }
            minusculas = variable.toLowerCase();
            if (variable.equals(minusculas) == false) {
                ret = false;
                error[0] = error[0] + variable + ": deben usarse solo minúsculas. ";
            }
            if (minusculas.endsWith("s")) {
                ret = false;
                error[0] = error[0] + minusculas + ": deben estar en singular. ";
            }
        }
        return ret;
    }                    

    public static boolean validar_objetivo(String objetivo, String [] error) {
        boolean ret = true;
        ret = validar_variable(objetivo, error);
        return ret;
    }                    
    
    public static boolean validar_verbo(String verbo, String [] error) {
        boolean ret = true;
        Locale locale;
        if (verbo == null || verbo.trim().isEmpty()) {
            ret = false;
            error[0] = error[0] + "Verbo de la acción no indicado. ";
        } else {
            verbo = verbo.trim();
            locale = Locale.getDefault();
            if (locale.getLanguage().equals("es")) {
                if (verbo.endsWith("ar") == false
                 && verbo.endsWith("er") == false
                 && verbo.endsWith("ir") == false) {
                    ret = false;
                    error[0] = error[0] + "El verbo de la acción no está en infinitivo. ";
                }
            }
        }
        return ret;
    }                    
    
    public static boolean escribir_archivo_acciones(contextos contexto, Map<String, Object> objects_mapa, String nombre_accion, String comentario_y_firma, String [] error) {
        boolean ret = true;
        String ruta_seleccionada = null;
        String archivo_seleccionado = null;
        String ruta_accion;
        Map<String, String> configuraciones_mapa = null;
        String nombre_proyecto = null;
        File file = null;
        PrintStream printstream = null;
        String ruta_parcial_archivo = null;
        int pos = 0;
        String texto;
        Pattern patron = null;
        Matcher matcher = null;
        configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
        ret = (configuraciones_mapa != null);
        if (ret) {
            ruta_seleccionada = configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            archivo_seleccionado = configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
            ret = (archivo_seleccionado != null);
        }
        if (ret) {
            if (archivo_seleccionado.startsWith(ruta_seleccionada) == false) {
                ret = false;
                error[0] = "El archivo seleccionado no está dentro de la carpeta del proyecto" + ruta_seleccionada + ". ";
            }
        }
        if (ret) {
            ruta_parcial_archivo = extraer_ruta_parcial(ruta_seleccionada, archivo_seleccionado, error);
            ret = (ruta_parcial_archivo != null);
        }
        if (ret) {
            file = new File(ruta_seleccionada);
            file = file.getParentFile();
            ruta_seleccionada = file.getAbsolutePath();
            nombre_proyecto = file.getName();
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_prefijo_acciones + nombre_proyecto + k_extension_lala, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            file = new File(ruta_seleccionada);
            if (file.exists() == false) {
                ret = false;
                error[0] = "No se encuentra el archivo: " + ruta_seleccionada;
            }
        }
        if (ret) {
            texto = Utf8.leer(file, error);
            nombre_accion = nombre_accion.trim();
            nombre_accion = nombre_accion.replaceAll("\\s+", "\\\\s+");
            nombre_accion = nombre_accion + "[^\\" + k_corchete_abrir.trim() + "]+\\"+ k_corchete_abrir.trim() + "\\s+" + ruta_parcial_archivo + "\\s+\\" + k_corchete_cerrar.trim();
            nombre_accion = "(?m)" + nombre_accion; // multilinea
            patron = Pattern.compile(nombre_accion);
            matcher = patron.matcher(texto);
            if (matcher.find(0)) {
                ret = false;
                error[0] = "Ya existe ese nombre de accion. ";
            }
        }
        if (ret) {
            printstream = Utf8.abrir(file, true, error);
            ret = (printstream != null);
        }
        if (ret) {
            pos = comentario_y_firma.lastIndexOf("\n");
            if (pos < 0) {
                ret = false;
                error[0] = "Falta el salto de linea de final de texto. ";
            }
        }
        if (ret) {
            texto = comentario_y_firma.substring(0, pos);
            ruta_accion = componer_ruta_accion(ruta_parcial_archivo, error);
            texto = texto + ruta_accion;
            printstream.print(texto);
            objects_mapa.put(k_mapa_editar_archivos_error, "Acción incorporada al archivo: " + ruta_seleccionada + ". ");
        }
        return ret;
    }

    public static String extraer_ruta_parcial(String ruta_seleccionada, String archivo_seleccionado, String [] error) {
        return archivo_seleccionado.substring(ruta_seleccionada.length());
    }

    public static String componer_ruta_accion(String ruta_parcial_archivo, String [] error) {
        return k_indentado + k_corchete_abrir + ruta_parcial_archivo + k_corchete_cerrar + "\n";
    }
}
