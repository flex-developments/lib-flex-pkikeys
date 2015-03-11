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

package flex.pkikeys.exceptions;

import flex.pkikeys.i18n.I18n;

/**
 * PKCSKeysException
 * Clase que indica los errores ocurridos durante la carga de las llaves, los
 * detalles del error se indican en el mensaje del constructor, además posee una
 * serie de atributos estáticos referentes a mensajes de error comunes entre los
 * diferentes estándares de respositorios.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 */
public class PKIKeysException extends Exception {
    final public static String ERROR_INVALID_PASSWORD = I18n.get(I18n.M_ERROR_INVALID_PASSWORD);
    final public static String ERROR_STANDARD_UNKNOWN = I18n.get(I18n.M_ERROR_STANDARD_UNKNOWN);
    final public static String ERROR_REPOSITORY_LOAD = I18n.get(I18n.M_ERROR_REPOSITORY_LOAD);
    final public static String ERROR_REPOSITORY_READ = I18n.get(I18n.M_ERROR_REPOSITORY_READ);
    final public static String ERROR_REPOSITORY_NULL = I18n.get(I18n.M_ERROR_REPOSITORY_NULL);
    final public static String ERROR_REPOSITORY_EMPTY = I18n.get(I18n.M_ERROR_REPOSITORY_EMPTY);
    final public static String ERROR_REPOSITORY_NOT_FOUND = I18n.get(I18n.M_ERROR_REPOSITORY_NOT_FOUND);
    final public static String ERROR_REPOSITORY_RESTRICTED = I18n.get(I18n.M_ERROR_REPOSITORY_RESTRICTED);
    final public static String ERROR_REPOSITORY_HASH = I18n.get(I18n.M_ERROR_REPOSITORY_HASH);
    final public static String ERROR_ALGORITHMS_UNKNOWN = I18n.get(I18n.M_ERROR_ALGORITHMS_UNKNOWN);
    final public static String ERROR_PRIVATE_KEY_NOT_FOUND = I18n.get(I18n.M_ERROR_PRIVATE_KEY_NOT_FOUND);
    final public static String ERROR_CERTIFICATE_READ = I18n.get(I18n.M_ERROR_CERTIFICATE_READ);
    final public static String ERROR_CONSOLE_NO_INSTANCE = I18n.get(I18n.M_ERROR_CONSOLE_NO_INSTANCE);
    final public static String ERROR_DRIVER_CORRUPTED = I18n.get(I18n.M_ERROR_DRIVER_CORRUPTED);
    final public static String ERROR_CONFIGURATION_NULL = I18n.get(I18n.M_ERROR_CONFIGURATION_NULL);
    final public static String ERROR_WHITELIST_LOAD = I18n.get(I18n.M_ERROR_WHITELIST_LOAD);
    final public static String ERROR_WHITELIST_NULL = I18n.get(I18n.M_ERROR_WHITELIST_NULL);

    /**
     * Constructor único para indicar el mensaje del error.
     *
     * @param message - Mensaje referente al error que se presentó.
     */
    public PKIKeysException(String message) {
        super(message);
    }
    
    public PKIKeysException(Throwable ex) {
        super(ex);
    }
}
