﻿/* ============================================================================
 * Nom du fichier   : Core.java
 * ============================================================================
 * Date de création : 7 avr. 2013
 * ============================================================================
 * Auteurs          : Biolzi Sébastien
 *                    Brito Carvalho Bruno
 *                    Decorvet Grégoire
 *                    Schweizer Thomas
 *                    Sinniger Marcel
 * ============================================================================
 */
package core;

import database.dbComponents.DBController;
import database.utils.DatabaseConstraintViolation;
import database.utils.DatabaseException;
import settings.Settings;

/**
 * Coeur du programme servant à recenser les fonctionnalités du logiciel.
 * @author Biolzi Sébastien
 * @author Brito Carvalho Bruno
 * @author Decorvet Grégoire
 * @author Schweizer Thomas
 * @author Sinniger Marcel
 *
 */
public class Core {
   
   private Settings settings;
   
   private DBController dbController;
   
   public Core() {
      settings = new Settings();
      dbController = new DBController();
      
      settings.loadSettings();
   }
   
   
   /**
    * Créer un nouveau compte, à compléter par l'utilisateur.
    * @return le nouveau compte.
    */
   public Account createAccount() {
      
      return new Account(dbController.createDBAccount());
      
   }
   
   /**
    * Retourne le compte ayant pour identifiant celui passé en paramètres.
    * @param id - l'identifiant du compte souhaité.
    * @return le compte correspondant à l'identifiant, null le cas échéant.
    */
   public Account getAccount(int id) {
      Account result = null;
      
      try {
         result = new Account(dbController.getDbAccount(id));
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to get the account with id "
                               + id + " from the database.");
      }
      
