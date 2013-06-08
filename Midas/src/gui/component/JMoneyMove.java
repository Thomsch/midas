/* ============================================================================
 * Nom du fichier   : JMoneyMove.java
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
package gui.component;

import gui.controller.listbox.FinancialTransactionListBox;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import settings.Language.Text;
import core.Core;

/**
 * Panel d'affichage des mouvements d'argent.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class JMoneyMove extends JPanel {
   
   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = 1880549632117034840L;

   private JLabel lblDescription;
   private FinancialTransactionListBox lastTransactions;
   
   /**
    * Construit le panel indiquant les dernières dépense efféctuées.
    * @param core Le coeur logique de l'application.
    */
   public JMoneyMove(Core core) {
      initContent(core);
      buildContent();
   }
   
   /**
    * Initialise les composants de l'interface.
    */
   private void initContent(Core core) {
      lblDescription = new JLabel(Text.LAST_MONEY_MOVE_LABEL.toString());
      lastTransactions = new FinancialTransactionListBox(core);
   }
   
   /**
    * Construit le layout et place les composants.
    */
   private void buildContent() {
      setLayout(new BorderLayout(5,5));
      
      // Ajout des composants au panel.
      add(lblDescription, BorderLayout.NORTH);      
      add(new JScrollPaneDefault(lastTransactions.getGraphicalComponent()), BorderLayout.CENTER);
   }
}