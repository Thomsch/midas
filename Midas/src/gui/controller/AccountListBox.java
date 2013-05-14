/* ============================================================================
 * Nom du fichier   : AccountList.java
 * ============================================================================
 * Date de création : 9 mai 2013
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
import gui.component.JAccountList;

import java.awt.Component;

import core.Core;
import core.components.AccountList;

/**
 * Contrôleur pour la liste à séléction simple d'auteurs.
 * 
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 * 
 */
public class AccountListBox extends Controller {

   JAccountList view;
   AccountList model;

   /**
    * @param core
    */
   public AccountListBox(Core core) {
      super(core);
      model.addObserver(view);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gui.Controller#initComponents()
    */
   @Override
   protected void initComponents() {
      model = getCore().getAllAccounts();
      view = new JAccountList(model);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gui.Controller#initListeners()
    */
   @Override
   protected void initListeners() {

   }

   /*
    * (non-Javadoc)
    * 
    * @see gui.Controller#getGraphicalComponent()
    */
   @Override
   public Component getGraphicalComponent() {
      return view;
   }

}