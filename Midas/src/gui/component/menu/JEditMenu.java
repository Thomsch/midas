/* ============================================================================
 * Nom du fichier   : JEditMenu.java
 * ============================================================================
 * Date de création : 4 mai 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.component.menu;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import settings.Language.Text;

/**
 * Représente le menu "Edition" de l'application.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class JEditMenu extends JMenu {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = -8256836741751163343L;

   private JMenuItem mniEditManageCategory;
   private JMenuItem mniEditManageUser;
   private JMenuItem mniEditAccount;
   private JMenuItem mniEditBudget;
   private JMenuItem mniEditOnTheFlyBudget;
   private JMenuItem mniEditNewExpense;
   private JMenuItem mniEditNewTransaction;

   /**
    * Crée le menu "Edition".
    */
   public JEditMenu() {
      setText(Text.EDIT_MENU.toString());

      initContent();
      buildContent();
   }

   /**
    * Construit le menu.
    */
   private void buildContent() {
      // Sous menu Edition->Comptes.
      add(mniEditAccount);

      // Sous menu Edition->Budgets.
      add(mniEditBudget);

      // Option Edition->Budget à la volée.
      add(mniEditOnTheFlyBudget);

      // Séparateur Edition->Separateur1.
      JSeparator sepEditOne = new JSeparator();
      add(sepEditOne);

      // Option Edition->Gérer les catégories.
      add(mniEditManageCategory);

      // Option Edition->Gérer les auteurs.
      add(mniEditManageUser);

      // Séparateur Edition->Separateur2.
      JSeparator sepEditTwo = new JSeparator();
      add(sepEditTwo);

      // Option Edition->Dépenser.
      add(mniEditNewExpense);

      // Option Edition->Transaction.
      add(mniEditNewTransaction);
   }

   /**
    * Initialise les composants du menu d'édition.
    */
   private void initContent() {
      mniEditAccount = new JMenuItem(Text.ACTION_MANAGE_ACCOUNT.toString());
      mniEditBudget = new JMenuItem(Text.ACTION_MANAGE_BUDGET.toString());
      mniEditOnTheFlyBudget = new JMenuItem(
            Text.ACTION_CREATE_ONTHEFLY_BUDGET.toString());
      mniEditManageCategory = new JMenuItem(
            Text.ACTION_MANAGE_CATEGORY.toString());
      mniEditManageUser = new JMenuItem(Text.ACTION_MANAGE_USER.toString());
      mniEditNewExpense = new JMenuItem(Text.ACTION_NEW_EXPENSE.toString());
      mniEditNewTransaction = new JMenuItem(
            Text.ACTION_NEW_TRANSACTION.toString());
   }

   /**
    * Ajoute un écouteur sur l'action de gestion des catégories.
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addManageCategoryListener(ActionListener listener) {
      mniEditManageCategory.addActionListener(listener);
   }

   /**
    * Ajoute un écouteur sur l'action de gestion des utilisateurs.
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addManageUserListener(ActionListener listener) {
      mniEditManageUser.addActionListener(listener);
   }

   /**
    * Ajoute un écouteur sur l'action de gestion des comptes.
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addManageAccountListener(ActionListener listener) {
      mniEditAccount.addActionListener(listener);
   }

   /**
    * Ajoute un écouteur sur l'action de gestion des budgets.
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addManageBudgetListener(ActionListener listener) {
      mniEditBudget.addActionListener(listener);
   }

   /**
    * Ajoute un écouteur sur l'action de gestion des budgets à la volée.
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addCreateOnTheFlyBudgetListener(ActionListener listener) {
      mniEditOnTheFlyBudget.addActionListener(listener);
   }

   /**
    * Ajoute un écouteur sur l'action d'ajout d'une nouvelle dépense.
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addNewExpenseListener(ActionListener listener) {
      mniEditNewExpense.addActionListener(listener);
   }

   /**
    * Ajoute un écouteur sur l'action d'ajout d'une nouvelle transaction
    * 
    * @param listener
    *           - l'écouteur à ajouter.
    */
   public void addNewTransactionListener(ActionListener listener) {
      mniEditNewTransaction.addActionListener(listener);
   }
}
