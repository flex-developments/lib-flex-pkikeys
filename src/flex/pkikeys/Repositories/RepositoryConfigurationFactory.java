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
import flex.helpers.exceptions.SMimeCoderHelperException;
import flex.pkikeys.exceptions.PKIKeysException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

/**
 * RepositoryConfigurationFactory
 * Clase que implementa el Factory de los herederos de AbstractRepositoryConfiguration.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
final public class RepositoryConfigurationFactory {
    
    //Implementar el Factory por Java Reflection
    /**
     * Obtener una instancia con valores por defecto de la configuracion del
     * tipo de repositorio indicado. Factory con implementacion de Java Reflection.
     * @param type Tipo
     * @return
     * @throws PKIKeysException 
     */
    public static AbstractRepositoryConfiguration getInstance(String type) throws PKIKeysException {
        String className = AbstractRepositoryConfiguration.class.getPackage().getName() + "." + 
                           type + "." + 
                           type + "Configuration";
        try {
            Class newclass = Class.forName(className);
            return (AbstractRepositoryConfiguration) newclass.getConstructor().newInstance();
            
        } catch (ClassNotFoundException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_STANDARD_UNKNOWN);
            
        } catch (NoSuchMethodException | 
                 SecurityException | 
                 InstantiationException | 
                 IllegalAccessException | 
                 IllegalArgumentException | 
                 InvocationTargetException ex) {
            throw new PKIKeysException(ex);
        }
    }
    
    /**
     * Obtener una instancia de AbstractRepositoryConfiguration de acuerdo a un string
 de configuracion. Factory con implementacion de Java Reflection.
     * @param encodedConf
     * @return
     * @throws PKIKeysException 
     */
    public static AbstractRepositoryConfiguration getInstanceFromEncodedConf(String encodedConf) 
    throws PKIKeysException {
        try {
            if(encodedConf == null) return null;
            if(encodedConf.isEmpty()) return null;
            
            //Decodificar y limpiar configuracion
            String decodedConf = new String(SMimeCoderHelper.getSMimeDecoded(encodedConf));
            decodedConf = decodedConf.replace("\\", "\\\\");
            decodedConf = decodedConf.replace("\\n", "\n");
            
            //Cargar configuracion
            Properties props = new Properties();
            props.load( new StringReader(decodedConf) );
            
            //Construir AbstractRepositoryConfiguration requerido
            AbstractRepositoryConfiguration repositoryConfiguration = getInstance(props.getProperty(AbstractRepositoryConfiguration.REPOSITORY_CONFIGURATION_STORE_TYPE));
            for (Map.Entry<Object, Object> e : props.entrySet())
                repositoryConfiguration.put((String)e.getKey(), (String)e.getValue());
            
            return repositoryConfiguration;
            
        } catch (SMimeCoderHelperException | IOException ex) {
            throw new PKIKeysException(ex);
        }
    }
}
