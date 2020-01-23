/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.webtec.lala;

import static ingui.javafx.webtec.Webtec.k_prefijo_url;
import innui.archivos.Utf8;
import innui.html.Urls;
import static innui.html.Urls.k_protocolo_por_defecto;
import innui.html.lala.Decoraciones;
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import innui.webtec.gui.autoformularios_errores;
import static innui.webtec.gui.autoformularios_errores.k_mapa_autoformulario_errores;
import java.net.URL;
import java.util.Map;
import static innui.html.lala.Decoraciones.k_prefijo_ancla_linea;
import innui.json.Textos;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.paginas_principales.leer_configuraciones;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.procesar_crear_archivos.k_extension_lala;
import static innui.webtec.lala.procesar_crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.procesar_insertar_nivel_acciones.validar_variable;
import java.io.File;
import java.util.TreeMap;
import static innui.webtec.lala.crear_atributos_semiconstantes.k_contexto_importables_atributos;
import static innui.webtec.lala.crear_atributos_semiconstantes.k_contexto_importables_atributos_ruta;
import static innui.webtec.lala.crear_atributos_semiconstantes.k_contexto_importables_semiconstantes;
import static innui.webtec.lala.crear_atributos_semiconstantes.k_contexto_importables_semiconstantes_ruta;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_crear_atributos_semiconstantes extends A_ejecutores {
    public static String k_mapa_procesar_crear_atributos_semiconstantes = "innui_webtec_lala_procesar_crear_atributos_semiconstantes";
    public static String k_mapa_procesar_crear_atributos_semiconstantes_errores = "innui_webtec_lala_procesar_crear_atributos_semiconstantes_errores";
    public static String k_ruta_insertar_lineas = "/lala/insertar_lineas";
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
        Map<String, String> configuraciones_mapa = null;
        File file;
        String nombre_archivo = null;
        String ruta_escribir = null;
        String texto = null;
        Map<String, String> [] json_mapas_array = null;
        TreeMap<String, String> treemap = null;
        try {
            error[0] = "";
            objects_mapa.remove(k_mapa_procesar_crear_atributos_semiconstantes_errores);
            sobreescribir = (String) objects_mapa.get("sobreescribir");
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
            operacion = (String) objects_mapa.get("operacion");
            if (operacion == null) {
                ret = false;
                error[0] = error[0] + "No se ha indicado la operación. ";
            }
            if (ret) {
                configuraciones_mapa = leer_configuraciones(contexto, objects_mapa, error);
                ret = (configuraciones_mapa != null);
            }
            if (ret) {
                nombre = nombre + " " + sufijo;
                if (operacion.equals("atributo")) {
                    archivo_seleccionado = (String) configuraciones_mapa.get(k_configuraciones_archivo_seleccionado);
                    file = new File(archivo_seleccionado);
                    nombre_archivo = file.getName();
                    nombre_archivo = nombre_archivo.substring(0, nombre_archivo.length() - k_extension_lala.length());
                    nombre = nombre_archivo + " " + nombre;
                }
            }
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                autoformulario_error = new autoformularios_errores();
                autoformulario_error.configurar(contexto);
                ret = autoformulario_error.ejecutar(objects_mapa, error);
                innui_webtec_gui_autoformularios_errores = (String) objects_mapa.get(k_mapa_autoformulario_errores); 
                objects_mapa.put(k_mapa_procesar_crear_atributos_semiconstantes_errores, innui_webtec_gui_autoformularios_errores);
            } else {
                if (operacion.equals("atributo")) {
                    texto = contexto.leer(k_contexto_importables_atributos).dar();
                    ruta_escribir = contexto.leer(k_contexto_importables_atributos_ruta).dar(); 
                } else {
                    texto = contexto.leer(k_contexto_importables_semiconstantes).dar();
                    ruta_escribir = contexto.leer(k_contexto_importables_semiconstantes_ruta).dar(); 
                }
                if (texto.isEmpty()) {
                    texto = "{}";
                }
                json_mapas_array = Textos.leer(texto, error);
                ret = (json_mapas_array != null);
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
                    objects_mapa.put(k_mapa_autoformularios_error, "Entrada incorporada al archivo importable del proyecto: " + ruta_escribir);
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_insertar_lineas, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
                if (ret == false) {
                    ret = poner_cabecera_en_mapa(contexto, objects_mapa, error);
                    objects_mapa.put(k_mapa_procesar_crear_atributos_semiconstantes, error[0]);
                }
            }
        } catch (Exception e) {
            error [0] = e.getMessage();
            if (error[0] == null) {
                error[0] = ""; //NOI18N
            }
            error[0] = "Error en procesar.procesar_crear_atributos_semiconstantes. " + error[0];
            ret = false;
        }
        return ret;
    }
}
