/* ============================================================================
 * Nom du fichier   : HomeScreen.java
 * ============================================================================
 * Date de création : 13 avr. 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.component;

import gui.Controller;
import gui.controller.QuickExpense;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * Contient les éléments du menu d'accueil.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
@SuppressWarnings("serial")
public class JHomeScreen extends JPanel {
   
   Controller controller;
   QuickExpense quickExpense;
   
   /**
    * Constructeur par défaut.
    */
   public JHomeScreen(Controller controller) {
      this.controller = controller;
      
      initContent();
      buildContent();
   }   
   
   /**
    * Initialise les composants de l'interface.
    */
   public void initContent() {
      quickExpense = new QuickExpense(controller.getCore());
   }
   
   /**
    * Construit et place les éléments de l'interface.
    */
   public void buildContent() {
      setLayout(new GridBagLayout());
      
      GridBagConstraints gblConstraints = new GridBagConstraints(); 
      // Options globales de contrainte.
      gblConstraints.fill = GridBagConstraints.BOTH;
      
      gblConstraints.insets = new Insets(5, 5, 5, 5);
      gblConstraints.weighty = 0.5;
      
      // Paramétrage des contraintes et ajout du panel d'alerte.
      gblConstraints.weightx = 0.5;
      gblConstraints.gridx = 0;
      gblConstraints.gridy = 0;      
      add(getWarningPanel(),gblConstraints);
      
      // Paramétrage des contraintes et ajout du panel des derniers mouvements d'argent.
      gblConstraints.weightx = 0.5;
      gblConstraints.gridx = 1;
      gblConstraints.gridy = 0;
      add(new JMoneyMove(controller.getCore()),gblConstraints);
      
      // Désormais, les composants suivants prennent toute la largeur verticale.
      gblConstraints.gridwidth = 2;
      
      // Ajout du premier séparateur.
      gblConstraints.gridx = 0;
      gblConstraints.gridy = 1;
      gblConstraints.weighty = 0.0;
      JSeparator sepOne = new JSeparator();
      add(sepOne, gblConstraints);
      
      // Paramétrage des contraintes et ajout de la zone d'ajout de dépense rapide.
      gblConstraints.anchor = GridBagConstraints.NORTH;
      gblConstraints.weighty = 0.02;
      gblConstraints.gridx = 0;
      gblConstraints.gridy = 2;
      add(quickExpense.getGraphicalComponent(),gblConstraints);
   }

   /**
    * Renvoie le panel d'alertes.
    * @return le panel d'alertes.
    */
   private JPanel getWarningPanel() {
      JPanel pnlWarning = new JPanel();
      
      pnlWarning.setLayout(new BorderLayout());
      
      // Ajout des composants au panel d'alertes.
      pnlWarning.add(new JLabel("Alertes:"), BorderLayout.NORTH);
      
      // Contenu de la liste
      DefaultListModel<String> dlmWarning = new DefaultListModel<>();
      dlmWarning.addElement("C'est la fin du monde");
      dlmWarning.addElement("J'aurais pas du dépenser autant :(");
      
      JList<String> lstWarning = new JList<>(dlmWarning);
      
      pnlWarning.add(lstWarning, BorderLayout.CENTER);
      
      return pnlWarning;
   }
}
