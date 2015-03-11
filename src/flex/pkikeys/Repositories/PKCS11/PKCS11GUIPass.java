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

package flex.pkikeys.Repositories.PKCS11;

import flex.pkikeys.Repositories.PKCS11.resources.PKCS11StringsBundle;
import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * PKCS11GUIPass
 * Clase que permite capturar la contraseña de acceso a la tarjeta criptográfica
 * desde una interfáz gráfica basada en Swing.
 *
 * @author Ing. Felix D. Lopez M. - flex.developments@gmail.com
 * @author Ing. Yessica De Ascencao - yessicadeascencao@gmail.com
 */
public class PKCS11GUIPass implements CallbackHandler {
   private Component parentComponent = null;
   final JPasswordField password = new JPasswordField(8);

   public PKCS11GUIPass() {}

   /**
    * Crea una ventana de dialogo callback y especifica la ventana pariente.
    *
    * @param parentComponent la ventana pariente -- especifique <code>null</code>
    * para la ventana por defecto
    */
    public PKCS11GUIPass(Component parentComponent) {
      this.parentComponent = parentComponent;
    }
    
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback pc = (PasswordCallback) callbacks[i];
                
                JLabel prompt = new JLabel(PKCS11StringsBundle.get(PKCS11StringsBundle.I_PKCS11_L_PASS));
                if (!pc.isEchoOn()) password.setEchoChar('*');
                Box passwordPanel = Box.createHorizontalBox();
                passwordPanel.add(prompt);
                passwordPanel.add(password);
                
                InputStream imgIS = flex.pkikeys.Repositories.AbstractRepository.class.getResourceAsStream("llaves.png");
                ImageIcon image = new ImageIcon(ImageIO.read(imgIS));
                int result = JOptionPane.showOptionDialog(
                    parentComponent,
                    passwordPanel,
                    PKCS11StringsBundle.get(PKCS11StringsBundle.I_PKCS11_TITLE),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    image,
                    null,
                    null);

                /* Perform the OK actions */
                if (result == JOptionPane.OK_OPTION) {
                    pc.setPassword(password.getPassword());
                }
                
            } else {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
        }
    }
    
    //Quizas algún día haga falta
