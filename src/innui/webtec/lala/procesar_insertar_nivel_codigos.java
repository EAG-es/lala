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
import static innui.html.lala.Decoraciones.k_contra;
import static innui.html.lala.Decoraciones.k_fin_bloque_repetir;
import static innui.html.lala.Decoraciones.k_fin_bloque_tratable;
import static innui.html.lala.Decoraciones.k_finalmente;
import static innui.html.lala.Decoraciones.k_indentado;
import static innui.html.lala.Decoraciones.k_salir;
import static innui.html.lala.Decoraciones.k_tratable;
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
import static innui.html.lala.Decoraciones.k_repetir;
import static innui.webtec.Webtec_controlador.poner_url_ref_a_contenido;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.html.lala.Decoraciones.k_captura;
import static innui.html.lala.Decoraciones.k_comentario_linea;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;

/**
 * Clase de ejemplo de procesamiento de un formulario, en el que se encuentra un error, y se retorna el mismo formulario más el mensaje de error
 */
public class procesar_insertar_nivel_codigos extends A_ejecutores {
    public static String k_mapa_procesar_insertar_nivel_codigos = "innui_webtec_lala_procesar_insertar_nivel_codigos";
    public static String k_ruta_editar_archivos = "/lala/editar_archivos";
    public static String k_ruta_pedir_accion = "/lala/pedir_acciones";
    public static String k_ruta_asignar_referencia = "/lala/asignar_referencias";
    public static String k_ruta_crear_si_condicion = "/lala/crear_si_condiciones";
    public static String k_ruta_crear_variable = "/lala/crear_variables";
    public static String k_ruta_crear_atributo_semiconstante = "/lala/crear_atributos_semiconstantes";
    public static String k_ruta_crear_subaccion = "/lala/insertar_subacciones";
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
        String tipo_codigo;
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
        String boton;
        String comentario;
        boolean es_solo_comentario = false;
        try {
            boton = (String) objects_mapa.get("pedir_accion_boton");
            if (boton != null) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_pedir_accion, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (boton == null) {
                boton = (String) objects_mapa.get("asignar_referencia_boton");
                if (boton != null) {
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_asignar_referencia, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
            }
            if (boton == null) {
                boton = (String) objects_mapa.get("crear_si_condicion_boton");
                if (boton != null) {
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_si_condicion, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
            }
            if (boton == null) {
                boton = (String) objects_mapa.get("crear_contra_si_condicion_boton");
                if (boton != null) {
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_si_condicion, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
            }
            if (boton == null) {
                boton = (String) objects_mapa.get("crear_variable_boton");
                if (boton != null) {
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_variable, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
            }
            if (boton == null) {
                boton = (String) objects_mapa.get("crear_atributo_semiconstante_boton");
                if (boton != null) {
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_atributo_semiconstante, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
            }
            if (boton == null) {
                boton = (String) objects_mapa.get("crear_subaccion_boton");
                if (boton != null) {
                    objects_mapa.put("tipo_accion", "subaccion");
                    url = Urls.completar_URL(k_prefijo_url + k_ruta_crear_subaccion, k_protocolo_por_defecto, error);
                    ret = poner_redireccion(contexto, url, true, null, error);
                }
            }
            if (boton == null) {
                error[0] = "";
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
                tipo_codigo = (String) objects_mapa.get("tipo_codigo");
                ret = (tipo_codigo != null);
                comentario = (String) objects_mapa.get("comentario");
                if (ret == false) {
                    if (comentario.isEmpty()) {
                        ret = false;
                        error[0] = "No se ha indicado el código que insertar. ";
                    } else {
                        es_solo_comentario = true;
                        ret = true;
                    }
                }
                if (ret) {
                    if (comentario.isEmpty() == false) {
                        comentario = relleno + k_comentario_linea + " " + comentario.trim() + "\n";
                    } else {
                        comentario = "";
                    }
                    if (es_solo_comentario == false) {
                        if (tipo_codigo.equals("repetir")) {
                            nueva_linea =  comentario + relleno + k_repetir + "\n";
                            nueva_linea = nueva_linea + relleno + k_fin_bloque_repetir + "\n";
                        } else if (tipo_codigo.equals("salir")) {
                            nueva_linea = comentario + relleno + k_salir + "\n";
                        } else if (tipo_codigo.equals("contra")) {
                            nueva_linea = comentario + relleno_corto + k_contra + "\n";
                        } else if (tipo_codigo.equals("tratable")) {
                            nueva_linea = comentario + relleno + k_tratable + "\n";
                            nueva_linea = nueva_linea + relleno + k_captura + "\n";
                            nueva_linea = nueva_linea + relleno + k_finalmente + "\n";
                            nueva_linea = nueva_linea + relleno + k_fin_bloque_tratable + "\n";
                        }
                    }
                }
                if (ret) {
                    if (es_solo_comentario) {
                        nueva_linea = comentario;
                    }
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
                    if (previo_texto == null) {
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
                    objects_mapa.put(k_mapa_autoformularios_error, error[0]);
                    autoformulario_error = new autoformularios_errores();
                    autoformulario_error.configurar(contexto);
                    ret = autoformulario_error.ejecutar(objects_mapa, error);
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
}
