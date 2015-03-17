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

import flex.pkikeys.Repositories.AbstractRepositoryConfiguration;

/**
 * PKCS11Configuration
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public final class PKCS11Configuration extends AbstractRepositoryConfiguration {
    transient final public static String REPOSITORY_CONFIGURATION_STORE_NAME = "name";
    
    public PKCS11Configuration() {
        super();
        put(REPOSITORY_CONFIGURATION_STORE_NAME, null);
    }
    
    public PKCS11Configuration(String name, String path, String password) {
        setName(name);
        setPath(path);
        setPassword(password);
    }

    public String getName() {
        return (String) get(REPOSITORY_CONFIGURATION_STORE_NAME);
    }

    public void setName(String name) {
        put(REPOSITORY_CONFIGURATION_STORE_NAME, name);
    }

    @Override
    public Class getConsumer() {
        return PKCS11.class;
    }
}
