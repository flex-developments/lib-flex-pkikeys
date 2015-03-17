/*
 * lib-flex-pkikeys
 *
 * Copyright (C) 2010
 * Ing. Felix D. Lopez M. - flex.developments en gmail
 * 
 * Desarrollo apoyado por la Superintendencia de Servicios de Certificación 
 * Electrónica (SUSCERTE) durante 2010-2014 por:
 * Ing. Felix D. Lopez M. - flex.developments en gmail | flopez en suscerte gob ve
 * Ing. Yessica De Ascencao - yessicadeascencao en gmail | ydeascencao en suscerte gob ve
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

package flex.pkikeys.Repositories.PKCS11.resources;

import flex.pkikeys.i18n.*;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * PKCS11StringsBundle
 * Clase estatica para controlar la internacionalizacion de los mensajes de lo
 * relacionado al repositorio PKCS11.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @version 1.0
 */
public class PKCS11StringsBundle {
    private static String LANG_PATH = "flex/pkikeys/Repositories/PKCS11/resources/PKCS11";
    private static Locale LANGUAGE = I18n.getLanguage();
    private static ResourceBundle bundle = ResourceBundle.getBundle(LANG_PATH, LANGUAGE);
    
    //List Resource Keys........................................................
    final public static String M_ERROR_PKCS11_TOKEN_NO_PRESENT = "M_ERROR_PKCS11_TOKEN_NO_PRESENT";
    final public static String M_ERROR_PKCS11_TOKEN_NO_NAME = "M_ERROR_PKCS11_TOKEN_NO_NAME";
    final public static String M_ERROR_PKCS11_TOKEN_DRIVER_NO_LOAD = "M_ERROR_PKCS11_TOKEN_DRIVER_NO_LOAD";
    final public static String M_ERROR_PKCS11_TOKEN_DRIVER_NO_PATH = "M_ERROR_PKCS11_TOKEN_DRIVER_NO_PATH";
    final public static String M_ERROR_PKCS11_TOKEN_NO_COMUNICATION = "M_ERROR_PKCS11_TOKEN_NO_COMUNICATION";
    final public static String M_ERROR_PKCS11_PARSING_CONFIGURATION = "M_ERROR_PKCS11_PARSING_CONFIGURATION";
    final public static String I_PKCS11_TITLE = "I_PKCS11_TITLE";
    final public static String I_PKCS11_TITLE_SUFIX_ERROR = "I_PKCS11_TITLE_SUFIX_ERROR";
    final public static String I_PKCS11_L_TOKEN_NAME = "I_PKCS11_L_TOKEN_NAME";
    final public static String I_PKCS11_L_TOKEN_DRIVER_PATH = "I_PKCS11_L_TOKEN_DRIVER_PATH";
    final public static String I_PKCS11_LST_TOKEN_NAME_OTHER = "I_PKCS11_LST_TOKEN_NAME_OTHER";
    final public static String I_PKCS11_LST_TOKEN_DRIVER_PATH_OTHER = "I_PKCS11_LST_TOKEN_DRIVER_PATH_OTHER";
    final public static String I_PKCS11_B_ACCEPT = "I_PKCS11_B_ACCEPT";
    final public static String I_PKCS11_B_CANCEL = "I_PKCS11_B_CANCEL";
    final public static String I_PKCS11_B_CHANGE = "I_PKCS11_B_CHANGE";
    final public static String I_PKCS11_FILTER_DESCRIPTION_WINDOWS = "I_PKCS11_FILTER_DESCRIPTION_WINDOWS";
    final public static String I_PKCS11_FILTER_DESCRIPTION_LINUX = "I_PKCS11_FILTER_DESCRIPTION_LINUX";
    final public static String I_PKCS11_FILTER_TT = "I_PKCS11_FILTER_TT";
    final public static String I_PKCS11_L_PASS = "I_PKCS11_L_PASS";
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
