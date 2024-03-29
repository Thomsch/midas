/* ============================================================================
 * Nom du fichier   : InterfaceLauncher.java
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
package midas;

import javax.swing.JFrame;

import gui.MainFrame;

/**
 * Launcher du programme pour tester les interfaces.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class InterfaceLauncher {

   /**
    * @param args Options de lancement.
    */
   public static void main(String[] args) {
      JFrame i = new MainFrame();
      i.setVisible(true);
   }

}