//   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
//      /* Collect messages to display in the dialog */
//      final List<Object> messages = new ArrayList<>(3);
//
//      /* Collection actions to perform if the user clicks OK */
//      final List<Object> okActions = new ArrayList<>(2);
//
//      ConfirmationInfo confirmation = new ConfirmationInfo();
//
//      for (int i = 0; i < callbacks.length; i++) {
//         if (callbacks[i] instanceof TextOutputCallback) {
//            TextOutputCallback tc = (TextOutputCallback) callbacks[i];
//
//            switch (tc.getMessageType()) {
//               case TextOutputCallback.INFORMATION:
//                  confirmation.messageType = JOptionPane.INFORMATION_MESSAGE;
//                  break;
//                   
//               case TextOutputCallback.WARNING:
//                  confirmation.messageType = JOptionPane.WARNING_MESSAGE;
//                  break;
//                   
//               case TextOutputCallback.ERROR:
//                  confirmation.messageType = JOptionPane.ERROR_MESSAGE;
//                  break;
//                   
//               default:
//                  throw new UnsupportedCallbackException(
//                          callbacks[i], "Se desconoce el tipo de mensaje");
//            }
//
//            messages.add(tc.getMessage());
//
//         } else if (callbacks[i] instanceof NameCallback) {
//            final NameCallback nc = (NameCallback) callbacks[i];
//
//            JLabel prompt = new JLabel(nc.getPrompt());
//
//            final JTextField name = new JTextField(JTextFieldLen);
//            String defaultName = nc.getDefaultName();
//
//            if (defaultName != null) {
//               name.setText(defaultName);
//            }
//
//            /*
//             * Put the prompt and name in a horizontal box,
//             * and add that to the set of messages.
//             */
//            Box namePanel = Box.createHorizontalBox();
//            namePanel.add(prompt);
//            namePanel.add(name);
//
//
//            messages.add(namePanel);
//
//            /* Store the name back into the callback if OK */
//            okActions.add(new Action() {
//
//               @Override
//               public void perform() {
//                  nc.setName(name.getText());
//               }
//            });
//
//         } else if (callbacks[i] instanceof PasswordCallback) {
//            final PasswordCallback pc = (PasswordCallback) callbacks[i];
//            
//            JLabel prompt = new JLabel("Por favor introduzca el Pin de la Tarjeta: ");
//            
//            if (!pc.isEchoOn()) {
//               password.setEchoChar('*');
//            }
//            
//            JLabel top1TextField = new JLabel();
//            top1TextField.setText("Obtener repositorio PKCS#11");
//
//            Box passwordPanel = Box.createHorizontalBox();
//            passwordPanel.add(prompt);
//            passwordPanel.add(password);
//
//            messages.add(top1TextField);
//            messages.add(passwordPanel);
//
//            okActions.add(new Action() {
//
//               @Override
//               public void perform() {
//                  pc.setPassword(password.getPassword());
//               }
//            });
//
//         } else if (callbacks[i] instanceof ConfirmationCallback) {
//            ConfirmationCallback cc = (ConfirmationCallback) callbacks[i];
//
//            confirmation.setCallback(cc);
//            if (cc.getPrompt() != null) {
//               messages.add(cc.getPrompt());
//            }
//
//         } else {
//            throw new UnsupportedCallbackException(
//                    callbacks[i], "Unrecognized Callback");
//         }
//      }
//      
//      java.net.URL rutaImg = flex.pkikeys.Repositorios.Repositorio.class.getResource("llaves.png");
//      ImageIcon imagen = new ImageIcon(rutaImg);
//
//      /* Display the dialog */
//      int allCertificates = JOptionPane.showOptionDialog(
//              parentComponent,
//              messages.toArray(),
//              "Firma Electrónica", /* title */
//              confirmation.optionType,
//              confirmation.messageType,
//              //null, /* icon */
//              imagen, /* icon */
//              confirmation.options, /* options */
//              confirmation.initialValue);		/* initialValue */
//
//      /* Perform the OK actions */
//      if (allCertificates == JOptionPane.OK_OPTION || allCertificates == JOptionPane.YES_OPTION) {
//         Iterator iterator = okActions.iterator();
//         while (iterator.hasNext()) {
//            ((Action) iterator.next()).perform();
//         }
//      }
//      confirmation.handleResult(allCertificates);
//        try {
//            this.finalize();
//        } catch (Throwable ex) {
//        }
//   }
//
//   /*
//    * Provee asistencia cuando se realiza la traduccion entre JAAS y la ventana
//    * de confirmacion en Swing.
//    */
//   private static class ConfirmationInfo {
//
//      private int[] translations;
//      int optionType = JOptionPane.OK_CANCEL_OPTION;
//      Object[] options = null;
//      Object initialValue = null;
//      int messageType = JOptionPane.QUESTION_MESSAGE;
//      private ConfirmationCallback callback;
//
//      /* Set the confirmation callback handler */
//      void setCallback(ConfirmationCallback callback)
//              throws UnsupportedCallbackException {
//         this.callback = callback;
//
//         int confirmationOptionType = callback.getOptionType();
//         switch (confirmationOptionType) {
//            case ConfirmationCallback.YES_NO_OPTION:
//               optionType = JOptionPane.YES_NO_OPTION;
//               translations = new int[]{
//                  JOptionPane.YES_OPTION, ConfirmationCallback.YES,
//                  JOptionPane.NO_OPTION, ConfirmationCallback.NO,
//                  JOptionPane.CLOSED_OPTION, ConfirmationCallback.NO
//               };
//               break;
//            case ConfirmationCallback.YES_NO_CANCEL_OPTION:
//               optionType = JOptionPane.YES_NO_CANCEL_OPTION;
//               translations = new int[]{
//                  JOptionPane.YES_OPTION, ConfirmationCallback.YES,
//                  JOptionPane.NO_OPTION, ConfirmationCallback.NO,
//                  JOptionPane.CANCEL_OPTION, ConfirmationCallback.CANCEL,
//                  JOptionPane.CLOSED_OPTION, ConfirmationCallback.CANCEL
//               };
//               break;
//            case ConfirmationCallback.OK_CANCEL_OPTION:
//               optionType = JOptionPane.OK_CANCEL_OPTION;
//               translations = new int[]{
//                  JOptionPane.OK_OPTION, ConfirmationCallback.OK,
//                  JOptionPane.CANCEL_OPTION, ConfirmationCallback.CANCEL,
//                  JOptionPane.CLOSED_OPTION, ConfirmationCallback.CANCEL
//               };
//               break;
//            case ConfirmationCallback.UNSPECIFIED_OPTION:
//               options = callback.getOptions();
//               /*
//                * There's no way to know if the default option means
//                * to cancel the login, but there isn't a better way
//                * to guess this.
//                */
//               translations = new int[]{
//                  JOptionPane.CLOSED_OPTION, callback.getDefaultOption()
//               };
//               break;
//            default:
//               throw new UnsupportedCallbackException(
//                       callback,
//                       "Unrecognized option type: " + confirmationOptionType);
//         }
//
//         int confirmationMessageType = callback.getMessageType();
//         switch (confirmationMessageType) {
//            case ConfirmationCallback.WARNING:
//               messageType = JOptionPane.WARNING_MESSAGE;
//               break;
//            case ConfirmationCallback.ERROR:
//               messageType = JOptionPane.ERROR_MESSAGE;
//               break;
//            case ConfirmationCallback.INFORMATION:
//               messageType = JOptionPane.INFORMATION_MESSAGE;
//               break;
//            default:
//               throw new UnsupportedCallbackException(
//                       callback,
//                       "Unrecognized message type: " + confirmationMessageType);
//         }
//      }
//
//      /**
//       * Procesa el resultado retornado por la ventana de dialogo en Swing
//       *
//       * @param allCertificates
//       */
//      void handleResult(int allCertificates) {
//         if (callback == null) {
//            return;
//         }
//
//         for (int i = 0; i < translations.length; i += 2) {
//            if (translations[i] == allCertificates) {
//               allCertificates = translations[i + 1];
//               break;
//            }
//         }
//         callback.setSelectedIndex(allCertificates);
//      }
//   }
}
