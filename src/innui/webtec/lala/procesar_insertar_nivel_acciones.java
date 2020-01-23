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
import static innui.html.lala.Decoraciones.k_accion_local;
import static innui.html.lala.Decoraciones.k_fin_bloque_tratable;
import static innui.html.lala.Decoraciones.k_finalmente;
import static innui.html.lala.Decoraciones.k_indentado;
import static innui.html.lala.Decoraciones.k_retornar;
import static innui.html.lala.Decoraciones.k_tratable;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import static innui.webtec.lala.procesar_abrir_archivos.k_contexto_archivo_abierto;
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
import static innui.html.lala.Decoraciones.k_comentario_fin;
import static innui.html.lala.Decoraciones.k_comentario_inicio;
import static innui.html.lala.Decoraciones.k_comentario_marca_linea;
import static innui.html.lala.Decoraciones.k_documentacion_param;
import static innui.html.lala.Decoraciones.k_subaccion;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.html.lala.Decoraciones.k_variable;
import static innui.webtec.lala.insertar_lineas.k_mapa_espacios_num;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_insertar_nivel_acciones extends A_ejecutores {
    public static String k_mapa_procesar_insertar_nivel_acciones = "innui_webtec_lala_insertar_nivel_acciones";
    public static String k_mapa_procesar_insertar_nivel_acciones_errores = "innui_webtec_lala_insertar_nivel_acciones_errores";
    public static String k_ruta_editar_archivos = "/lala/editar_archivos";
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
        String [] error_parcial = { "" };
        int linea;
        String comentario = null;
        boolean es_solo_comentario = false;
        String espacios;
        int espacios_num;
        int i;
        String relleno = "";
        try {
            error[0] = "";
            tipo_accion = (String) objects_mapa.get("tipo_accion");
            espacios = (String) objects_mapa.get(k_mapa_espacios_num);
            espacios_num = Integer.valueOf(espacios);
            if (tipo_accion.equals(k_subaccion)) {
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
                objects_mapa.remove(k_mapa_procesar_insertar_nivel_acciones_errores);
                verbo = (String) objects_mapa.get("verbo");
                verbo = verbo.trim();
                ret = validar_verbo(verbo, error_parcial);
                error[0] = error[0] + error_parcial[0];
                comentario = (String) objects_mapa.get("comentario");
                if (verbo.isEmpty() && comentario.isEmpty() == false) {
                    ret = true;
                    es_solo_comentario = true;
                }
                if (comentario.isEmpty() == false) {
                    comentario = relleno + k_comentario_inicio + "\n"
                      + relleno + k_comentario_marca_linea + comentario + "\n";
                }
                if (es_solo_comentario == false) {
                    objetivo = (String) objects_mapa.get("objetivo");
                    objetivo = objetivo.trim();
                    ret = ret && validar_objetivo(objetivo, error_parcial);
                    error[0] = error[0] + error_parcial[0];
                    parametro = (String) objects_mapa.get("parametro_1");
                    if (parametro != null && parametro.isEmpty() == false) {
                        parametros_list.add(parametro);
                        comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                        ret = ret && validar_variable(parametro, error_parcial);
                        error[0] = error[0] + error_parcial[0];
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_2");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_3");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_4");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_5");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_6");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    }
                    parametro = (String) objects_mapa.get("parametro_7");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_8");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_9");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            ret = ret && validar_variable(parametro, error_parcial);
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                    parametro = (String) objects_mapa.get("parametro_10");
                    if (parametro != null && parametro.isEmpty() == false) {
                        if (es_parametro_vacio) {
                            ret = false;
                            error[0] = error[0] + "Parametro vacío antes de: " + parametro + ". ";
                        } else {
                            parametro = parametro.trim();
                            parametros_list.add(parametro);
                            ret = ret && validar_variable(parametro, error_parcial);
                            comentario = comentario + relleno + k_comentario_marca_linea + k_documentacion_param + parametro + "\n";
                            error[0] = error[0] + error_parcial[0];
                        }
                    } else {
                        es_parametro_vacio = true;
                    }
                }
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                autoformulario_error = new autoformularios_errores();
                autoformulario_error.configurar(contexto);
                ret = autoformulario_error.ejecutar(objects_mapa, error);
                innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
                objects_mapa.put(k_mapa_procesar_insertar_nivel_acciones_errores, innui_webtec_gui_autoformularios_errores);
            } else {
                if (comentario.isEmpty() == false) {
                    comentario = comentario + relleno + k_comentario_fin + "\n";
                }
                texto = contexto.leer(k_contexto_archivo_abierto).dar();
                ret = (texto != null);
                if (es_solo_comentario == false) {
                    if (ret) {
                        if (tipo_accion.equals("accion")) {
                            nueva_linea = relleno + k_accion;
                        } else if (tipo_accion.equals("accion_local")) {
                            nueva_linea = relleno + k_accion_local;
                        } else if (tipo_accion.equals("subaccion")) {
                            nueva_linea = relleno + k_subaccion;
                        } else {
                            ret = false;
                            error[0] = "Tipo de acción desconocido. ";
                        }
                    }
                    if (ret) {
                        nueva_linea = comentario + nueva_linea + verbo + " " + objetivo ;
                        nueva_linea = nueva_linea + "    " + "(" + " ";
                        for (String nodo: parametros_list) {
                            if (nueva_linea.endsWith("(" + " ") == false) {
                                nueva_linea = nueva_linea + "," + " ";
                            }
                            nueva_linea = nueva_linea + nodo;
                        }
                        nueva_linea = nueva_linea + " ... ) ";
                        nueva_linea = nueva_linea +  "\n";
                        nueva_linea = nueva_linea + relleno + k_indentado + k_variable + "\n";
                        nueva_linea = nueva_linea + relleno + k_indentado + k_tratable + "\n";
                        nueva_linea = nueva_linea + relleno + k_indentado + k_captura + "\n";
                        nueva_linea = nueva_linea + relleno + k_indentado + k_finalmente + "\n";
                        nueva_linea = nueva_linea + relleno + k_indentado + k_fin_bloque_tratable + "\n";
                        nueva_linea = nueva_linea + relleno + k_retornar + "\n";
                    }
                } else {
                    nueva_linea = comentario;
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
                    objects_mapa.put(k_mapa_editar_archivos_error, "");
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
                if (ret == false) {
                    ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
                    objects_mapa.put(k_mapa_procesar_insertar_nivel_acciones, error[0]);
                }
            } 
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.procesar_insertar_nivel_acciones. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static boolean validar_variable(String variable, String [] error) {
        boolean ret = true;
        String minusculas;
        String patron;
        error[0] = "";
        if (variable == null || variable.isEmpty()) {
            ret = false;
            error[0] = "Valor no indicado. ";
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
                error[0] = error[0] + minusculas + ": deben tener un significado singular. ";
            }
        }
        return ret;
    }                    

    public static boolean validar_objetivo(String objetivo, String [] error) {
        boolean ret = true;
        ret = ret && validar_variable(objetivo, error);
        return ret;
    }                    
    
    public static boolean validar_verbo(String verbo, String [] error) {
        boolean ret = true;
        Locale locale;
        if (verbo == null || verbo.isEmpty()) {
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
    
}
