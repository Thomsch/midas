/* ============================================================================
 * Nom du fichier   : AcCreateOnTheFlyBudget.java
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
import gui.views.JCreateOnTheFlyBudget;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.Core;
import core.components.BudgetOnTheFly;

/**
 * TODO
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class AcCreateOnTheFlyBudget extends UserAction {

   private BudgetOnTheFly budget;
   private JCreateOnTheFlyBudget view;
   
   private Controller controller;
   /**
    * @param core
    * @param dependencies
    */
   public AcCreateOnTheFlyBudget(Core core, Controller controller) {
      super(core);
      this.controller = controller;
   }

   /* (non-Javadoc)
    * @see gui.UserAction#execute(core.Core, java.awt.event.ActionEvent, java.lang.Object[])
    */
   @Override
   protected void execute(Core core, ActionEvent event, Object[] dependencies) {
      budget = core.createBudgetOnTheFly();
      
      view = new JCreateOnTheFlyBudget((Component)event.getSource(), controller, budget);
      
      view.addValidateListener(new UserAction(core) {
         @Override
         protected void execute(Core core, ActionEvent event, Object[] dependencies) {
            core.saveBudgetOnTheFly(budget);
            view.dispose();
         }
      });
      
      view.addCancelListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            view.dispose();
         }
      });
      
      budget.addObserver(view);
      
      // ATTENTION  : le réglage de la modalité doit être fait après la paramétrisation de la fenêtre !
      view.setModalityType(ModalityType.APPLICATION_MODAL);
      view.setVisible(true);
   }
   
   public BudgetOnTheFly getCreatedBudget() {
      return budget;
   }

}
