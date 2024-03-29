/* ============================================================================
 * Nom du fichier   : InconsistencyDate.java
 * ============================================================================
 * Date de création : 7 juin 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.alert;

import javax.swing.JOptionPane;

import settings.Language.Text;
import core.MidasLogs;
import core.exceptions.InconsistencyDateException;

/**
 * Affiche un message d'alerte à l'utilisateur s'il ne choisit pas un couple de
 * dates correctes.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class InconsistencyDate {
   private final String message = Text.INCONSISTENCY_DATE_MESSAGE.toString();

   /**
    * Affiche le message d'information à l'utilisateur.
    * 
    * @param e
    *           - l'exception qui a déclenché l'alerte.
    */
   public InconsistencyDate(InconsistencyDateException e) {
      MidasLogs.errors.push(e.getMessage());
      JOptionPane.showMessageDialog(null, message, Text.APP_TITLE.toString(),
            JOptionPane.ERROR_MESSAGE);
   }
}
