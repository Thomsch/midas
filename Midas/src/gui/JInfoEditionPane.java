/* ============================================================================
 * Nom du fichier   : JInfoEditionPane.java
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
package gui;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Panel d'affichage ou de modification d'informations.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * @param <E>
 *
 */
public abstract class JInfoEditionPane<E> extends JPanel {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = -1271766180763265086L;
   
   // Données représentées par ce panel.
   // (En protected pour gagner en lisibilité chez les enfants.)
   protected E data;
   
   /**
    * Constructeur par défaut => aucune information à afficher.
    */
   public JInfoEditionPane () {
   }

   /**
    * La classe ce met en mode "Présentation des données".
    * @param parent Fenêtre parente qui contient cet objet.
    * @param container Container direct de cet objet.
    * @param last Objet précédent affiché de cette classe.
    * @param data Les nouvelles données à afficher.
    */
   public JInfoEditionPane (JDialog parent, Container container, JInfoEditionPane<E> last, E data) {
      this(parent, container, last, data, false);
   }
   
   /**
    * La classe se met en mode "Edition" ou "Présentation de données".
    * @param parent Fenêtre parente qui contient cet objet.
    * @param container Container direct de cet objet.
    * @param last Objet précédent affiché de cette classe.
    * @param data Les nouvelles données à afficher.
    * @param edition True => mode "Edition". False => "Présentation de données".
    */
   public JInfoEditionPane (JDialog parent, Container container, JInfoEditionPane<E> last, E data, boolean edition) {
      container.remove(last);
      this.data = data;
      initContent(data);
      initListeners();
      buildContent();
      container.add(last);
      parent.pack();
   }
   
   /**
    * Initialise les listeners propre à ce panel.
    */
   public abstract void initListeners();

   /**
    * Initialise les composants du panel.
    */
   public abstract void initContent(E data);
   
   /**
    * Construit et place les composants sur le panel.
    */
   public abstract void buildContent();

}