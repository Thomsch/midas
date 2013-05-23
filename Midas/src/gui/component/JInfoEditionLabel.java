/* ============================================================================
 * Nom du fichier   : JInfoEditionLabel.java
 * ============================================================================
 * Date de création : 22 mai 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.component;

import gui.utils.TextChangedListener;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import settings.Language.Text;

/**
 * Représente un champ de texte qui peut être éditable ou non.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class JInfoEditionLabel extends JPanel {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = 3259394361524059135L;
   
   private final int nbRows = 20;
   
   private JLabel lblMetaInfo;
   private JTextField tfdData;
   
   /**
    * Contructeur de la classe.
    * @param metainfo Contenu du label de présentation de la donnée.
    * @param data Donnée présentée.
    */
   public JInfoEditionLabel (Text metainfo, String data) {
      initContent(metainfo, data);
      buildContent();
   }
   
   /**
    * Initialise les composants du panel.
    * @param metainfo Contenu du label de présentation de la donnée.
    * @param data Donnée présentée.
    */
   public void initContent(Text metainfo, String data) {
      lblMetaInfo = new JLabel(metainfo.toString());
      tfdData = new JTextField(data, nbRows);
      tfdData.setEditable(false); // On ne peut pas éditer par défaut.
   }
   
   /**
    * Place les composants du panel.
    */
   public void buildContent() {
      setLayout(new BorderLayout(5,5));
      add(lblMetaInfo, BorderLayout.WEST);
      add(tfdData, BorderLayout.EAST); 
   }
   
   /**
    * Rends le champ de donnée éditable ou non.
    * @param b True si la zone de texte est éditable.
    */
   public void setEditable(boolean b) {
      tfdData.setEditable(b);
   }
   
   /**
    * Ajout un écouteur sur l'action de taper du texte dans le champ de saisie.
    * @param listener Action à effectuer lorsque cela se produit.
    */
   public void addTextChangedListener(TextChangedListener listener) {
      TextChangedListener.addListener(tfdData, listener);
   }
   
   /**
    * Renovie le contenu de la zone de texte.
    * @return Le contenu de la zone de texte.
    */
   public String getText() {
      return tfdData.getText();
   }
}
