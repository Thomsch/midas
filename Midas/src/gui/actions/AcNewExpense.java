/* ============================================================================
 * Nom du fichier   : AcNewExpense.java
 * ============================================================================
 * Date de création : 10 mai 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.actions;

import gui.Controller;
import gui.UserAction;
import gui.utils.Positions;
import gui.utils.Positions.ScreenPosition;
import gui.views.JNewExpense;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import settings.Language.Text;
import core.Core;
import core.components.FinancialTransaction;

/**
 * Contrôleur et action de l'ajout d'une dépense.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class AcNewExpense extends UserAction {
   
   private FinancialTransaction expense;
   private Controller controller;
   private JNewExpense view;

   /**
    * @param core
    * @param dependencies
    */
   public AcNewExpense(Core core, Controller controller, Object[] dependencies) {
      super(core, dependencies);
      this.controller = controller;
   }

   /* (non-Javadoc)
    * @see gui.UserAction#execute(core.Core, java.awt.event.ActionEvent, java.lang.Object[])
    */
   @Override
   protected void execute(Core core, ActionEvent event, Object[] dependencies) {
      expense = core.createFinancialTransaction();
      
      // Réglages de la fenêtre.
      view = new JNewExpense(controller, expense);
      view.setTitle(Text.APP_TITLE.toString() + " - " + Text.EXPENSE_CREATION_TITLE.toString());
      Positions.setPositionOnScreen(view, ScreenPosition.CENTER);
      view.setResizable(false);
      
      initListeners(core);
   
      expense.addObserver(view);
      view.setModalityType(ModalityType.APPLICATION_MODAL);
      view.setVisible(true);
   }
   
   /**
    * Initialise les listeners de cette action.
    * @param core Coeur logique de l'application.
    */
   private void initListeners(Core core) {
      view.addValidateListener(new UserAction(core) {
         @Override
         protected void execute(Core core, ActionEvent event, Object[] dependencies) {
            expense.setDate(view.getDate());
            expense.setAccount(expense.getBudget().getBindedAccount());
            core.saveFinancialTransaction(expense);
            view.dispose();
         }
      });
      
      view.addCancelListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent arg0) {
            view.dispose();
         }
      });
   }
}
