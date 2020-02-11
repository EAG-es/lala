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
import static innui.html.lala.Decoraciones.k_mapa_linea_numero;
import innui.webtec.A_ejecutores;
import static innui.webtec.Webtec_controlador.poner_redireccion;
import innui.webtec.gui.autoformularios;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_accion;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_enviar;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_error;
import static innui.webtec.gui.autoformularios.k_mapa_autoformularios_presentar;
import java.util.Map;
import innui.webtec.gui.configuraciones;
import static innui.webtec.lala.abrir_archivos.k_contexto_archivo_abierto;
import static innui.webtec.lala.editar_archivos.k_mapa_editar_archivos_error;
import static innui.webtec.lala.editar_archivos.k_ruta_editar_archivos;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.crear_archivos.k_extension_lala;
import static innui.webtec.lala.crear_archivos.validar_nombre_carpeta;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_archivo_seleccionado;
import static innui.webtec.lala.crear_proyectos.k_configuraciones_ruta_seleccionada;
import static innui.webtec.lala.insertar_lineas.k_contexto_linea;
import static innui.webtec.lala.insertar_lineas.k_contexto_posterior;
import static innui.webtec.lala.insertar_lineas.k_contexto_previo;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_posterior;
import static innui.webtec.lala.insertar_lineas.k_mapa_texto_previo;
import static innui.webtec.lala.paginas_principales.poner_cabecera_en_mapa;
import static innui.webtec.lala.paginas_principales.poner_menu_contextual_en_mapa;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.net.URL;

/**
 * Clase de ejemplo, con plantilla asociada, de aplicación que hace uso de los autoformularios.
 */
public class editar_lineas extends A_ejecutores {
    public static String k_mapa_editar_lineas = "editar_lineas"; //NOI18N
    public static String k_ruta_editar_lineas = "/lala/editar_lineas"; //NOI18N
    public static int k_lineas_num = 15;
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
        if ((boton_enviar_pulsado == null)
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
        textos edicion_texto = null;
        textos resto_texto = null;
        textos error_texto = null;
        URL url;
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
                edicion_texto = new textos();
                resto_texto = new textos();
                error_texto = new textos();
                ret = extraer_texto_edicion(linea_texto + posterior_texto, k_lineas_num, edicion_texto, resto_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                previo_texto = previo_texto.substring(0, previo_texto.length() - linea_texto.length());
                posterior_texto = resto_texto.dar();
                contexto.modificar(k_contexto_previo, previo_texto);
                contexto.modificar(k_contexto_posterior, posterior_texto);
            }
            objects_mapa.put(k_mapa_editar_lineas, edicion_texto.dar()); 
            objects_mapa.put(k_mapa_texto_previo, previo_texto); 
            objects_mapa.put(k_mapa_texto_posterior, posterior_texto);    
            if (ret == false) {
                objects_mapa.put(k_mapa_autoformularios_error, error[0]); 
            }
            url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_lineas, k_protocolo_por_defecto, error);
            objects_mapa.put(k_mapa_autoformularios_accion, url.toExternalForm());
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
            error[0] = "Error en presentar.editar_lineas. " + error[0];
            ret = false;
        }
        return ret;
    }
   
    public boolean procesar(Map<String, Object> objects_mapa, String[] error) {
        boolean ret = true;
        URL url;
        String previo_texto = null;
        String posterior_texto = null;
        String edicion_texto = null;
        String final_texto = null;
        textos decorado_texto = null;
        textos error_texto = null;
        try {
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
                edicion_texto = (String) objects_mapa.get(k_mapa_editar_lineas);
                decorado_texto = new textos();
                error_texto = new textos();
                ret = Decoraciones.decorar_subtexto(edicion_texto, decorado_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                final_texto = previo_texto + decorado_texto.dar() + posterior_texto;
                decorado_texto.poner(final_texto);
                ret = Decoraciones.actualizar_numeros_linea(decorado_texto, error_texto);
                error[0] = error_texto.dar();
            }
            if (ret) {
                contexto.modificar(k_contexto_archivo_abierto, decorado_texto.dar());                
                objects_mapa.put(k_mapa_editar_archivos_error, "");
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_archivos, k_protocolo_por_defecto, error);
                ret = poner_redireccion(contexto, url, true, null, error);
            }
            if (ret == false) {
                url = Urls.completar_URL(k_prefijo_url + k_ruta_editar_lineas, k_protocolo_por_defecto, error);
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
            error[0] = "Error en procesar.editar_lineas. " + error[0];
            ret = false;
        }
        return ret;
    }

    public static boolean extraer_texto_edicion(String texto, int lineas_num, textos edicion_texto, textos resto_texto, textos error_texto) {
        boolean ret = true;
        StringReader stringreader = null;
        BufferedReader bufferedreader = null;
        String error;
        String linea_leida_texto;
        String edicion = "";
        String resto = "";
        int i = 0;
        try {
            stringreader = new StringReader(texto);
            bufferedreader = new BufferedReader(stringreader);
            while (true) {
                linea_leida_texto = bufferedreader.readLine();
                if (linea_leida_texto == null) {
                    break;
                }
                if (i >= lineas_num) {
                    resto = resto + linea_leida_texto + "\n";
                } else {
                    edicion = edicion + linea_leida_texto + "\n";
                    i = i + 1;
                }
            }
            resto_texto.poner(resto);
            ret = Decoraciones.desdecorar(edicion, edicion_texto, error_texto);
        } catch (Exception e) {
            error = e.getMessage();
            if (error == null) {
                error = ""; //NOI18N
            }
            error_texto.poner("Error en extraer_texto_edicion. " + error);
            ret = false;
        }
        return ret;
    }
}
