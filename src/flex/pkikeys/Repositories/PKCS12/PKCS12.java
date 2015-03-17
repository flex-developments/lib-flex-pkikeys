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

package flex.pkikeys.Repositories.PKCS12;

import flex.pkikeys.PKIKeysLogger;
import flex.pkikeys.Repositories.PKCS12.resources.PKCS12StringsBundle;
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
 * PKCS12
 * Clase para cargar las claves desde repositorios en formato PKCS#12.
 *
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public final class PKCS12 extends AbstractRepository {
    
    public PKCS12() {
        this(new PKCS12Configuration());
    }
    
    public PKCS12(PKCS12Configuration configuracion) {
        setConfiguration(configuracion);
        setRepositoryType(this.getClass().getSimpleName());
    }
    
    @Override
    public PKCS12Configuration getConfiguration() {
        return (PKCS12Configuration)configuration;
    };

    /**
     * Método para capturar los parámetros de acceso al repositorio desde líneas de consola.
     * Se capturará la ruta del repositorio PKCS#12 y su Contraseña de acceso.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     * @throws flex.pkikeys.exception.ChageRepositoryTypeException
     */
    @Override
    public void loadFromConsole() throws PKIKeysException, ChangeRepositoryTypeException {
        //Leer ruta
        if(getConfiguration().getPath() == null) {
            Console terminal = System.console();
            if (terminal == null) 
                throw new PKIKeysException(PKIKeysException.ERROR_CONSOLE_NO_INSTANCE);
            
            getConfiguration().setPath(terminal.readLine(PKCS12StringsBundle.get(PKCS12StringsBundle.I_PKCS12_FILTER_TT) + ": ")
            );
        }
        //Leer contraseña
        if(getConfiguration().getPassword() == null) {
            try {
                getPasswordFromConsole(configuration);
                
            } catch (    IOException | UnsupportedCallbackException ex) {
                PKIKeysLogger.writeErrorLog(ex);
            }
        }
    }

    /**
     * Método para capturar los parámetros de acceso al repositorio PKCS#12 
     * desde una interáz gráfica. Se capturará Ruta del archivo PKCS#12 y 
     * Contraseña de acceso al mismo.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     * @throws flex.pkikeys.exception.ChageRepositoryTypeException
     */
    @Override
    public void loadFromGUI() throws PKIKeysException, ChangeRepositoryTypeException {
        PKCS12GUI ventanaP12 = new PKCS12GUI(getConfiguration().getPath());
        ventanaP12.getConfiguration();
        getConfiguration().setPath(ventanaP12.getPath());
        getConfiguration().setPassword(ventanaP12.getPassword());
    }
    
    /**
     * Método para cargar las llaves desde el repositorio según los parámetros previamente cargados.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     */
    @Override
    public void loadKeys(String alias)  throws PKIKeysException {
        KeyStore repository = null;
        
        //Verificar contra la lista blanca antes de cargar las llaves
        verifyRepositoryIntegrity();
            
        try {
            repository = KeyStore.getInstance(getRepositoryType());
            repository.load(new FileInputStream(getConfiguration().getPath()), getConfiguration().getPassword().toCharArray());
            
        } catch (KeyStoreException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_STANDARD_UNKNOWN);

        } catch (FileNotFoundException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_NOT_FOUND + " " + getConfiguration().getPath());

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
