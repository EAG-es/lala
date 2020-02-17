/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innui.edicion;

import innui.archivos.Utf8;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emilio
 */
public class Deshacer_con_archivos {
    public static int max_deshacer_num = 50;
    public List<File> archivos_lista = new ArrayList();
    public int posicion_guardar = 0;
    public int posicion_deshacer = -1;
    
    public boolean iniciar(String [] error) {
        boolean ret = true;
        archivos_lista.clear();
        posicion_guardar = 0;
        posicion_deshacer = 0;
        return ret;
    }
    
    public boolean guardar_cambio(String texto, String [] error) {
        boolean ret = true;
        File temporal_file = null;
        try {
            posicion_guardar = posicion_deshacer + 1; 
            posicion_guardar = posicion_guardar % max_deshacer_num;
            if (archivos_lista.size() >= posicion_guardar) {
                if (posicion_guardar < max_deshacer_num) {
                    temporal_file = File.createTempFile("innui_edicion_", ".tmp.html");
                    temporal_file.deleteOnExit();
                    archivos_lista.add(temporal_file);
                } else {
                    temporal_file = archivos_lista.get(posicion_guardar);
                }
                ret = Utf8.escribir(temporal_file, texto, error);
                if (ret) {
                    posicion_deshacer = posicion_guardar;
                    posicion_guardar = posicion_guardar + 1;
                }
            } else {
                ret = false;
                error[0] = "Posición incorrecta en la lista de archivos con los que deshacer. ";
            }
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
    
    public String deshacer_cambio(String [] error) {
        boolean ret = true;
        String retorno = null;
        File temporal_file = null;
        int tam;
        int antigua_deshacer;
        antigua_deshacer = posicion_deshacer;
        posicion_deshacer = posicion_deshacer - 1;
        if (posicion_deshacer < 0) {
            posicion_deshacer = max_deshacer_num - 1;
        }
        if (posicion_deshacer == posicion_guardar) {
            ret = false;
            error[0] = "No hay más cambios que deshacer. ";
            posicion_deshacer = antigua_deshacer;
        }
        if (ret) {
            tam = archivos_lista.size();
            if (posicion_deshacer < tam) {
                temporal_file = archivos_lista.get(posicion_deshacer);
                retorno = Utf8.leer(temporal_file, error);
            } else {
                ret = false;
                error[0] = "No hay más cambios que deshacer. ";
                posicion_deshacer = antigua_deshacer;
            }
        }
        return retorno;
    }

    public String rehacer_cambio(String [] error) {
        boolean ret = true;
        String retorno = null;
        File temporal_file = null;
        int tam;
        int antigua_deshacer;
        antigua_deshacer = posicion_deshacer;
        posicion_deshacer = posicion_deshacer + 1;
        if (posicion_deshacer >= max_deshacer_num) {
            posicion_deshacer = 0;
        }
        if (posicion_deshacer == posicion_guardar) {
            ret = false;
            error[0] = "No hay más cambios que rehacer. ";
            posicion_deshacer = antigua_deshacer;
        }
        if (ret) {
            tam = archivos_lista.size();
            if (posicion_deshacer < tam) {
                temporal_file = archivos_lista.get(posicion_deshacer);
                retorno = Utf8.leer(temporal_file, error);
            } else {
                ret = false;
                error[0] = "No hay más cambios que rehacer. ";
                posicion_deshacer = antigua_deshacer;
            }
        }
        return retorno;
    }
}
