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

import flex.helpers.FileHelper;
import flex.pkikeys.i18n.I18n;
import flex.pkikeys.PKIKeysLogger;
import flex.pkikeys.exceptions.ChangeRepositoryTypeException;
import flex.pkikeys.exceptions.PKIKeysException;
import java.io.Console;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * AbstractRepository
 * Clase abstracta para definir la estructura y comportamiento de las
 * clases especializadas en el acceso a los diferentes tipos de repositorios.
 * (JKS, PKCS11, PKCS12, etc). Todo Heredero debe crear un paquete en 
 * flex.pkikeys.Repositories con su mismo nombre (Ver la forma en que se han 
 * implementado JSK, PKCS11 y PKCS12, para mantener compatibilidad con RepositoryFactory.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 * @version 1.0
 */
public abstract class AbstractRepository {
    //OJO...
    final public static String REPOSITORY_TYPE_JKS = "JKS";
    final public static String REPOSITORY_TYPE_PKCS11 = "PKCS11";
    final public static String REPOSITORY_TYPE_PKCS12 = "PKCS12";
    
    protected String repositorioType = null;
    protected PrivateKey privateKey = null;
    protected X509Certificate signCertificate = null;
    protected List<X509Certificate> allCertificates = null;
    protected AbstractRepositoryConfiguration configuration = null;
    protected Provider cryptographyProvider = null;
    
    /**
     * Método para capturar los parámetros de acceso al repositorio desde líneas 
     * de consola.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     * @throws flex.pkikeys.exceptions.ChangeRepositoryTypeException
     */
    public abstract void loadFromConsole() throws PKIKeysException, ChangeRepositoryTypeException;

    /**
     * Método para capturar los parámetros de acceso al repositorio gráfica.
     *
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     * @throws flex.pkikeys.exceptions.ChangeRepositoryTypeException
     */
    public abstract void loadFromGUI() throws PKIKeysException, ChangeRepositoryTypeException;
    
    /**
     * Método para cargar las llaves desde el repositorio según la configuración 
     * establecida y arrojar la salida en consola.
     *
     * @param alias
     * @throws PKIKeysException La razón de la excepción será especificada en el 
     * mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     */
    public abstract void loadKeys(String alias) throws PKIKeysException;

    /**
     * Método que finalmente ejecuta el ingreso al repositorio previamente inicializado en el método cargarLlaves(),
     * y extrae las llaves correspondientes al Alias que se solicite.
     * 
     * @param repository - Repositorio previmente inicializado en el método cargarLlaves()
     * @param password
     * @param alias - Alias que identifica a la llave privada que se desea importar
     * @throws PKIKeysException La razón de la excepción será especificada en el mensaje y de no ser específica se imprimirá en consola la traza de la excepción.
     */
    protected void importKeys(KeyStore repository, String password, String alias) 
    throws PKIKeysException {
        try {
            if (!repository.aliases().hasMoreElements()) {
                throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_EMPTY);
                
            } else {
                if (alias == null) alias = "";
                
                if ( (repository.size() == 1) && (alias.isEmpty())){
                    Enumeration en = repository.aliases();
                    alias = (String) en.nextElement();
                    
                } else if( alias.isEmpty() ) {
                    RepositoryGUIAlias aliasesWindow = new RepositoryGUIAlias(repository.aliases());
                    aliasesWindow.getAlias();
                    alias = aliasesWindow.getSelectedAlias();
                }
                
                if(password == null) privateKey = (PrivateKey)repository.getKey(alias, null);
                else privateKey = (PrivateKey)repository.getKey(alias, password.toCharArray());
                
                signCertificate = (X509Certificate) repository.getCertificate(alias);
                
                //Cargar certificados presentes en el repositorio
                allCertificates = new ArrayList<>();
                Enumeration en = repository.aliases();
                while(en.hasMoreElements()) {
                    allCertificates.add((X509Certificate) repository.getCertificate(en.nextElement().toString()));
                }
            }
            
        } catch (KeyStoreException ex) {
            PKIKeysLogger.writeErrorLog(ex);
            throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_LOAD);
            
        } catch (NoSuchAlgorithmException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_ALGORITHMS_UNKNOWN);
            
        } catch (UnrecoverableKeyException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_PRIVATE_KEY_NOT_FOUND);
        }
    }
    
    /**
     * Método que captura la contraseña desde una linea de consola sin dejar ver lo que el usuario teclea.
     *
     * @param configuration
     * @throws PKIKeysException Se lanza esta excepcion si no es posible instancias una consola para la captura de la contrasñea.
     * @throws java.io.IOException
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     */
    protected void getPasswordFromConsole(AbstractRepositoryConfiguration configuration) 
    throws PKIKeysException, IOException, UnsupportedCallbackException {
        Console terminal = System.console();
        if (terminal == null) 
            throw new PKIKeysException(PKIKeysException.ERROR_CONSOLE_NO_INSTANCE);
        
        //OJO... Implementar CallbackHandler
        configuration.setPassword(new String(terminal.readPassword(I18n.get(I18n.M_PASSWORD_READ))));
//        CallbackHandler callbackHandler = null;
//        Callback[] callbacks = new Callback[1];
//
//        callbacks[0] = new PasswordCallback("Introduzca su contraseña: ", false);
//        try {
//            // Invocamos al CallbackHandler
//            callbackHandler.handle(callbacks);
//            
//        } catch (IOException | UnsupportedCallbackException ex) {
//        }
//        
//        configuracion.setContraseña(String.valueOf(((PasswordCallback) callbacks[0]).getPassword()));
//        
//        ((PasswordCallback) callbacks[1]).clearPassword();
    }
    
    /**
     * Comparacion de los CheckSum de los repositorios con la lista blanca.
     * 
     * @throws PKIKeysException 
     */
    public void verifyRepositoryIntegrity() throws PKIKeysException {
        try {
            if( 
                ( getConfiguration().isVerifyRepositoryIntegrity() ) && 
                ( getConfiguration().getRepositoryWhiteList() != null )
              ) {
                
                String hashDriver = FileHelper.getCheckSum(
                        getConfiguration().getPath(), 
                        getConfiguration().getRepositoryWhiteList().getHashAlgWhiteList()
                );
                
                if(getConfiguration().getRepositoryWhiteList().contains(hashDriver)) return;
                throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_RESTRICTED);
                
            } else if ( getConfiguration().isVerifyRepositoryIntegrity() ) {
                throw new PKIKeysException(PKIKeysException.ERROR_WHITELIST_NULL);
            }
            
        } catch(IOException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_READ);
            
        } catch(NoSuchAlgorithmException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_HASH);
        }
    }
    
    /**
     * Obtener certificados dentro del repositorio
     * 
     * @return Retorna los certificados dentro de la zona publica del repositorio
     */
    public List<X509Certificate> getAllCertificates() {
        return allCertificates;
    }
    /**
     * Obtener Certificado.
     *
     * @return Retorna el certificado que fue cargado desde el repositorio.
     */
    public X509Certificate getSignCertificate() {
        return signCertificate;
    }

    /**
     * Obtener Llave Privada.
     *
     * @return Retorna (de ser posible) la llave privada que fue cargada desde el repositorio.
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    
    /**
     * Método para obtener configuración del repositorio.
     *
     * @return
     */
    public AbstractRepositoryConfiguration getConfiguration() {
        return configuration;
    };
    
    protected void setConfiguration (AbstractRepositoryConfiguration configuration) {
        this.configuration = configuration;
    }
    
    public String getRepositoryType() {
        return repositorioType;
    }
    
    protected void setRepositoryType(String repositoryType) {
        this.repositorioType = repositoryType;
    }
    
    public Provider getRepositoryCryptographyProvider() {
        return cryptographyProvider;
    }
    
    protected void setRepositoryCryptographyProvider(Provider cryptographyProvider) {
        this.cryptographyProvider = cryptographyProvider;
    }
}