      return result;
   }
   
   /**
    * Sauvegarde ou met à jour le compte donné dans la base de donnée.
    * @param account - le compte à sauver.
    */
   public void saveAccount(Account account) {
      try {
         dbController.saveToDatabase(account.getDBAccount());
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the account with id "
               + /* id + */ " to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the account with id "
               + /* id + */ " to database.");
      }
      
   }
   
   /**
    * Créer une nouvelle catégorie dont les champs sont à compléter.
    * @return la catégorie à compléter.
    */
   public Category createCategory(){
      
      return new Category(dbController.createCategory());
      
   }
   
   /**
    * Retourne la catégorie ayant pour identifiant celui passé en paramètres.
    * @param id - l'identifiant de la catégorie souhaitée.
    * @return la catégorie correspondant à l'identifiant, null le cas échéant.
    */
   public Category getCategoryID(int id) {
      Category result = null;
      
      try {
         result = new Category(dbController.getDbCategory(id));
      }
      catch (DatabaseException e) {
         
      }
      
      return result; 
   }
   
   /**
    * Sauvegarde ou met à jour la catégorie donnée dans la base de donnée.
    * @param category - la catégorie à sauver.
    */
   public void saveCategory(Category category) {
      try {
         dbController.saveToDatabase(category.getDBCategory());
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the category with id "
               + /* id + */ " to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the category with id "
               + /* id + */ " to database.");
      }
   }
   
   /**
    * Créer un nouveau budget dont les champs sont à compléter.
    * @return le budget à compléter.
    */
   public Budget createBudget(){
      return new Budget(dbController.createDbBudget());
   }
   
   /**
    * Retourne le budget ayant pour identifiant celui passé en paramètres.
    * @param id - l'identifiant du budget souhaité.
    * @return le budget correspondant à l'identifiant, null le cas échéant.
    */
   public Budget getBudgetID(int id) {
      Budget result = null;
      
      try {
         result = new Budget(dbController.getDbBudget(id));
      }
      catch (DatabaseException e) {
         
      }
      
      return result; 
   }
   
   /**
    * Sauvegarde ou met à jour le budget donné dans la base de donnée.
    * @param budget - le budget à sauver.
    */
   public void saveBudget(Budget budget) {
      try {
         dbController.saveToDatabase(budget.getDBBudget());
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the budget with id "
               + /* id + */ " to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the budget with id "
               + /* id + */ " to database.");
      }
   }
   
   /**
    * TODO Créer un nouveau budget à la volée dont les champs sont à compléter.
    * @return le budget à compléter.
    */
   public BudgetOnTheFly createBudgetOnTheFly(){
//      return new BudgetOnTheFly(dbController.createDbBudgetOnTheFly());
      return null;
   }
   
   /**
    * TODO Retourne le budget ayant pour identifiant celui passé en paramètres.
    * @param id - l'identifiant du budget souhaité.
    * @return la catégorie correspondant à l'identifiant, null le cas échéant.
    */
   public BudgetOnTheFly getBudgetOnTheFlyID(int id) {
      BudgetOnTheFly result = null;
      
      
//      try {
//         result = new BudgetOnTheFly(dbController.getDbBudgetOnTheFly(id));
//      }
//      catch (DatabaseException e) {
//         
//      }
      
      return result; 
   }
   
   /**
    * TODO Sauvegarde ou met à jour le budget à la volée donné dans la base de
    * donnée.
    * @param budget - le budget à sauver.
    */
   public void saveBudgetOnTheFly(BudgetOnTheFly budget) {
      
//      try {
//         dbController.saveToDatabase(budget.getDBBudget());
//      }
//      catch (DatabaseConstraintViolation e) {
//         MidasLogs.errors.push("Core", "Unable to save the budget with id "
//               + /* id + */ " to database, because of constraint violation.");
//      }
//      catch (DatabaseException e) {
//         MidasLogs.errors.push("Core", "Unable to save the budget with id "
//               + /* id + */ " to database.");
//      }
      
   }
   
   /**
    * Créer un nouvel utilisateur dont les champs sont à compléter.
    * @return l'utilisateur à compléter.
    */
   public User createUser(){
      return new User(dbController.createDBUser());
   }
   
   /**
    * TODO Retourne l'utilisateur ayant pour identifiant celui passé en paramètres.
    * @param id - l'identifiant de l'utilisateur souhaité.
    * @return l'utilisateur correspondant à l'identifiant, null le cas échéant.
    */
   public User getUserID(int id) {
      User result = null;
      
//      try {
//         result = new User(dbController.getDBUser(id));
//      }
//      catch (DatabaseException e) {
//         
//      }
      
      return result; 
   }
   
   /**
    * Sauvegarde ou met à jour l'utilisateur donné dans la base de donnée.
    * @param user - l'utilisateur à sauver.
    */
   public void saveUser(User user) {
      try {
         dbController.saveToDatabase(user.getDBUser());
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the user with id "
               + /* id + */ " to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the user with id "
               + /* id + */ " to database.");
      }
   }
   
   /**
    * Créer une nouvelle transaction dont les champs sont à compléter.
    * @return la transaction à compléter.
    */
   public FinancialTransaction createFinancialTransaction(){
      return new FinancialTransaction(
                                    dbController.createFinancialTransaction());
   }
   
   /**
    * Retourne la transaction ayant pour identifiant celui passé en paramètres.
    * @param id - l'identifiant de la transaction souhaitée.
    * @return la transaction correspondant à l'identifiant, null le cas échéant.
    */
   public FinancialTransaction getFinancialTransactionID(int id) {
      FinancialTransaction result = null;
      
      try {
         result = new FinancialTransaction(
                                 dbController.getDbFinancialTransaction(id));
      }
      catch (DatabaseException e) {
         
      }
      
      return result; 
   }
   
   /**
    * TODO Sauvegarde ou met à jour la transaction donnée dans la base de donnée.
    * @param transaction - la transaction à sauver.
    */
   public void saveFinancialTransaction(FinancialTransaction transaction) {
//      try {
//         dbController.saveToDatabase(transaction.getDBFinancialTransaction());
//      }
//      catch (DatabaseConstraintViolation e) {
//         MidasLogs.errors.push("Core", "Unable to save the budget with id "
//               + /* id + */ " to database, because of constraint violation.");
//      }
//      catch (DatabaseException e) {
//         MidasLogs.errors.push("Core", "Unable to save the budget with id "
//               + /* id + */ " to database.");
//      }
   }
   
   
   
}
