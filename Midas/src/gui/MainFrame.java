/* ============================================================================
 * Nom du fichier   : MainFrame.java
 * ============================================================================
 * Date de création : 12 avr. 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Fenêtre principale.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{
      
   /**
    * Constructeur sans paramètre.
    */
   public MainFrame(String title)  {
      super(title);
      init();
   }
   
   /**
    * Initialisations des différentes éléments présents dans la vue.
    */
   private void init() {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setContentPane(buildContent());
      
      pack();
      setVisible(true);
   }
   
   /**
    * Renvoie le contenu de l'interface.
    * Notre interface est composée d'un menu principal,
    * et d'une fenêtre à onlgets.
    * @return Le contenu à afficher.
    */
   private JPanel buildContent() {
      
      // Définission du menu principal.
      setJMenuBar(new MainMenu());
      
      // Définission du contenur principal.
      JPanel pnlContent = new JPanel();
      pnlContent.setLayout(new BorderLayout());
      
      JTabbedPane tbpMain = new JTabbedPane();
      tbpMain.addTab("Accueil", new HomeScreen());
      
      pnlContent.add(tbpMain,BorderLayout.CENTER);
      return pnlContent;
   }   
   
}
