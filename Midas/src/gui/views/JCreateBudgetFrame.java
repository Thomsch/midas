/* ============================================================================
 * Nom du fichier   : JCreateBudgetFrame.java
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
package gui.views;

import gui.View;
import gui.component.JDateInput;
import gui.component.JInfoEditionLabel;
import gui.component.JMoneyInfoEditionLabel;
import gui.component.JTimeSliceChooser;
import gui.component.JValidateCancel;
import gui.controller.combobox.ComboBoxAccount;
import gui.exception.BadDateException;
import gui.utils.StandardInsets;
import gui.utils.TextChangedListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Observable;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import settings.Language.Text;
import utils.TimeSlice;
import core.Core;
import core.MidasLogs;
import core.components.Budget;
import core.exceptions.NegativeLimitException;

/**
 * Fenêtre de création d'un budget.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class JCreateBudgetFrame extends JDialog implements View {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = -2619002208500615656L;

   private JInfoEditionLabel ielName;
   private JMoneyInfoEditionLabel mielAmount;
   private JValidateCancel vclActions;
   private ComboBoxAccount accounts;
   private JDateInput ditDate;
   private JTimeSliceChooser tscBudgetLength;
   private JInfoEditionLabel ielDescription;

   private Core core;
   private Budget budget;

   /**
    * Crée la fenêtre d'ajout de budget.
    * 
    * @param core
    *           - le coeur logique du programme.
    * @param budget
    *           - le nouveau budget à remplir.
    */
   public JCreateBudgetFrame(Core core, Budget budget) {
      this.core = core;
      this.budget = budget;

      initContent();
      initListeners();
      setContentPane(buildContent());
      pack();
   }

   /**
    * Initialise les listeners à usage interne de l'interface.
    */
   private void initListeners() {
      ielName.addTextChangedListener(new TextChangedListener() {

         @Override
         public void textChanged(DocumentEvent event) {
            budget.setName(ielName.getText());
            checkItemIntegrity();
         }
      });

      ielDescription.addTextChangedListener(new TextChangedListener() {

         @Override
         public void textChanged(DocumentEvent event) {
            budget.setDescription(ielDescription.getText());
         }
      });

      mielAmount.addTextChangedListener(new TextChangedListener() {

         @Override
         public void textChanged(DocumentEvent event) {
            try {
               budget.setLimit(Double.valueOf(mielAmount.getText()));
               mielAmount.setValid();
            }
            catch (NumberFormatException e) {
               MidasLogs.errors.push(e.getMessage());
               mielAmount.setInvalid();
            }
            catch (NegativeLimitException e) {
               mielAmount.setInvalid();
               MidasLogs.errors.push(e.getMessage());
            }
            checkItemIntegrity();
         }
      });

      accounts.addSelectedChangedListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            if (accounts.isValidItemSelected()) {
               budget.setBindedAccount(accounts.getSelectedItem());
            }
            checkItemIntegrity();
         }
      });
   }

   /**
    * Construit le contenu de la fenêtre.
    * 
    * @return Le contenu de la fenêtre.
    */
   private JPanel buildContent() {
      JPanel pnlContent = new JPanel();
      pnlContent.setLayout(new GridBagLayout());

      GridBagConstraints constraints = new GridBagConstraints();
      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.anchor = GridBagConstraints.WEST;

      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.weightx = 0.5;
      constraints.weighty = 0.5;
      constraints.insets = new StandardInsets();
      pnlContent.add(ielName, constraints);

      constraints.gridy = 1;
      pnlContent.add(mielAmount, constraints);

      constraints.gridy = 2;
      pnlContent.add(accounts.getGraphicalComponent(), constraints);

      constraints.gridy = 3;
      pnlContent.add(tscBudgetLength, constraints);

      constraints.gridy = 4;
      pnlContent.add(ditDate, constraints);

      constraints.gridy = 5;
      pnlContent.add(ielDescription, constraints);

      constraints.gridy = 6;
      constraints.fill = GridBagConstraints.NONE;
      constraints.anchor = GridBagConstraints.EAST;
      pnlContent.add(vclActions, constraints);
      return pnlContent;
   }

   /**
    * Initialise les composants de la fenêtre.
    */
   private void initContent() {
      ielName = new JInfoEditionLabel(Text.BUDGET_NAME_LABEL);
      mielAmount = new JMoneyInfoEditionLabel(Text.AMOUNT_LABEL);
      accounts = new ComboBoxAccount(core);

      ditDate = new JDateInput(Text.DATE_LABEL);

      tscBudgetLength = new JTimeSliceChooser(TimeSlice.MONTHLY,
            TimeSlice.ANNUAL);

      ielDescription = new JInfoEditionLabel(Text.BUDGET_DESCRIPTION_LABEL);

      vclActions = new JValidateCancel();
   }

   /**
    * Ajoute un écouteur sur le bouton de validation.
    * 
    * @param listener
    *           - l'écouteur ajouté.
    */
   public void addValidateListener(ActionListener listener) {
      vclActions.addValidateListener(listener);
   }

   /**
    * Ajoute un écouteur sur le bouton d'annulation.
    * 
    * @param listener
    *           - l'écouteur ajouté.
    */
   public void addCancelListener(ActionListener actionListener) {
      vclActions.addCancelListener(actionListener);
   }

   /**
    * Renvoie la date sélectionnée.
    * 
    * @return La date sélectionnée.
    * @throws BadDateException
    *            si la date est invalide.
    */
   public Date getDate() throws BadDateException {
      return ditDate.getDate();
   }

   /**
    * Renvoie l'intervalle de temps choisi par l'utilisateur.
    * 
    * @return L'intervalle de temps choisi par l'utilisateur.
    */
   public TimeSlice getTimeSlice() {
      return tscBudgetLength.getTimeSlice();
   }

   /**
    * Vérifie que l'objet complété par l'utilisateur peut être sauvegardé dans
    * la base de données.
    */
   public void checkItemIntegrity() {
      boolean result;
      result = ielName.isValidData()
            && accounts.isValidItemSelected()
            && mielAmount.isPositive();
      vclActions.setEnableValidateButton(result);
   }

   @Override
   public void update(Observable arg0, Object arg1) {
      // Pas d'update pour l'instant. Voir rapport.
   }
}
