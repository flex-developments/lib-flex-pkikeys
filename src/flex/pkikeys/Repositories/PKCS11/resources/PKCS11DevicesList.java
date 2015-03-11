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

package flex.pkikeys.Repositories.PKCS11.resources;

import flex.pkikeys.PKIKeysLogger;
import flex.helpers.SystemHelper;
import flex.helpers.exceptions.SystemHelperException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * PKCS11DevicesList
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 */
public class PKCS11DevicesList extends LinkedHashMap<String, String> {
    
    public PKCS11DevicesList(String name, String library) {
        try {
            if(name == null) name = "";
            if(library == null) library = "";
        
            Properties prop = new Properties();
            InputStream lector = null;
            if(SystemHelper.isWindows()) 
                lector = PKCS11DevicesList.class.getResourceAsStream("PKCS11WindowsList.properties");
            else if(SystemHelper.isLinux()) 
                lector = PKCS11DevicesList.class.getResourceAsStream("PKCS11LinuxList.properties");
            
            prop.load(lector);
            
            for( String key : prop.stringPropertyNames() ) {
                if(!key.startsWith("#"))
                    put(key, prop.getProperty(key));
            }
            
            lector.close();
            
        } catch (SystemHelperException | IOException ex) {
            PKIKeysLogger.writeErrorLog(ex);
        }
        
        //Si el dispositivo que viene de la configuración anterior no existe
        //dentro del archivo de configuracion se agrega al hashmap
        if(name.compareTo(PKCS11StringsBundle.get(PKCS11StringsBundle.I_PKCS11_LST_TOKEN_NAME_OTHER)) == 0)
            name = PKCS11StringsBundle.get(PKCS11StringsBundle.I_PKCS11_LST_TOKEN_NAME_OTHER) + ".";
        
        if( (!name.isEmpty()) && (!library.isEmpty()) ) {
            if(get(name) == null) {
                put(name, library);
            }
        }

        //Agregar el último por defecto
        put(
            PKCS11StringsBundle.get(PKCS11StringsBundle.I_PKCS11_LST_TOKEN_NAME_OTHER), 
            PKCS11StringsBundle.get(PKCS11StringsBundle.I_PKCS11_LST_TOKEN_DRIVER_PATH_OTHER)
        );
    }
}
