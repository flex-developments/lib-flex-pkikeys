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

package flex.pkikeys.Repositories.JKS;

import flex.pkikeys.Repositories.JKS.resources.JKSStringsBundle;
import flex.pkikeys.PKIKeysLogger;
import flex.pkikeys.Repositories.AbstractRepository;
import flex.pkikeys.exceptions.ChangeRepositoryTypeException;
import flex.pkikeys.exceptions.PKIKeysException;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * JKS
 * Clase para cargar claves desde repositorios en formato JKS.
 *
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public class JKS extends AbstractRepository {
    
    public JKS() {
        this(new JKSConfiguration());
    }
    
    public JKS(JKSConfiguration configuration) {
        setConfiguration(configuration);
        setRepositoryType(this.getClass().getSimpleName());
    }
    
    @Override
    public JKSConfiguration getConfiguration() {
        return (JKSConfiguration)configuration;
    };
    
    /**
     * Método para capturar los parámetros de acceso al repositorio desde líneas 
     * de consola.
     * Se capturará la ruta del repositorio JKS y su Contraseña de acceso.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     * @throws flex.pkikeys.exceptions.ChangeRepositoryTypeException
     */
    @Override
    public void loadFromConsole() throws PKIKeysException, ChangeRepositoryTypeException {
        if( getConfiguration().getPath() == null) {
            Console terminal = System.console();
            if (terminal==null)
                throw new PKIKeysException(PKIKeysException.ERROR_CONSOLE_NO_INSTANCE);
            
            getConfiguration().setPath(terminal.readLine(JKSStringsBundle.get(JKSStringsBundle.I_JKS_FILTER_TT) + ": ")
            );
        }
        
        if(getConfiguration().getPassword() == null) {
            try {
                getPasswordFromConsole(getConfiguration());
                
            } catch (    IOException | UnsupportedCallbackException ex) {
                PKIKeysLogger.writeErrorLog(ex);
            }
        }
    }

    /**
     * Método para capturar los parámetros de acceso al repositorio JKS desde 
     * una interáz gráfica.
     * Se capturará Ruta del archivo JKS y Contraseña de acceso al mismo.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     * @throws flex.pkikeys.exceptions.ChangeRepositoryTypeException
     */
    @Override
    public void loadFromGUI() throws PKIKeysException, ChangeRepositoryTypeException {
        JKSGUI ventanaJKS = new JKSGUI(getConfiguration().getPath());
        ventanaJKS.getConfiguration();
        
        getConfiguration().setPath(ventanaJKS.getPath());
        getConfiguration().setPassword(ventanaJKS.getPassword());
    }

    /**
     * Método para cargar las llaves desde el repositorio según los parámetros 
     * previamente cargados.
     *
     * @param alias
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     */
    @Override
    public void loadKeys(String alias)  throws PKIKeysException {
        KeyStore repository = null;
        
        //Verificar contra la lista blanca antes de cargar las llaves
        verifyRepositoryIntegrity();
        
        try {
            repository = KeyStore.getInstance(getRepositoryType());
            repository.load(
                    new FileInputStream(getConfiguration().getPath()), 
                                        getConfiguration().getPassword().toCharArray()
            );

        } catch (KeyStoreException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_STANDARD_UNKNOWN);

        } catch (FileNotFoundException ex) {
            throw new PKIKeysException(
                    PKIKeysException.ERROR_REPOSITORY_NOT_FOUND + " " + 
                    getConfiguration().getPath()
            );

        } catch (IOException ex) {
            if (ex.getCause() != null) {
                if(ex.getCause() instanceof javax.crypto.BadPaddingException)
                    throw new PKIKeysException(PKIKeysException.ERROR_INVALID_PASSWORD);
            }
            PKIKeysLogger.writeErrorLog(ex);
            throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_LOAD);

        } catch (NoSuchAlgorithmException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_ALGORITHMS_UNKNOWN);

        } catch (CertificateException ex) {
            PKIKeysLogger.writeErrorLog(ex);
            throw new PKIKeysException(PKIKeysException.ERROR_CERTIFICATE_READ);
        }
        
        importKeys(repository, getConfiguration().getPassword(), alias);
    }
}
