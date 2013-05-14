/* ============================================================================
 * Nom du fichier   : JNewExpense.java
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
package gui.views;

import gui.Controller;
import gui.View;
import gui.component.JComboBoxBudget;
import gui.component.JComboBoxCategory;
import gui.component.JDateInput;
import gui.component.JLabelMoneyPanel;
import gui.component.JLabelTextPanel;
import gui.component.JRecursionChooser;
import gui.component.JValidateCancel;
import gui.controller.ComboBoxUser;
import gui.controller.ComboBoxesCategory;
import gui.utils.StandardInsets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JPanel;

import settings.Language.Text;

/**
 * TODO
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class JNewExpense extends javax.swing.JDialog implements View {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = 9007162125947939904L;
   
   private Controller controller;
   
   //Composants
   private JComboBoxBudget cbbBudgets;
   private ComboBoxesCategory categories;
   private ComboBoxUser users;
   private JLabelTextPanel ltpReason;
   private JLabelMoneyPanel lmpAmount;
   private JDateInput ditDate;

   private JRecursionChooser rcrRecurrenceOptions;
   
   private JValidateCancel vclActions;
   
   
   public JNewExpense(Controller controller) {
      this.controller = controller;
      setContentPane(buildContent());
      pack();
   }
   
   private JPanel buildContent() {
      JPanel pnlContent = new JPanel();
      pnlContent.setLayout(new GridBagLayout());
      
      initContent();
      
      GridBagConstraints constraints = new GridBagConstraints();
      
      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.anchor = GridBagConstraints.WEST;
      constraints.insets = new StandardInsets();
      constraints.weightx = 0.5;
      constraints.weighty = 0.5;
      
      constraints.gridx = 0;
      constraints.gridy = 0;
      pnlContent.add(cbbBudgets, constraints);
      
      constraints.gridy = 1;
      pnlContent.add(categories.getGraphicalComponent(), constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      pnlContent.add(users.getGraphicalComponent(), constraints);
      
      constraints.gridy = 3;
      pnlContent.add(ltpReason, constraints);
      
      constraints.gridy = 4;
      pnlContent.add(lmpAmount, constraints);
      
      constraints.gridy = 5;
      pnlContent.add(ditDate, constraints);
      
      constraints.gridy = 6;
      pnlContent.add(rcrRecurrenceOptions, constraints);
      
      constraints.gridy = 7;
      constraints.anchor = GridBagConstraints.EAST;
      constraints.fill = GridBagConstraints.NONE;
      pnlContent.add(vclActions, constraints);
      
      return pnlContent;
   }

   private void initContent() {
      cbbBudgets = new JComboBoxBudget();
      categories = new ComboBoxesCategory(controller.getCore());
      users = new ComboBoxUser(controller.getCore());
      ltpReason = new JLabelTextPanel(Text.REASON_LABEL.toString());
      lmpAmount = new JLabelMoneyPanel(Text.AMOUNT_LABEL.toString());
      ditDate = new JDateInput(Text.DATE_LABEL.toString());
      
      rcrRecurrenceOptions = new JRecursionChooser();
      
      vclActions = new JValidateCancel();
   }
   /* (non-Javadoc)
    * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
    */
   @Override
   public void update(Observable arg0, Object arg1) {
      // TODO Auto-generated method stub

   }
   
   public void addValidateListener(ActionListener listener) {
      vclActions.addValidateListener(listener);
   }
   
   public void addCancelListener(ActionListener listener) {
      vclActions.addCancelListener(listener);
   }

}
