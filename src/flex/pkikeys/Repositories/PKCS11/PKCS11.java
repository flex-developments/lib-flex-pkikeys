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

import flex.helpers.SystemHelper;
import flex.helpers.exceptions.SystemHelperException;
import flex.pkikeys.PKIKeysLogger;
import flex.pkikeys.Repositories.AbstractRepository;
import flex.pkikeys.exceptions.ChangeRepositoryTypeException;
import flex.pkikeys.exceptions.PKIKeysException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.ProviderException;
import java.security.Security;
import javax.smartcardio.Card;

/**
 * PKCS11
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments en gmail
 * @author Ing. Yessica De Ascencao - yessicadeascencao en gmail
 */
public class PKCS11 extends AbstractRepository {
    private Card card;
    private KeyStore.Builder ksBuilder = null;
    
    public PKCS11() {
        this(new PKCS11Configuration());
    }
    
    public PKCS11(PKCS11Configuration configuracion) {
        setConfiguration(configuracion);
        setRepositoryType(this.getClass().getSimpleName());
    }
    
    @Override
    public PKCS11Configuration getConfiguration() {
        return (PKCS11Configuration)configuration;
    };
    
    @Override
    public void loadFromConsole() throws PKIKeysException, ChangeRepositoryTypeException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void loadFromGUI() throws PKIKeysException, ChangeRepositoryTypeException {
        PKCS11GUI deviceWin = new PKCS11GUI(getConfiguration().getName(), getConfiguration().getPath());
        deviceWin.getConfiguration();
        getConfiguration().setName(deviceWin.getName());
        getConfiguration().setPath(deviceWin.getLibrary());
        loadDriver();
        PKCS11GUIPass P11Win = new PKCS11GUIPass();
        ksBuilder = KeyStore.Builder.newInstance(getRepositoryType(), cryptographyProvider, new KeyStore.CallbackHandlerProtection(P11Win));
        
//        //OJO... Revisar implementación para bloqueo de recurso en Windows
//        if(SistemaUtil.isWindows()){
//            card.beginExclusive();
//            ksBuilder = KeyStore.Builder.newInstance(getRepositorioType(), pkcs11Provider, new KeyStore.CallbackHandlerProtection(P11Win));
//            card.endExclusive();
//
//        } else if(SistemaUtil.isLinux()){
//            ksBuilder = KeyStore.Builder.newInstance(getRepositorioType(), pkcs11Provider, new KeyStore.CallbackHandlerProtection(P11Win));
//            }
        
    }
    
    private void loadDriver() throws PKIKeysException {
        try {
            verifyRepositoryIntegrity();	
            
            String smathCardConf = "name=" + getConfiguration().getName() 
                                 + "\nlibrary=" + getConfiguration().getPath()
                                 + "\nslot=1"
//                                 + "\nattributes(*,*,*) = {"
//                                    + "\nCKA_TOKEN = true"
//                                    + "\nCKA_EXTRACTABLE = true"
//                                    + "\nCKA_SENSITIVE = true"
//                                 + "\n}"
                                 + "\ndisabledMechanisms = {"
                                    + "\nCKM_SHA1_RSA_PKCS"
                                 + "\n}";
            
            InputStream confStream = new ByteArrayInputStream(smathCardConf.getBytes());
            
            setRepositoryCryptographyProvider(new sun.security.pkcs11.SunPKCS11(confStream));
            
            if (Security.getProvider(cryptographyProvider.getName()) != null)
                Security.removeProvider(cryptographyProvider.getName());
            
            Security.addProvider(cryptographyProvider);
            
        } catch (ProviderException ex) {
            PKIKeysLogger.writeErrorLog(ex);
            if(ex.getCause().toString().startsWith("sun.security.pkcs11.ConfigurationException"))
                throw new PKIKeysException(PKCS11Exception.ERROR_PKCS11_PARSING_CONFIGURATION);
            
        } catch (Exception ex) {
            PKIKeysLogger.writeErrorLog(ex);
            throw new PKIKeysException(ex);
        }
    }

    @Override
    public void loadKeys(String alias) throws PKIKeysException {
        try {
            KeyStore ks;
            if(SystemHelper.isWindows()){
//                String type = ((String)AccessController.doPrivileged(
//                    new GetPropertyAction(
//                        "javax.smartcardio.TerminalFactory.DefaultType", 
//                        "PC/SC"))
//                ).trim();
//                TerminalFactory term = TerminalFactory.getInstance(
//                    type, 
//                    null, 
//                    getRepositoryCryptographyProvider()
//                );
//                Card smartcard = ((CardTerminal)term.terminals().list().get(0)).connect("*");
                ks = ksBuilder.getKeyStore();
//                smartcard.beginExclusive();
                importKeys(ks, null, alias);
//                smartcard.endExclusive();

            } else if(SystemHelper.isLinux()){
                ks = ksBuilder.getKeyStore();
                
                importKeys(ks, null, alias);
            }
            
        } catch (SystemHelperException ex) {
            PKIKeysLogger.writeErrorLog(ex);
            throw new PKIKeysException(ex);
            
        } catch (KeyStoreException | SecurityException ex) {
            PKIKeysLogger.writeErrorLog(ex);
            throw new PKIKeysException(ex);
        } //catch (NoSuchAlgorithmException | CardException) {
    }
}
