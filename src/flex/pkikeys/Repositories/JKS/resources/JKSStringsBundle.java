/*
 * lib-flex-pkikeys
 *
 * Copyright (C) 2010
 * Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * 
 * Desarrollo apoyado por la Superintendencia de Servicios de Certificación 
 * Electrónica (SUSCERTE) durante 2010-2014 por:
 * Ing. Felix D. Lopez M. - flex.developments@gmail.com | flopez@suscerte.gob.ve
 * Ing. Yessica De Ascencao - yessicadeascencao@gmail.com | ydeascencao@suscerte.gob.ve
 *
 * Este programa es software libre; Usted puede usarlo bajo los terminos de la
 * licencia de software GPL version 2.0 de la Free Software Foundation.
 *
 * Este programa se distribuye con la esperanza de que sea util, pero SIN
 * NINGUNA GARANTIA; tampoco las implicitas garantias de MERCANTILIDAD o
 * ADECUACION A UN PROPOSITO PARTICULAR.
 * Consulte la licencia GPL para mas detalles. Usted debe recibir una copia
 * de la GPL junto con este programa; si no, escriba a la Free Software
 * Foundation Inc. 51 Franklin Street,5 Piso, Boston, MA 02110-1301, USA.
 */

package flex.pkikeys.Repositories.JKS.resources;

import flex.pkikeys.i18n.*;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JKSStringsBundle
 * Clase estatica para controlar la internacionalizacion de los mensajes de lo
 * relacionado al repositorio JKS.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @version 1.0
 */
public class JKSStringsBundle {
    private static String LANG_PATH = "flex/pkikeys/Repositories/JKS/resources/JKS";
    private static Locale LANGUAGE = I18n.getLanguage();
    private static ResourceBundle bundle = ResourceBundle.getBundle(LANG_PATH, LANGUAGE);
    
    //List Resource Keys........................................................
    final public static String M_ERROR_JKS_NO_PATH = "M_ERROR_JKS_NO_PATH";
    final public static String M_ERROR_JKS_EMPTY_PASS = "M_ERROR_JKS_EMPTY_PASS";
    final public static String I_JKS_TITLE = "I_JKS_TITLE";
    final public static String I_JKS_TITLE_SUFIX_ERROR = "I_JKS_TITLE_SUFIX_ERROR";
    final public static String I_JKS_L_PATH = "I_JKS_L_PATH";
    final public static String I_JKS_L_PASS = "I_JKS_L_PASS";
    final public static String I_JKS_B_ACCEPT = "I_JKS_B_ACCEPT";
    final public static String I_JKS_B_CANCEL = "I_JKS_B_CANCEL";
    final public static String I_JKS_B_CHANGE = "I_JKS_B_CHANGE";
    final public static String I_JKS_FILTER_DESCRIPTION = "I_JKS_FILTER_DESCRIPTION";
    final public static String I_JKS_FILTER_TT = "I_JKS_FILTER_TT";
    final public static String I_JKS_TT_PASS = "I_JKS_TT_PASS";
    //--------------------------------------------------------------------------
    
    /**
     * Obtener String internacionalizado.
     * 
     * @param key Clave del string dentro del bundle.
     * 
     * @return valor de la clave dentro del bundle.
     */
    public static String get(String key) {
        return ResourceBundle.getBundle(LANG_PATH, LANGUAGE).getString(key);
    }
    
    /**
     * Obtener String internacionalizado con formato.
     * 
     * @param key Clave del string dentro del bundle.
     * @param arguments Argumentos para el formato.
     * 
     * @return valor de la clave dentro del bundle con formato procesado.
     */
    public static String get(String key, Object ... arguments) {
        MessageFormat temp = new MessageFormat(get(key));
        return temp.format(arguments);
    }
    
    /**
     * Obtener todas las keys del buundle.
     * 
     * @return Enumeration de las keys.
     */
    public static Enumeration<String> getKeys() {
        return bundle.getKeys();
    }
    
    //////////////////////////// Geters and Seters /////////////////////////////
    /**
     * Obtener el lenguaje utilizado por la libreria para la internacionalizacion
     * de los mensajes.
     * 
     * @return Lenguaje para la internacionalizacion de los mensajes.
     */
    public static Locale getLanguage() {
        return LANGUAGE;
    }
    
    /**
     * Establecer el lenguaje utilizado por la libreria para la internacionalizacion
     * de los mensajes.
     * Ejemplos:
     *      I18n.setLanguage(es);
     *      I18n.setLanguage(en);
     *      I18n.setLanguage(es-VE);
     *      I18n.setLanguage(es-ES);
     * @param language 
     */
    public static void setLanguage(String language) {
        LANGUAGE = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle(LANG_PATH, LANGUAGE);
    }

    public static String getLangPath() {
        return LANG_PATH;
    }

    public static void setLangPath(String langPath) {
        LANG_PATH = langPath;
        bundle = ResourceBundle.getBundle(LANG_PATH, LANGUAGE);
    }
}
