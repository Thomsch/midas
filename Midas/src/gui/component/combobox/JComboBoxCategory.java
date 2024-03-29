/* ============================================================================
 * Nom du fichier   : JComboBoxesCategory.java
 * ============================================================================
 * Date de création : 16 avr. 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.component.combobox;

import gui.JComboBoxTemplate;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.DefaultComboBoxModel;

import settings.Language.Text;

import core.components.Category;
import core.components.CategoryList;

/**
 * Liste déroulante de catégorie.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class JComboBoxCategory extends JComboBoxTemplate<Category> {
   
   /**
    * ID de sérialisation.
    */
   private static final long serialVersionUID = 9189481079308834687L;
   
   private CategoryList categories;
   private String select;
   private String newEntry;
   
   /**
    * Crée la liste déroulante des catégories.
    * 
    * @param categories
    *           - la liste des catégories voulues.
    */
   public JComboBoxCategory(CategoryList categories) {
      this(categories, false);
   }
   
   /**
    * Crée la liste déroulante des catégories.
    * 
    * @param children
    *           - la liste des catégories.
    * @param isChildren
    *           - s'il s'agit d'une liste de sous-catégories ou non.
    */
   public JComboBoxCategory(CategoryList categories, boolean isChildren) {
      this.categories = categories;
      
      if(isChildren) {
         select = new String(Text.SELECT_SUBCATEGORY_LABEL.toString());
         newEntry = new String(Text.NEW_SUBCATEGORY_LABEL.toString());
      }
      else {
         select = new String(Text.SELECT_CATEGORY_LABEL.toString());
         newEntry = new String(Text.NEW_CATEGORY_LABEL.toString());
      }
      update(categories, null);      
   }

   @Override
   public void update(Observable o, Object arg) {
      LinkedList<Category> list = categories.getAll(new SortByName());
      
      if(isFirstUse()) {
         list.addFirst(categories.createFalseEntry(select));
      }
      
      int index = updateIndex();
      list.add(index, categories.createFalseEntry(newEntry));
      
      Category[] temp = new Category[0];
      setModel(new DefaultComboBoxModel<Category>(list.toArray(temp)));
   }
   
   /**
    * Comparateur de catégories selon leurs noms.
    * 
    * @author Biolzi Sébastien
    * @author Brito Carvalho Bruno
    * @author Decorvet Grégoire
    * @author Schweizer Thomas
    * @author Sinniger Marcel
    * 
    */
   private class SortByName implements Comparator<Category> {
      @Override
      public int compare(Category arg0, Category arg1) {
         return arg0.getName().compareToIgnoreCase(arg1.getName());
      }
   } 
}