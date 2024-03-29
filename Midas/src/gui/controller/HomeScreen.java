/* ============================================================================
 * Nom du fichier   : HomeScreen.java
 * ============================================================================
 * Date de création : 28 avr. 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package gui.controller;

import gui.Controller;
import gui.component.JHomeScreen;
import core.Core;

/**
 * Contrôleur de l'écran d'accueil.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class HomeScreen extends Controller {

   private JHomeScreen homeScreen;

   /**
    * Crée le contrôleur de l'écran d'accueil.
    * 
    * @param core
    *           - le coeur logique du programme.
    */
   public HomeScreen(Core core) {
      super(core);
   }

   @Override
   protected void initComponents() {
      homeScreen = new JHomeScreen(this);
   }

   @Override
   protected void initListeners() {
      // Pas de listener
   }

   @Override
   public JHomeScreen getGraphicalComponent() {
      return homeScreen;
   }
}
