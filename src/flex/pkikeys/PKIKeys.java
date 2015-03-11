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

package flex.pkikeys;

import flex.pkikeys.Repositories.RepositoryGUIType;
import flex.pkikeys.Repositories.AbstractRepository;
import flex.pkikeys.Repositories.AbstractRepositoryConfiguration;
import flex.pkikeys.Repositories.RepositoryFactory;
import flex.pkikeys.Repositories.RepositoriesWhiteList;
import flex.pkikeys.exceptions.ChangeRepositoryTypeException;
import flex.pkikeys.exceptions.PKIKeysException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * PKIKeys
 * Clase facade que provee acceso a llaves criptográficas contenida en un repositorio.
 * Repositorios implementados como herederos de flex.pkikeys.Repositories.AbstractRepository.
 * Ejemplo: JKS, PKCS#11 y PKCS#12.
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 * @version 1.0
 */
public final class PKIKeys {
    private AbstractRepository repository = null;
    
    public PKIKeys() throws PKIKeysException {}
    
    /**
     * Construye el repositorio desde el estandar solicitado.
     * 
     * @param repositoryType De acuerdo a los valores declarados en
     * flex.pkikeys.Repositorios.Repositorio.REPOSITORIO_TYPE_*
     * @throws PKIKeysException La razon de la excepcion sera especificada en el 
     * mensaje y de no ser asi se imprimira en consola la traza de la excepcion.
     */
    public PKIKeys (String repositoryType) throws PKIKeysException {
        repository = RepositoryFactory.getInstance(repositoryType);
    }
    
    /**
     * Construye el repositorio desde la configuracion.
     * 
     * @param configuration Configuracion heredera de flex.pkikeys.Repositories.AbstractRepositoryConfiguration
     * @throws PKIKeysException La razon de la excepcion sera especificada en el 
     * mensaje y de no ser asi se imprimira en consola la traza de la excepcion.
     */
    public PKIKeys (AbstractRepositoryConfiguration configuration) throws PKIKeysException {
        repository = RepositoryFactory.getInstance(configuration);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Cargar parametros de acceso al repositorio desde consola.
     *
     * @throws PKIKeysException La razon de la excepcion sera especificada en el 
     * mensaje y de no ser asi se imprimira en consola la traza de la excepcion.
     */
    public void loadFromConsole() throws PKIKeysException {
        try {
            if(repository == null)
                throw new PKIKeysException(PKIKeysException.ERROR_REPOSITORY_NULL);
            
            repository.loadFromConsole();
            
        } catch (ChangeRepositoryTypeException ex) {
            repository = null;
            loadFromConsole(); 
        }
    }

    /**
     * Cargar parametros de acceso al repositorio desde interfaz grafica.
     *
     * @throws PKIKeysException La razon de la excepcion sera especificada en el 
     * mensaje y de no ser asi se imprimira en consola la traza de la excepcion.
     */
    public void loadFromGUI() throws PKIKeysException {
        try {
            if(repository == null) {
                RepositoryGUIType repoWin = new RepositoryGUIType();
                repoWin.getKSType();
                String selectedType = repoWin.getSelectedKSType();
                repository = RepositoryFactory.getInstance(selectedType);
            }
            repository.loadFromGUI();
            
        } catch (ChangeRepositoryTypeException ex) {
            repository = null;
            loadFromGUI(); 
        }
    }

    /**
     * Cargar llaves del repositorio.
     *
     * @param alias
     * @throws PKIKeysException La razon de la excepcion sera especificada en el 
     * mensaje y de no ser asi se imprimira en consola la traza de la excepcion.
     */
    public void loadKeys(String alias) throws PKIKeysException {
        repository.loadKeys(alias);
        System.gc();
    }
    
    ///////////////////////// Geters and Seters ////////////////////////////////
    
    public void setWitheListRepositories(RepositoriesWhiteList whiteList) {
        repository.getConfiguration().setRepositoryWhiteList(whiteList);
    }
    
    public RepositoriesWhiteList getWitheListRepositories() {
        return repository.getConfiguration().getRepositoryWhiteList();
    }
    
    public void setVerifyRepositoryIntegrity(boolean verifyIntegrity) {
        repository.getConfiguration().setVerifyRepositoryIntegrity(verifyIntegrity);
    }
    
    public boolean isVerifyRepositoryIntegrity() {
        return repository.getConfiguration().isVerifyRepositoryIntegrity();
    }
    
    /**
     * Obtener algoritmo de hash utilizado para construir la lista blanca de repositorios
     * 
     * @return Algoritmo de hash utilizado para construir la lista blanca.
     */
    public String getHashAlgFromWhiteListRepositories() {
        return getWitheListRepositories().getHashAlgWhiteList();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Obtener certificados dentro del repositorio
     * 
     * @return Retorna los certificados dentro de la zona publica del repositorio
     */
    public List<X509Certificate> getAllCertificates() {
        return repository.getAllCertificates();
    }
    
     /**
     * Obtener Certificado.
     *
     * @return Retorna el certificado cargado desde el repositorio.
     */
    public X509Certificate getSignCertificate() {
        return repository.getSignCertificate();
    }
    
    /**
     * Obtener Llave Privada.
     *
     * @return Retorna la llave privada cargada desde el repositorio.
     */
    public PrivateKey getPrivateKey() {
        return repository.getPrivateKey();
    }
    
    /**
     * Obtener configuración del repositorio.
     *
     * @return Retorna la configuracion del repositorio inicializado.
     */
    public AbstractRepositoryConfiguration getRepositoryConfiguration() {
        return repository.getConfiguration();
    }

    public String getRepositoryType(){
        return repository.getRepositoryType();
    }
    
    public Provider getRepositoryCryptographyProvider() {
        return repository.getRepositoryCryptographyProvider();
    }
    
    public void close() {
        repository = null;
        System.gc();
    }
}
