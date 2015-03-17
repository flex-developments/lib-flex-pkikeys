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

package flex.pkikeys.test;

import flex.pkikeys.PKIKeys;
import java.security.Signature;

/**
 * PkiKeysTest
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 * @version 1.0
 */
public class PkiKeysTest {
    
    public static void main(String[] args) {
        probarFirmarString();
        
        System.out.println("End!");
        System.exit(0);
    }
    
    private static void probarFirmarString() {
        try {
            String data = "test";
            PKIKeys keys = TestsResources.getKeys(true, false, true);
            
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(keys.getPrivateKey());
            signature.update(data.getBytes());
            byte[] signBytes = signature.sign();
            System.out.println("Firma <" + new String(signBytes) + ">");
            System.out.println("Configuration utilizada <" + keys.getRepositoryConfiguration().getConfigurationEncode() + ">");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
