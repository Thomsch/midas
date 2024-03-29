/* ============================================================================
 * Nom du fichier   : JCategoryIE.java
 * ============================================================================
 * Date de création : 26 mai 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.component.infoedition;

import gui.JInfoEditionPane;
import gui.JManageFrame;
import gui.component.JInfoEditionLabel;
import gui.utils.TextChangedListener;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.event.DocumentEvent;

import settings.Language.Text;
import core.components.Category;

/**
 * Panneau d'édition des informations d'une catégorie.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class JCategoryIE extends JInfoEditionPane<Category> {

   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = 9096965303469092507L;

   private JInfoEditionLabel ielName;
   private JInfoEditionLabel ielParent;

   /**
    * Crée un panneau pour l'affichage des informations d'une catégorie.
    * 
    * @param parent
    *           - la fenêtre parente.
    * @param container
    *           - le conteneur de ce panneau.
    * @param last
    *           - le précédent panneau.
    * @param data
    *           - la catégorie à afficher.
    */
   public JCategoryIE(JManageFrame parent, Container container,
         JCategoryIE last, Category data) {
      this(parent, container, last, data, false);
   }

   /**
    * Crée un panneau avec rien à afficher.
    */
   public JCategoryIE() {
      super();
   }

   /**
    * Crée un panneau pour l'affichage des informations d'une catégorie.
    * 
    * @param parent
    *           - la fenêtre parente.
    * @param container
    *           - le conteneur de ce panneau.
    * @param last
    *           - le précédent panneau.
    * @param data
    *           - la catégorie à afficher.
    * @param edition
    *           - si les informations sont éditables ou non.
    */
   public JCategoryIE(JManageFrame parent, Container container,
         JCategoryIE last, Category data, boolean edition) {
      super(parent, container, last, data, edition);
   }

   @Override
   public void initListeners() {
      ielName.addTextChangedListener(new TextChangedListener() {
         @Override
         public void textChanged(DocumentEvent event) {
            checkItemIntegrity();
         }
      });
   }

   @Override
   public LinkedList<JInfoEditionLabel> initContent(Category data) {
      LinkedList<JInfoEditionLabel> result = new LinkedList<>();

      // Définition des champs.
      ielName = new JInfoEditionLabel(Text.CATEGORY_NAME_LABEL, data.getName());
      ielParent = new JInfoEditionLabel(Text.CATEGORY_PARENT_LABEL,
            (data.isChild() ? data.getParentCategory().getName() : ""));

      // Ajout des champs à la liste.
      result.add(ielName);

      return result;
   }

   @Override
   public void buildContent() {
      setLayout(new GridLayout(2, 1));
      add(ielName);

      // On affiche le parent seulement si la catégorie en possède un.
      if (data.isChild()) {
         add(ielParent);
      }
   }

   /**
    * Vérifie que l'objet complété par l'utilisateur peut être sauvegardé dans
    * la base de données.
    */
   private void checkItemIntegrity() {
      boolean checkResult;
      checkResult = ielName.isValidData();
      setEnabledValidateButton(checkResult);
   }

   @Override
   public void saveItem() {
      data.setName(ielName.getText());
   }
}
