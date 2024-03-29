/* ============================================================================
 * Nom du fichier   : JTimeSliceChooser.java
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
package gui.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import settings.Language.Text;
import utils.TimeSlice;

/**
 * Panneau de sélection d'intervalle de temps (Journalier, Mois, etc...).
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class JTimeSliceChooser extends JPanel {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = 2161876509341301904L;

   // Composants.
   private ButtonGroup radioGroup;
   private LinkedList<JRadioButton> list;

   /**
    * Crée le panneau de sélection d'intervalle
    * 
    * @param slices
    *           - les différents intervalles de temps à afficher.
    */
   public JTimeSliceChooser(TimeSlice... slices) {
      initContent(slices);
      buildContent(slices);
   }

   /**
    * Initialise le contenu de la fenêtre.
    * 
    * @param slices
    *           - les différents intervalles de temps à afficher.
    */
   private void initContent(TimeSlice... slices) {
      radioGroup = new ButtonGroup();
      list = new LinkedList<>();

      for (TimeSlice slice : slices) {
         list.add(new JRadioButton(slice.getName()));
         radioGroup.add(list.getLast());
      }
   }

   /**
    * Construit le composant graphique.
    * 
    * @param slices
    *           - les différents intervalles de temps à afficher.
    */
   private void buildContent(TimeSlice... slices) {
      setLayout(new GridBagLayout());

      GridBagConstraints constraints = new GridBagConstraints();
      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.weightx = 0.5;
      constraints.weighty = 0.5;

      constraints.gridx = 0;
      constraints.gridy = 0;
      add(buildRadioButtonGroup(), constraints);
   }

   /**
    * Construit et retourne le groupe de radio boutons sur le panneau.
    * 
    * @return Le panneau créé.
    */
   private JPanel buildRadioButtonGroup() {
      JPanel pnlContent = new JPanel();

      pnlContent.setLayout(new GridLayout(radioGroup.getButtonCount() / 2, 2));
      pnlContent.setBorder(BorderFactory
            .createTitledBorder(Text.TIME_SLICE_SETTINGS.toString()));

      for (JRadioButton button : list) {
         pnlContent.add(button);
      }
      return pnlContent;
   }

   /**
    * Retourne l'identifiant de l'intervalle de temps sélectionné dans le
    * composant.
    * 
    * @return L'intervalle sélectionné.
    */
   public TimeSlice getTimeSlice() {
      for (JRadioButton button : list) {
         if (button.isSelected()) {
            return TimeSlice.getTimeSlice(button.getText());
         }
      }
      return null;
   }
}
