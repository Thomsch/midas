/* ============================================================================
 * Nom du fichier   : AccountView.java
 * ============================================================================
 * Date de création : 22 avr. 2013
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

import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import settings.Language.Text;
import core.components.Category;


/**
 * Exemple => A Supprimer dans un futur proche.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class JCategory extends JFrame implements View {

   /**
    * ID de série.
    */
   private static final long serialVersionUID = 9054247667917665007L;

   private JPanel panel;
   
   private JButton btnAccept;
   private JButton btnCancel;
   private JTextField txAccountName;
   
   private Category modele;
   
   public JCategory(String title, int posX, int posY, int width, int height, Category modele) {
      setTitle(title);
      
      setBounds(posX, posY, width, height);
      
      this.modele = modele;
      
      panel = new JPanel();
      txAccountName = new JTextField();
      btnAccept = new JButton(Text.VALIDATE_BUTTON.toString());
      btnCancel = new JButton(Text.CANCEL_BUTTON.toString());
      
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      
      panel.add(txAccountName);
      panel.add(btnAccept);
      panel.add(btnCancel);
      
      getContentPane().add(panel);
      
   }

   /* (non-Javadoc)
    * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
    */
   @Override
   public void update(Observable arg0, Object arg1) {
      txAccountName.setText(modele.getName());
      
      repaint();
   }

}
