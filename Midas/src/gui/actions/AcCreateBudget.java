/* ============================================================================
 * Nom du fichier   : AcCreateBudget.java
 * ============================================================================
 * Date de création : 9 mai 2013
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
import gui.alert.BadDate;
import gui.alert.BadTimeSlice;
import gui.alert.InconsistencyDate;
import gui.exception.BadDateException;
import gui.exception.BadTimeSliceException;
import gui.utils.Positions;
import gui.utils.Positions.ScreenPosition;
import gui.views.JCreateBudgetFrame;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import settings.Language.Text;
import utils.TimeSlice;
import core.Core;
import core.components.Budget;
import core.components.Recurrence;
import core.exceptions.InconsistencyDateException;

/**
 * Contrôleur et action d'ajout d'un budget.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class AcCreateBudget extends UserAction {

   private Budget budget;
   private Recurrence recurrence;
   private JCreateBudgetFrame view;

   /**
    * Crée l'action de création de budget.
    * 
    * @param core
    *           - le coeur logique de l'application.
    */
   public AcCreateBudget(Core core) {
      super(core);
   }

   @Override
   protected void execute(Core core, ActionEvent event, Object[] dependencies) {
      // Initialisation de la récurrence du budget.
      recurrence = core.createReccurence();
      budget = core.createBudget();

      view = new JCreateBudgetFrame(core, budget);
      view.setTitle(Text.APP_TITLE.toString() + " - "
            + Text.BUDGET_CREATION_TITLE);
      Positions.setPositionOnScreen(view, ScreenPosition.CENTER);

      initListeners(core);

      budget.addObserver(view);

      // ATTENTION : le réglage de la modalité doit être fait après la
      // paramétrisation de la fenêtre !
      view.setModalityType(ModalityType.APPLICATION_MODAL);
      view.setVisible(true);
   }

   /**
    * Retourne le budget produit par cette action.
    * 
    * @return Le budget produit.
    */
   public Budget getCreatedBudget() {
      return budget;
   }

   /**
    * Initialise les écouteurs de l'action.
    * 
    * @param core
    *           - le coeur logique de l'application.
    */
   private void initListeners(Core core) {
      view.addValidateListener(new UserAction(core) {
         @Override
         protected void execute(Core core, ActionEvent event,
               Object[] dependencies) {
            try {
               // Récupération du début et de la fin de la récurrence choisie.
               Date[] result = TimeSlice.getFirstAndLastDay(
                     view.getTimeSlice(), view.getDate());
               // Ici l'intervalle de récurrence est toujours à 0 car on ne fait
               // pas de récurrence dans cette version-ci du logiciel.
               recurrence.setIntervalRecurrence(0);
               recurrence.setStartDate(result[0]);
               recurrence.setEndDate(result[1]);

               core.saveRecurrence(recurrence);
               budget.setRecurrence(recurrence);
               core.saveBudget(budget);
               view.dispose();
            }
            catch (BadTimeSliceException e) {
               new BadTimeSlice(e);
            }
            catch (BadDateException e) {
               new BadDate(e);
            }
            catch (InconsistencyDateException e) {
               new InconsistencyDate(e);
            }
         }
      });

      view.addCancelListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            view.dispose();
         }
      });
   }

}
