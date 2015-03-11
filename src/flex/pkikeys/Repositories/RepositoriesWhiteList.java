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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * ListaBlancaRepositorios
 * 
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 * @version 1.0
 */
public final class RepositoriesWhiteList extends ArrayList<String> {
    private String hashAlgWhiteList = null;
    
    public RepositoriesWhiteList(String hashAlgWhiteList) {
        setHashAlgWhiteList(hashAlgWhiteList);
    }
            
    public RepositoriesWhiteList(InputStream reader) throws PKIKeysException {
        try {
            List<String> lines = linesList(reader);
            //Cada vez que lee una lista nueva se reinicia los valores
            setHashAlgWhiteList(null);
            
            //Se llena la lista de nuevo
            for(String line : lines) {    
                //Si no es un comentario ni está vacía
                if( ( !line.trim().startsWith("#") ) && ( !line.trim().isEmpty() ) ){
                    //La primera línea debe indicar el algoritmo de hash con el
                    //fueron generados los hash de los drivers
                    if(hashAlgWhiteList == null) {
                        setHashAlgWhiteList(line.trim());
                    } else {
                        add(line.trim());
                    }
                }
            }
            
            if(getHashAlgWhiteList() == null )
                throw new PKIKeysException(new NoSuchAlgorithmException());
            
        } catch (IOException ex) {
            throw new PKIKeysException(PKIKeysException.ERROR_WHITELIST_LOAD);
        }
    }

    public String getHashAlgWhiteList() {
        return hashAlgWhiteList;
    }

    public void setHashAlgWhiteList(String hashAlgWhiteList) {
        this.hashAlgWhiteList = hashAlgWhiteList;
        clear();
    }
    
    private List<String> linesList(InputStream inStream) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader buffer;
        buffer = new BufferedReader(new InputStreamReader(inStream));

        while(buffer.ready()) lines.add(buffer.readLine());

        return lines;
    }
}
