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

package flex.pkikeys.Repositories.PKCS11;

import flex.pkikeys.Repositories.PKCS11.resources.PKCS11StringsBundle;
import flex.pkikeys.exceptions.PKIKeysException;

/**
 * PKCS11Exception
 * Clase que indica los errores ocurridos durante el acceso al repositorio en
 * formato PKCS#11, los detalles del error se indican en el mensaje del constructor,
 * además posee una serie de atributos estáticos referentes a mensajes de error
 * propios.
 *
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public class PKCS11Exception extends PKIKeysException {
    final public static String ERROR_PKCS11_TOKEN_DRIVER_NO_LOAD = PKCS11StringsBundle.get(PKCS11StringsBundle.M_ERROR_PKCS11_TOKEN_DRIVER_NO_LOAD);
    final public static String ERROR_PKCS11_TOKEN_NO_COMUNICATION = PKCS11StringsBundle.get(PKCS11StringsBundle.M_ERROR_PKCS11_TOKEN_NO_COMUNICATION);
    final public static String ERROR_PKCS11_TOKEN_NO = PKCS11StringsBundle.get(PKCS11StringsBundle.M_ERROR_PKCS11_TOKEN_NO_PRESENT);
    final public static String ERROR_PKCS11_PARSING_CONFIGURATION = PKCS11StringsBundle.get(PKCS11StringsBundle.M_ERROR_PKCS11_PARSING_CONFIGURATION);

    /**
     * Constructor único para indicar el mensaje del error.
     * 
     * @param message - Mensaje referente al error que se presentó.
     */
    public PKCS11Exception(String message) {
        super(message);
    }
}
