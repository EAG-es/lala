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
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.k_ente_nulo;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import innui.json.Textos;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.chunk.Procesamientos.k_infijo_in_;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import static innui.webtec.lala.paginas_principales.leer_configuraciones;
import java.io.File;
import java.net.URL;
import java.util.Map;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_proyectos.k_carpeta_importable;
import static innui.webtec.lala.crear_proyectos.k_carpeta_innui;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.crear_proyectos.k_prefijo_atributos;
import static innui.webtec.lala.crear_proyectos.k_prefijo_semiconstantes;
import static innui.webtec.lala.insertar_lineas.k_ruta_insertar_lineas;
import static innui.webtec.lala.insertar_nivel_acciones.validar_variable;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class insertar_atributos_semiconstantes extends A_ejecutores {
    public static String k_insertar_atributos_semiconstantes = "/lala/insertar_atributos_semiconstantes";
    public static String k_nombre_crear_atributos_semiconstantes_atributos = "innui_webtec_lala_crear_atributos_semiconstantes_datos_previos";
    public static String k_nombre_crear_semiconstantes = "innui_webtec_lala_crear_semiconstantes";
    public static String k_nombre_crear_atributos = "innui_webtec_lala_crear_atributos";
    public static String k_contexto_importables_atributos = "contexto_lala_importables_atributos";
    public static String k_contexto_importables_atributos_ruta = "contexto_lala_importables_atributos_ruta";
    public static String k_contexto_importables_semiconstantes = "contexto_lala_importables_semiconstantes";
    public static String k_contexto_importables_semiconstantes_ruta = "contexto_lala_importables_semiconstantes_ruta";    
    public static String k_operacion_atributo = "atributo";
    public static String k_mapa_sufijo = "sufijo";
    public static String k_mapa_bool = "bool";
    public static String k_mapa_valor_decimal = "valor_decimal";
    public static String k_mapa_valor_entero = "valor_entero";
    public static String k_mapa_valor_texto = "valor_texto";
    public static String k_mapa_sobreescribir = "sobreescribir";
    public static String k_mapa_nombre = "nombre";
    public static String k_mapa_operacion = "operacion";
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
        String previo_texto = null;
        String posterior_texto = null;
        try {
            ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
            if (ret) {
                ret = poner_menu_contextual_en_mapa(contexto, objects_mapa, error);
            }
            if (ret) {
                ret = leer_rutas_importables(contexto, objects_mapa, error);
            }
            previo_texto = contexto.leer(k_contexto_previo).dar();
            posterior_texto = contexto.leer(k_contexto_posterior).dar();
            objects_mapa.put(k_mapa_texto_previo, previo_texto); 
            objects_mapa.put(k_mapa_texto_posterior, posterior_texto);                 
            url_accion = Urls.completar_URL(k_prefijo_url + k_insertar_atributos_semiconstantes, k_protocolo_por_defecto, error);
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
        
    public static boolean leer_rutas_importables(contextos contexto, Map<String, Object> objects_mapa, String [] error) {
        boolean ret = true;
        String ruta_seleccionada = null;
        Map<String, String> configuraciones_mapa = null;
        String nombre_proyecto = null;
        File file = null;
        String ruta_importable = null;
        String texto = null;
        Locale locale;
        String idioma;
        configuraciones_mapa = paginas_principales.leer_configuraciones(contexto, objects_mapa, error);
        ret = (configuraciones_mapa != null);
        if (ret) {
            ruta_seleccionada = configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            file = new File(ruta_seleccionada);
            file = file.getParentFile();
            nombre_proyecto = file.getName();
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_carpeta_innui, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            ruta_seleccionada = aumentar_ruta(ruta_seleccionada, k_carpeta_importable, error);
            ret = (ruta_seleccionada != null);
        }
        if (ret) {
            ruta_importable = aumentar_ruta(ruta_seleccionada, k_prefijo_atributos + nombre_proyecto + k_extension_lala, error);
            ret = (ruta_importable != null);
        }
        if (ret) {
            file = new File(ruta_importable);
            if (file.exists() == false) {
                ret = false;
                error[0] = "No se encuentra el archivo: " + ruta_importable;
            }
        }
        if (ret) {
            texto = Utf8.leer(file, error);
            ret = (texto != null);
        }
        if (ret) {
            objects_mapa.put(k_nombre_crear_atributos, texto);
            contexto.fondear_con_datos(k_contexto_importables_atributos, texto);
            contexto.fondear_con_datos(k_contexto_importables_atributos_ruta, ruta_importable);
        }
        if (ret) {
            locale = Locale.getDefault();
            idioma = locale.getLanguage();
            ruta_importable = aumentar_ruta(ruta_seleccionada, k_prefijo_semiconstantes + k_infijo_in_ + idioma + "_" + nombre_proyecto + k_extension_lala, error);
            ret = (ruta_importable != null);
        }
        if (ret) {
            file = new File(ruta_importable);
            if (file.exists() == false) {
                ret = false;
                error[0] = "No se encuentra el archivo: " + ruta_importable;
            }
        }
        if (ret) {
            texto = Utf8.leer(file, error);
            ret = (texto != null);
        }
        if (ret) {
            objects_mapa.put(k_nombre_crear_semiconstantes, texto);
            contexto.fondear_con_datos(k_contexto_importables_semiconstantes, texto);
            contexto.fondear_con_datos(k_contexto_importables_semiconstantes_ruta, ruta_importable);
        }
        return ret;
    }

    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        String operacion = null;
        URL url;
        String nombre = "";
        String linea_mapa = "";
        int linea;
        String sobreescribir = null;
        String valor = null;
        String sufijo = null;
        String [] error_local =  { "" };
        String archivo_seleccionado = null;
        String ruta_seleccionada = null;
        Map<String, String> configuraciones_mapa = null;
        File file;
        String nombre_archivo = null;
        String ruta_archivo = null;
        String ruta_escribir = null;
        String texto = null;
        Map<String, String> [] json_mapas_array = null;
        TreeMap<String, String> treemap = null;
        try {
            error[0] = "";
            sobreescribir = (String) objects_mapa.get(k_mapa_sobreescribir);
            nombre = (String) objects_mapa.get(k_mapa_nombre);
            ret = ret && validar_variable(nombre, error_local);
            error[0] = error[0] + error_local[0];
            error_local[0] = "";
            operacion = (String) objects_mapa.get(k_mapa_operacion);
            if (operacion == null) {
                ret = false;
                error[0] = error[0] + "No se ha indicado la operación. ";
            }
            if (ret) {
                if (operacion.equals(k_operacion_atributo) == false) {
                    valor = (String) objects_mapa.get(k_mapa_valor_texto);
                    if (valor != null && valor.trim().isEmpty() == false) {
                        sufijo = "te";
                    }
                    if (sufijo == null) {
                        valor = (String) objects_mapa.get(k_mapa_valor_entero);
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
                        valor = (String) objects_mapa.get(k_mapa_valor_decimal);
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
                        valor = (String) objects_mapa.get(k_mapa_bool);
                        if (valor != null && valor.trim().isEmpty() == false) {
                            sufijo = "bo";
                        }
                    }
                } else {
                    if (sufijo == null) {
                        valor = k_ente_nulo;
                        sufijo = (String) objects_mapa.get(k_mapa_sufijo);
                        if (sufijo == null) {
                            sufijo = "";
                        }
                    }
                }
            }
            if (ret) {
                configuraciones_mapa = leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
            }
            if (ret) {
                ruta_seleccionada = (String) configuraciones_mapa.get(k_configuraciones_ruta_seleccionada);
                archivo_seleccionado = (String) configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
                file = new File(archivo_seleccionado);
                nombre_archivo = file.getName();
            }
            if (ret) {
                ruta_archivo = archivo_seleccionado.substring(ruta_seleccionada.length() + 1, archivo_seleccionado.length() - k_extension_lala.length());
                nombre = nombre + " " + sufijo;
                if (operacion.equals(k_operacion_atributo)) {
                    ruta_archivo = ruta_archivo.replaceAll("[\\/]", ".");
                    nombre = ruta_archivo + "." + nombre;
                    valor = nombre;
                } else {
                    if (valor == null) {
                        ret = false;
                        error[0] = "No se ha indicado un valor válido. ";
                    } else {
                        file = new File(ruta_seleccionada);
                        file = file.getParentFile();
                        nombre_archivo = file.getName();
                        nombre = nombre_archivo + " " + nombre;
                    }
                }
            }
            if (ret) {
                if (operacion.equals(k_operacion_atributo)) {
                    texto = contexto.leer(k_contexto_importables_atributos).dar();
                    ruta_escribir = contexto.leer(k_contexto_importables_atributos_ruta).dar(); 
                } else {
                    texto = contexto.leer(k_contexto_importables_semiconstantes).dar();
                    ruta_escribir = contexto.leer(k_contexto_importables_semiconstantes_ruta).dar(); 
                }
                if (texto.trim().isEmpty()) {
                    texto = "{}";
                }
                json_mapas_array = Textos.leer(texto, error);
                ret = (json_mapas_array != null);
            }
            if (ret) {
                treemap = new TreeMap();
                treemap.putAll(json_mapas_array[0]);
                if (sobreescribir != null) {
                    treemap.put(nombre, valor);
                } else {
                    if (treemap.containsKey(nombre) == false) {
                        treemap.put(nombre, valor);
                    } else {
                        ret = false;
                        error[0] = "Ya existe el nombre: " + nombre;
                    }
                }
            }
            if (ret) {
                texto = Textos.escribir(treemap, error);
                ret = (texto != null);
            }
            if (ret) {
                ret = Utf8.escribir(ruta_escribir, texto, error);
            }
            if (ret) {
                linea_mapa = (String) objects_mapa.get(k_mapa_linea_numero);
                linea = Integer.valueOf(linea_mapa);
                linea_mapa = Decoraciones.crear_formato_marca_linea(linea);
                ret = poner_url_ref_a_contenido(k_prefijo_ancla_linea + linea_mapa, objects_mapa, error);
            }
            if (ret) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_insertar_lineas, k_protocolo_por_defecto, error);
                objects_mapa.put(k_mapa_autoformularios_error, "Entrada incorporada al archivo importable del proyecto: " + ruta_escribir);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (ret == false) {
                url = Urls.completar_URL(k_prefijo_url + k_insertar_atributos_semiconstantes, k_protocolo_por_defecto, error);
                if (ret == false) {
                    objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
                }
                objects_mapa.put(k_mapa_autoformularios_presentar, "");
                objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
                ret = poner_redireccion(contexto, url, true, null, error);  
            } 
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.crear_atributos_semiconstantes. " + error[0];
            ret = false;
        }
        return ret;
    }

}
