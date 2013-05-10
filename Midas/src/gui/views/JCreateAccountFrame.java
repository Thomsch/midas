/* ============================================================================
 * Nom du fichier   : NewAccountFrame.java
 * ============================================================================
 * Date de création : 18 avr. 2013
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
import gui.component.JLabelMoneyPanel;
import gui.component.JLabelTextPanel;
import gui.component.JValidateCancelReset;
import gui.utils.StandardInsets;
import gui.utils.TextChangedListener;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import settings.Language.Text;
import core.MidasLogs;
import core.components.Account;
/**
 * Fenêtre d'ajout de compte.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class JCreateAccountFrame extends JDialog implements View{

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = -2273955948172856823L;
   
   private Account account;
   
   private JLabelTextPanel ltpName;
   private JLabelMoneyPanel ltpThreshold;
   private JLabelMoneyPanel ltpInitialAmount;
   private JLabelTextPanel ltpNumber;
   private JLabelTextPanel ltpDescription;
   private JValidateCancelReset vcrActions;
   
   /**
    * Construit une nouvelle fenêtre pour ajouter un compte.
    */
   public JCreateAccountFrame(Component parent, Account account) {
      this.account = account;
      
      setTitle(Text.APP_TITLE.toString());
      setContentPane(buildContent());
      setLocationRelativeTo(parent);
      setResizable(false);
      pack();
      update(null, null);
   }
   
   private JPanel buildContent() {
      JPanel pnlContent = new JPanel();
      initComponent();
      
      pnlContent.setLayout(new GridBagLayout());
      GridBagConstraints constraints = new GridBagConstraints();
      
      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.gridx = 0;
      constraints.gridy = 0;
      constraints.weightx = 0.5;
      constraints.weighty = 0.5;
      constraints.insets = new StandardInsets();
      pnlContent.add(ltpName, constraints);
      
      constraints.gridy = 1;
      pnlContent.add(ltpThreshold, constraints);
      
      constraints.gridy = 2;
      pnlContent.add(ltpInitialAmount, constraints);
      
      constraints.gridy = 3;
      pnlContent.add(ltpNumber, constraints);
      
      constraints.gridy = 4;
      pnlContent.add(ltpDescription, constraints);
      
      constraints.gridy = 5;
      constraints.anchor = GridBagConstraints.EAST;
      constraints.fill = GridBagConstraints.NONE;
      pnlContent.add(vcrActions, constraints);
      
      
      // A changer c'est moche.
      ltpName.addTextChangedListener(new TextChangedListener() {
         
         @Override
         public void textChanged(DocumentEvent event) {
            account.setAccountName(ltpName.getText());            
         }
      });
      
      ltpThreshold.addTextChangedListener(new TextChangedListener() {
         
         @Override
         public void textChanged(DocumentEvent event) {
            try {
               account.setOverdraftLimit(Double.valueOf(ltpThreshold.getText()));
            } catch(NumberFormatException e) {
               MidasLogs.errors.push("NOT PARSING");
            } 
         }
      });
      
      ltpInitialAmount.addTextChangedListener(new TextChangedListener() {
         
         @Override
         public void textChanged(DocumentEvent event) {
            try {
               account.setOverdraftLimit(Double.valueOf(ltpInitialAmount.getText()));
            } catch(NumberFormatException e) {
               MidasLogs.errors.push("NOT PARSING");
            } 
         }
      });
      
      ltpNumber.addTextChangedListener(new TextChangedListener() {
         
         @Override
         public void textChanged(DocumentEvent event) {
            account.setAccountName(ltpNumber.getText());
         }
      });
      
      return pnlContent;
   }
   
   private void initComponent() {
      ltpName = new JLabelTextPanel(Text.ACCOUNT_NAME_LABEL.toString());
      ltpThreshold = new JLabelMoneyPanel(Text.ACCOUNT_THRESHOLD_LABEL.toString());
      ltpInitialAmount = new JLabelMoneyPanel(Text.ACCOUNT_INITIAL_AMOUNT_LABEL.toString());
      ltpNumber = new JLabelTextPanel(Text.ACCOUNT_NUMBER_LABEL.toString());
      ltpDescription = new JLabelTextPanel(Text.ACCOUNT_DESCRIPTION_LABEL.toString());
      vcrActions = new JValidateCancelReset();
   }
   
   public void addValidateListener(ActionListener listener) {
      vcrActions.addValidateListener(listener);
   }
   
   public void addCancelListener(ActionListener listener) {
      vcrActions.addCancelListener(listener);
   }
   
   /* (non-Javadoc)
    * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
    */
   @Override
   public void update(Observable arg0, Object arg1) {
      ltpName.setText(account.getAccountName());
      ltpThreshold.setText(String.valueOf(account.getOverdraftLimit()));
      ltpInitialAmount.setText(String.valueOf(account.getAccountBalance()));
      ltpNumber.setText(account.getAccountNumber());
   }

   /**
    * @param i
    */
   public void setDefaultThreshold(int i) {
      ltpThreshold.setText("0");
   }
}
