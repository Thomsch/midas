/* ============================================================================
 * Nom du fichier   : ManageAuthorFrame.java
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

import gui.JManageFrame;
import gui.testdata.AuthorList;

import javax.swing.JPanel;

/**
 * TODO
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class JManageAuthorFrame extends JManageFrame {

   /**
    * ID de série.
    */
   private static final long serialVersionUID = 3252804495254018094L;  
   
   protected JPanel initContent() {
      super.initContent();
      getContent().add(new AuthorList(), getConstraints());      
      return getContent();
   }
   
   
   

}