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

package flex.pkikeys.Repositories;

import flex.helpers.SMimeCoderHelper;
import flex.helpers.StringHelper;
import flex.helpers.exceptions.SMimeCoderHelperException;
import flex.pkikeys.exceptions.PKIKeysException;
import java.util.LinkedHashMap;

/**
 * AbstractRepositoryConfiguration
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public abstract class AbstractRepositoryConfiguration extends LinkedHashMap<String, Object> {
    transient final public static String REPOSITORY_CONFIGURATION_STORE_TYPE = "type";
    transient final public static String REPOSITORY_CONFIGURATION_STORE_PATH = "path";
    transient final public static String REPOSITORY_CONFIGURATION_STORE_PASSWORD = "password";
    transient final public static String REPOSITORY_CONFIGURATION_STORE_VERIFY_INTEGRITY = "verifyIntegrity";
    transient final public static String REPOSITORY_CONFIGURATION_STORE_WHITE_LIST = "whiteList";
    transient final public static String USER_CONFIGURATION_PROXY = "proxy";
    transient final public static String USER_CONFIGURATION_TSS = "tss";
    transient final public static String USER_CONFIGURATION_AUTHORITIES = "authorities";
    
    /**
     * Constructor con valores inicializados por default.
     */
    public AbstractRepositoryConfiguration() {
        put(REPOSITORY_CONFIGURATION_STORE_TYPE, AbstractRepository.REPOSITORY_TYPE_PKCS12);
        put(REPOSITORY_CONFIGURATION_STORE_PATH, null);
        put(REPOSITORY_CONFIGURATION_STORE_PASSWORD, null);
        put(USER_CONFIGURATION_PROXY, null);
        put(USER_CONFIGURATION_TSS, null);
        put(USER_CONFIGURATION_AUTHORITIES, null);
        put(REPOSITORY_CONFIGURATION_STORE_VERIFY_INTEGRITY, false);
        put(REPOSITORY_CONFIGURATION_STORE_WHITE_LIST, null);
    }
    
    /**
     * Obtener la clase heredera de AbstractRepository que implementa el heredero de 
 AbstractRepositoryConfiguration.
     * 
     * @return Clase que implementara la configuracion.
     */
    public abstract Class getConsumer();
    
    /**
     * Obtener string de configuracion codificado SMime/Base64.
     * 
     * @return String de configuracion codificada SMime/Base64.
     * @throws PKIKeysException 
     */
    public String getConfigurationEncode() throws PKIKeysException {
        String encodedConf = "";
        
        //Agregar al string de configuracion unicamente los parametros String a excepcion del password.
        for(String key : keySet())
            if( (get(key) instanceof String) && (key.compareTo(REPOSITORY_CONFIGURATION_STORE_PASSWORD) != 0) )
                encodedConf = encodedConf + key + "=" + get(key) + "\n";
        encodedConf = StringHelper.removeLastCarrierReturn(encodedConf);
        
        //Codificar configuracion
        try {
            encodedConf = SMimeCoderHelper.getSMimeEncoded(encodedConf.getBytes());
        } catch (SMimeCoderHelperException ex) {
            throw new PKIKeysException(ex);
        }
        encodedConf = StringHelper.removeLastCarrierReturn(encodedConf);
        
        return encodedConf;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    ///////// Geters y Seters para facilitar acceso a los parametros ///////////
    public String getPath() {
        return (String) get(REPOSITORY_CONFIGURATION_STORE_PATH);
    }

    public void setPath(String path) {
        put(REPOSITORY_CONFIGURATION_STORE_PATH, path);
    }
    
    public String getPassword() {
        return (String) get(REPOSITORY_CONFIGURATION_STORE_PASSWORD);
    }

    public void setPassword(String password) {
        put(REPOSITORY_CONFIGURATION_STORE_PASSWORD, password);
    }

    public boolean isVerifyRepositoryIntegrity() {
        return (boolean) get(REPOSITORY_CONFIGURATION_STORE_VERIFY_INTEGRITY);
    }

    public void setVerifyRepositoryIntegrity(boolean verifyRepositoryIntegrity) {
        put(REPOSITORY_CONFIGURATION_STORE_VERIFY_INTEGRITY, verifyRepositoryIntegrity);
    }

    public RepositoriesWhiteList getRepositoryWhiteList() {
        return (RepositoriesWhiteList) get(REPOSITORY_CONFIGURATION_STORE_WHITE_LIST);
    }
    
    public void setRepositoryWhiteList(RepositoriesWhiteList repositoryWhiteList) {
        put(REPOSITORY_CONFIGURATION_STORE_WHITE_LIST, repositoryWhiteList);
    }
    ////////////////////////////////////////////////////////////////////////////
}
