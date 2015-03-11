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

package flex.pkikeys.Repositories;

import flex.pkikeys.exceptions.PKIKeysException;
import java.lang.reflect.InvocationTargetException;

/**
 * RepositoryFactory
 * Clase que implementa el Factory de los herederos de AbstractRepository.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 */
final public class RepositoryFactory {
    
    //Implementar el Factory por Java Reflection
    public static AbstractRepository getInstance(String type) throws PKIKeysException {
        //Construir el canonical
        String className = AbstractRepository.class.getPackage().getName() + "." + 
                           type + "." + 
                           type;
        try {
            Class newclass = Class.forName(className);
            return (AbstractRepository) newclass.getConstructor().newInstance();
            
        } catch (ClassNotFoundException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_STANDARD_UNKNOWN);
            
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new PKIKeysException(ex);
        }
    }
    
    //Implementar el Factory por Java Reflection
    public static AbstractRepository getInstance(AbstractRepositoryConfiguration configuration) 
    throws PKIKeysException {
        if(configuration != null) {
            try {
                Class newclass = configuration.getConsumer();
                
                //Armar argumentos del constructor
                Class[] intArgsClass = new Class[] { configuration.getClass() };
                Object[] intArgs = new Object[] { configuration };
                
                //Invocar constructor con argumentos
                return (AbstractRepository) newclass.getConstructor(intArgsClass).newInstance(intArgs);

            } catch (NoSuchMethodException | 
                     SecurityException | 
                     InstantiationException | 
                     IllegalAccessException | 
                     IllegalArgumentException | 
                     InvocationTargetException ex) {
                throw new PKIKeysException(ex);
            }
            
        } else {
            throw new PKIKeysException(PKIKeysException.ERROR_CONFIGURATION_NULL);
        }
    }
}
