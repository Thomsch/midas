/* ============================================================================
 * Nom du fichier   : AcManageAccount.java
 * ============================================================================
 * Date de création : 7 mai 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.actions;

import gui.UserAction;
import gui.utils.Positions;
import gui.utils.Positions.ScreenPosition;
import gui.views.JManageAccount;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import settings.Language.Text;
import core.Core;

/**
 * Action / contrôleur pour l'ajout, modification et consultation des comptes.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class AcManageAccount extends UserAction {

   private JManageAccount view;

   /**
    * Crée une nouvelle action qui va gérer la modification des comptes.
    * 
    * @param core
    *           - le coeur logique du programme.
    */
   public AcManageAccount(Core core) {
      super(core);
   }

   @Override
   protected void execute(Core core, ActionEvent event, Object[] dependencies) {
      view = new JManageAccount(core);
      view.setTitle(Text.APP_TITLE.toString() + " - "
            + Text.ACCOUNT_MANAGEMENT_TITLE);
      view.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      Positions.setPositionOnScreen(view, ScreenPosition.CENTER);

      initListeners(core);

      // ATTENTION : le réglage de la modalité doit être fait après la
      // paramétrisation de la fenêtre !
      view.setModalityType(ModalityType.APPLICATION_MODAL);
      view.setVisible(true);
   }

   /**
    * Initialise les écouteurs de l'action.
    * 
    * @param core
    *           - le coeur logique du programme.
    */
   private void initListeners(final Core core) {
      view.addButtonAddListener(new AcCreateAccount(core));

      view.addButtonModifyListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            view.saveItem();
            core.saveAccount(view.getSelectedValue());
         }
      });

      view.addButtonDeleteListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            core.deactivateAccount(view.getSelectedValue());
            view.selectNoItem();
            view.updateModel();
         }
      });
   }
}
