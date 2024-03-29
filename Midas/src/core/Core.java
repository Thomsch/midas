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

import java.util.Iterator;
import java.util.LinkedList;

import settings.Settings;
import core.cache.Cache;
import core.components.Account;
import core.components.AccountList;
import core.components.Alert;
import core.components.AlertList;
import core.components.Budget;
import core.components.BudgetList;
import core.components.BudgetOnTheFly;
import core.components.Category;
import core.components.CategoryList;
import core.components.FinancialTransaction;
import core.components.Recurrence;
import core.components.FinancialTransactionList;
import core.components.User;
import core.components.UserList;
import core.exceptions.AmountUnavailableException;
import database.dbComponents.DBAccount;
import database.dbComponents.DBBudget;
import database.dbComponents.DBBudgetOnTheFly;
import database.dbComponents.DBCategory;
import database.dbComponents.DBController;
import database.dbComponents.DBFinancialTransaction;
import database.dbComponents.DBRecurrence;
import database.dbComponents.DBUser;
import database.utils.DatabaseConstraintViolation;
import database.utils.DatabaseException;

/**
 * Coeur du programme servant à recenser les fonctionnalités du logiciel.
 * 
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

   private Cache cache;

   private UserList users;
   private AccountList accounts;
   private CategoryList primaryCategories;
   private BudgetList budgets;
   private FinancialTransactionList lastTransactions;
   private AlertList alerts;
   
   private final int numberOfTransactions = 20;

   public Core() {
      settings = new Settings();
      dbController = new DBController();
      cache = new Cache();

      users = new UserList(this);
      accounts = new AccountList(this);
      primaryCategories = new CategoryList(this);
      budgets = new BudgetList(this);
      
      lastTransactions = new FinancialTransactionList(this);
      alerts = new AlertList(this);

      settings.loadSettings();

      loadUsers();
      loadAccounts();
      loadPrimaryCategories();
      loadBudgets();
      loadLatestFinancialTransactions();
      loadAlerts();
   }
   
   /**
    * Charge et établis la liste des numberOfTransactions dernières 
    * transactions.
    */
   private void loadLatestFinancialTransactions() {
      LinkedList<DBFinancialTransaction> dbTransactions = null;
      try {
         dbTransactions =
            dbController.getLatestDbFinancialTransactions(numberOfTransactions);
      }
      catch (DatabaseException e) {

      }

      if (dbTransactions != null) {
         LinkedList<FinancialTransaction> transactionTemp = new LinkedList<>();

         for (DBFinancialTransaction dbTransaction : dbTransactions) {
            transactionTemp.add(new FinancialTransaction(this, dbTransaction));
         }

         lastTransactions.setItems(transactionTemp);
      }

   }
   
   /**
    * Charge et établis la liste des alertes pour chaque budget à partir de la
    * liste des budgets.
    */
   private void loadAlerts() {
      
      LinkedList<Budget> list = budgets.getList();
      
      Iterator<Budget> it = list.iterator();
      Budget current;
      
      while (it.hasNext()) {
         current = it.next();
         
         // Ajout d'une alerte si le budget n'est pas tenu et encore actif
         if (!current.isPositive() && !current.isFinished()) {
            alerts.addOrUpdate(new Alert(this, current));
         }
      }
   }

   /**
    * Charge et établis la liste de tous les budgets depuis la base de donnée.
    */
   private void loadBudgets() {
      LinkedList<DBBudget> dbBudgets = null;
      try {
         dbBudgets = dbController.getAllDbBudgets();
      }
      catch (DatabaseException e) {

      }

      if (dbBudgets != null) {
         LinkedList<Budget> budgetTemp = new LinkedList<>();

         for (DBBudget dbBudget : dbBudgets) {
            budgetTemp.add(new Budget(this, dbBudget));
         }

         budgets.setItems(budgetTemp);
      }
   }

   /**
    * Charge et établis la liste de tous les utilisateurs depuis la base de
    * donnée.
    */
   private void loadUsers() {
      LinkedList<DBUser> dbUsers = null;
      try {
         dbUsers = dbController.getAllDbUsers();
      }
      catch (DatabaseException e) {

      }

      if (dbUsers != null) {
         LinkedList<User> userTemp = new LinkedList<>();

         for (DBUser dbUser : dbUsers) {
            userTemp.add(new User(this, dbUser));
         }

         users.setItems(userTemp);
      }
   }

   /**
    * Charge et établis la liste de tous les comptes depuis la base de donnée.
    */
   private void loadAccounts() {
      LinkedList<DBAccount> dbAccounts = null;
      try {
         dbAccounts = dbController.getAllDbAccounts();
      }
      catch (DatabaseException e) {

      }

      if (dbAccounts != null) {
         LinkedList<Account> accountTemp = new LinkedList<>();

         for (DBAccount dbAccount : dbAccounts) {
            accountTemp.add(new Account(this, dbAccount));
         }

         accounts.setItems(accountTemp);
      }
   }

   /**
    * Charge et établis la liste de tous les catégories primaires (celle n'ayant
    * pas de catégorie parente).
    */
   private void loadPrimaryCategories() {
      LinkedList<DBCategory> dbCategories = null;
      try {
         dbCategories = dbController.getAllParentCategories();
      }
      catch (DatabaseException e) {

      }

      if (dbCategories != null) {
         LinkedList<Category> categoryTemp = new LinkedList<>();

         for (DBCategory dbCategory : dbCategories) {
            categoryTemp.add(new Category(this, dbCategory));
         }
         primaryCategories.setItems(categoryTemp);
      }
   }

   /**
    * Crée un nouveau compte, à compléter par l'utilisateur.
    * 
    * @return Le nouveau compte.
    */
   public Account createAccount() {
      return new Account(this, dbController.createDBAccount());
   }

   /**
    * Crée un nouveau budget dont les champs sont à compléter.
    * 
    * @return Le budget à compléter.
    */
   public Budget createBudget() {
      return new Budget(this, dbController.createDbBudget());
   }

   /**
    * Crée un nouveau budget à la volée dont les champs sont à compléter.
    * 
    * @return Le budget à compléter.
    */
   public BudgetOnTheFly createBudgetOnTheFly() {
      return new BudgetOnTheFly(this, dbController.createDbBudgetOnTheFly());
   }

   /**
    * Crée un nouvel utilisateur dont les champs sont à compléter.
    * 
    * @return L'utilisateur à compléter.
    */
   public User createUser() {
      return new User(this, dbController.createDBUser());
   }

   /**
    * Crée une nouvelle récurrence.
    * 
    * @return La nouvelle récurrence.
    */
   public Recurrence createReccurence() {
      return new Recurrence(this, dbController.createRecurence());
   }

   /**
    * Crée une nouvelle transaction dont les champs sont à compléter.
    * 
    * @return La transaction à compléter.
    */
   public FinancialTransaction createFinancialTransaction() {
      return new FinancialTransaction(this,
            dbController.createFinancialTransaction());
   }

   /**
    * Créer une nouvelle catégorie dont les champs sont à compléter.
    * 
    * @return La catégorie à compléter.
    */
   public Category createCategory() {
      return new Category(this, dbController.createCategory());
   }

   /**
    * Retourne le compte ayant pour identifiant celui passé en paramètres.
    * 
    * @param id
    *           - l'identifiant du compte souhaité.
    * @return Le compte correspondant à l'identifiant, null le cas échéant.
    */
   public Account getAccount(int id) {
      Account result = accounts.get(id);

      // Si pas présent dans le coeur, demander à la base de données pour
      // vérifier
      if (result == null) {
         try {
            DBAccount dbAccount = dbController.getDbAccount(id);
            
            // Mise à jour de la liste si présent dans la base de données
            if (dbAccount != null) {
               result = new Account(this, dbAccount);
               
               // On ne charge le compte que s'il est actif !
               if(dbAccount.getEnabled()) {
                  accounts.addItem(result);
               }
            }
            
         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the account with id "
                  + id + " from the database.");
         }
      }

      return result;
   }

   /**
    * Retourne l'utilisateur ayant pour identifiant celui passé en paramètres.
    * 
    * @param id
    *           - l'identifiant de l'utilisateur souhaité.
    * @return L'utilisateur correspondant à l'identifiant, null le cas échéant.
    */
   public User getUser(int id) {
      User result = users.get(id);

      // Si pas présent dans le coeur, demander à la base de données pour
      // vérifier
      if (result == null) {
         try {
            DBUser dbUser = dbController.getDbUser(id);
            
            // Mise à jour de la liste si présent dans la base de données
            if (dbUser != null) {
               result = new User(this, dbUser);
               
               // On ne charge l'utilisateur que s'il est actif !
               if(dbUser.getEnabled()) {
                  users.addItem(result);
               }
            }
            
         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the user with id "
                  + id + " from the database.");
         }
      }

      return result;
   }

   /**
    * Retourne la catégorie ayant pour identifiant celui passé en paramètres.
    * 
    * @param id
    *           - l'identifiant de la catégorie souhaitée.
    * @return La catégorie correspondant à l'identifiant, null le cas échéant.
    */
   public Category getCategory(int id) {
      Category result = primaryCategories.get(id);

      // Si pas présent dans le coeur, demander à la base de données pour
      // vérifier
      if (result == null) {
         try {
            DBCategory dbCategory = dbController.getDbCategory(id);
            
            // Mise à jour de la liste si présent dans la base de données
            if (dbCategory != null) {
               result = new Category(this, dbCategory);
               
               // On ne charge l'utilisateur que s'il est actif !
               if(dbCategory.getEnabled()) {
                  primaryCategories.addItem(result);
               }
            }  
            
         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the category with id "
                  + id + " from the database.");
         }
      }
      
      return result;
   }

   /**
    * Retourne la transaction ayant pour identifiant celui passé en paramètres.
    * 
    * @param id
    *           - l'identifiant de la transaction souhaitée.
    * @return La transaction correspondant à l'identifiant, null le cas échéant.
    */
   public FinancialTransaction getFinancialTransaction(int id) {
      FinancialTransaction result = cache.getReference(
            FinancialTransaction.class, id);

      // Si pas présent dans le cache, demander à la base de données
      if (result == null) {
         try {
            
            DBFinancialTransaction dbFinancialTransaction =
                                    dbController.getDbFinancialTransaction(id);
            
            if (dbFinancialTransaction != null) {
               result = new FinancialTransaction(this, dbFinancialTransaction);

               // Mise à jour du cache
               cache.putToCache(result);
            }
         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the financial " +
                  "transaction with id " + id + " from the database.");
         }
      }
      return result;
   }

   /**
    * Retourne le budget ayant pour identifiant celui passé en paramètres.
    * 
    * @param id
    *           - l'identifiant du budget souhaité.
    * @return La catégorie correspondant à l'identifiant, null le cas échéant.
    */
   public BudgetOnTheFly getBudgetOnTheFly(int id) {
      BudgetOnTheFly result = cache.getReference(BudgetOnTheFly.class, id);

      // Si pas présent dans le cache, demander à la base de données
      if (result == null) {
         try {
            
            DBBudgetOnTheFly dbBudgetOnTheFly =
                                          dbController.getDbBudgetOnTheFly(id);
            
            if (dbBudgetOnTheFly != null) {
               result = new BudgetOnTheFly(this, dbBudgetOnTheFly);
               
               // Mise à jour du cache
               cache.putToCache(result);
            }
         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the budget on the fly "
                  + "with id " + id + " from the database.");
         }
      }

      return result;
   }

   /**
    * Retourne la récurrence ayant pour identifiant celui passé en paramètres.
    * 
    * @param id
    *           - l'identifiant de la récurrence souhaitée.
    * @return La récurrence correspondante à l'identifiant, null le cas échéant.
    */
   public Recurrence getRecurrence(int id) {
      Recurrence result = cache.getReference(Recurrence.class, id);

      // Si pas présent dans le cache, demander à la base de données
      if (result == null) {
         try {
            DBRecurrence dbRec = dbController.getDbRecurrence(id);

            if (dbRec != null) {
               result = new Recurrence(this, dbRec);

               // Mise à jour du cache
               cache.putToCache(result);
            }

         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the recurrence with id "
                  + id + " from the database.");
         }
      }

      return result;
   }

   /**
    * Retourne la liste des catégories étant des sous-catégories de cette donnée
    * en paramètre.
    * 
    * @param category
    *           - la catégorie parente.
    * @return La liste des sous-catégories de celle donnée.
    */
   public CategoryList getChildren(Category category) {
      CategoryList result = new CategoryList(this);
      LinkedList<DBCategory> dbChildren = null;
      try {
         dbChildren = dbController.getAllChildCategories(category.getId());
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core",
                               "Unable to get children from the database.");
      }

      if (dbChildren != null) {
         LinkedList<Category> categoryTemp = new LinkedList<>();

         for (DBCategory dbCategory : dbChildren) {
            categoryTemp.add(new Category(this, dbCategory));
         }
         result.setItems(categoryTemp);
      }
      return result;
   }

   /**
    * Retourne le budget ayant pour identifiant celui passé en paramètre.
    * 
    * @param id
    *           - l'identifiant du budget souhaité.
    * @return Le budget correspondant à l'identifiant, null le cas échéant.
    */
   public Budget getBudget(int id) {
      Budget result = budgets.get(id);

      // Si pas présent dans le coeur, demander à la base de données pour
      // vérifier
      if (result == null) {
         try {
            DBBudget dbBudget = dbController.getDbBudget(id);
            
            // Mise à jour de la liste si présent dans la base de données
            if (dbBudget != null) {
               result = new Budget(this, dbBudget);
               
               // On ne charge le budget que s'il est actif !
               if(dbBudget.getEnabled()) {
                  budgets.addItem(result);
               }
            }
            
         }
         catch (DatabaseException e) {
            MidasLogs.errors.push("Core", "Unable to get the budget with id "
                  + id + " from the database.");
         }
      }
      
      return result;
   }

   /**
    * Retourne la liste de tous les utilisateurs.
    * 
    * @return La liste des utilisateurs.
    */
   public UserList getAllUsers() {
      return users;
   }

   /**
    * Retourne la liste de tous les comptes.
    * 
    * @return La liste des comptes.
    */
   public AccountList getAllAccounts() {
      return accounts;
   }

   /**
    * Retourne la liste des dernières transactions financières.
    * 
    * @return La liste des transactions.
    */
   public FinancialTransactionList getLastFinancialTransactions() {
      return lastTransactions;
   }
   
   /**
    * Retourne la liste des alertes.
    * 
    * @return La liste des aletes.
    */
   public AlertList getAllAlerts() {
      return alerts;
   }
   
   /**
    * Retourne la liste de toutes les catégories principales.
    * 
    * @return La liste des catégories principales.
    */
   public CategoryList getAllPrimaryCategories() {
      return primaryCategories;
   }
   
   public CategoryList getAllCategories() {
      CategoryList result = new CategoryList(this);
      
      LinkedList<DBCategory> dbCategories = null;
      try {
         dbCategories = dbController.getAllDbCategories();
      }
      catch (DatabaseException e) {

      }

      if (dbCategories != null) {
         LinkedList<Category> categoryTemp = new LinkedList<>();

         for (DBCategory dbCategory : dbCategories) {
            categoryTemp.add(new Category(this, dbCategory));
         }
         result.setItems(categoryTemp);
      }
      return result;
   }

   /**
    * Retourne la liste de tous les budgets.
    * 
    * @return La liste des budgets.
    */
   public BudgetList getAllBudgets() {
      return budgets;
   }
   
   /**
    * Retourne la liste de tous les comptes qui sont sous leur seuil fixé.
    * 
    * @return Les comptes dont la limite de crédit est dépassée.
    */
   public AccountList getAllNegativeAccounts() {
      AccountList result = new AccountList(this);
      LinkedList<Account> list = getAllAccounts().getList();

      // Ajoute les comptes au seuil de crédit dépassé
      for (Account account : list) {
         if (account.isNegative()) {
            result.addItem(account);
         }
      }

      return result;
   }

   /**
    * Retourne la liste de tous les budgets qui ne sont pas tenus.
    * 
    * @return Les budgets non tenus.
    */
   public BudgetList getAllNegativeBudgets() {
      BudgetList result = new BudgetList(this);
      LinkedList<Budget> list = getAllBudgets().getList();

      // Ajoute tous les budgets négatifs non échus
      for (Budget budget : list) {
         if (!budget.isFinished() && !budget.isPositive()) {
            result.addItem(budget);
         }
      }

      return result;
   }

   /**
    * Retourne la liste de toutes les transactions financières.
    * 
    * @return La liste de toutes les transactions.
    */
   public LinkedList<FinancialTransaction> getAllFinancialTransaction() {
      LinkedList<DBFinancialTransaction> list;
      LinkedList<FinancialTransaction> result;
      result = cache.getAll(FinancialTransaction.class);

      try {
         list = dbController.getAllDbFinancialTransactions();

         boolean alreadyExist = false;

         for (DBFinancialTransaction dbItem : list) {

            alreadyExist = false;
            for (FinancialTransaction financialTransaction : result) {

               if (dbItem.getId() == financialTransaction.getId()) {
                  alreadyExist = true;
                  break;
               }
            }

            if (!alreadyExist) {
               result.add(new FinancialTransaction(this, dbItem));
            }

         }
      }
      catch (DatabaseException e) {

      }
      return result;
   }

   /**
    * Retourne la liste de toutes les transactions financières liées au budget
    * dont l'identifiant est donné en paramètre.
    * 
    * @param budgetId
    *           - l'identifiant du budget associé.
    * @return La liste des transactions liées au budget donné.
    */
   public LinkedList<FinancialTransaction>
                     getAllFinancialTransactionRelatedToBudget(int budgetId) {
      LinkedList<DBFinancialTransaction> list;
      LinkedList<FinancialTransaction> result;
      result = cache.getAll(FinancialTransaction.class);

      Iterator<FinancialTransaction> iterator = result.iterator();
      FinancialTransaction temp;
      while (iterator.hasNext()) {
         temp = iterator.next();
         // Suppression si le budget ne correspond pas
         if (temp.getBudgetId() == budgetId) {
            iterator.remove();
         }
      }

      try {
         list = dbController
               .getAllDbFinancialTransactionsRelatedToBudget(budgetId);

         boolean alreadyExist = false;

         for (DBFinancialTransaction dbItem : list) {

            alreadyExist = false;
            for (FinancialTransaction financialTransaction : result) {

               if (dbItem.getId() == financialTransaction.getId()) {
                  alreadyExist = true;
                  break;
               }
            }

            if (!alreadyExist) {
               result.add(new FinancialTransaction(this, dbItem));
            }

         }

      }
      catch (DatabaseException e) {

      }
      return result;
   }

   /**
    * Retourne la liste de toutes les transactions financières liées au compte
    * dont l'identifiant est donné en paramètre.
    * 
    * @param accountId
    *           - l'identifiant du compte associé.
    * @return La liste des transactions liées au compte donné.
    */
   public LinkedList<FinancialTransaction>
                     getAllFinancialTransactionRelatedToAccount(int accountId) {
      LinkedList<DBFinancialTransaction> list;
      LinkedList<FinancialTransaction> result;
      result = cache.getAll(FinancialTransaction.class);

      Iterator<FinancialTransaction> iterator = result.iterator();
      FinancialTransaction temp;
      while (iterator.hasNext()) {
         temp = iterator.next();
         // Suppression si le budget ne correspond pas
         if (temp.getAccountId() == accountId) {
            iterator.remove();
         }
      }

      try {
         list = dbController
               .getAllDbFinancialTransactionsRelatedToAccount(accountId);

         boolean alreadyExist = false;

         for (DBFinancialTransaction dbItem : list) {

            alreadyExist = false;
            for (FinancialTransaction financialTransaction : result) {

               if (dbItem.getId() == financialTransaction.getId()) {
                  alreadyExist = true;
                  break;
               }
            }

            if (!alreadyExist) {
               result.add(new FinancialTransaction(this, dbItem));
            }

         }

      }
      catch (DatabaseException e) {

      }
      return result;
   }

   /**
    * Retourne la liste de tous les budgets liés au compte dont l'identifiant
    * est donné en paramètre.
    * 
    * @param accountId
    *           - l'identifiant du compte associé.
    * @return La liste des budgets liés au compte donné.
    */
   public LinkedList<Budget> getAllBudgtesRelatedToAccount(int accountId) {
      LinkedList<DBBudget> list;
      LinkedList<Budget> result;

      result = cache.getAll(Budget.class);

      Iterator<Budget> iterator = result.iterator();
      Budget temp;
      while (iterator.hasNext()) {
         temp = iterator.next();
         // Suppression si le compte ne correspond pas
         if (temp.getBindedAccountId() == accountId) {
            iterator.remove();
         }
      }

      try {
         list = dbController.getAllDbBudgetsRelatedToAccount(accountId);

         boolean alreadyExist = false;

         for (DBBudget dbItem : list) {

            alreadyExist = false;
            for (Budget budget : result) {

               if (dbItem.getId() == budget.getId()) {
                  alreadyExist = true;
                  break;
               }
            }

            if (!alreadyExist) {
               result.add(new Budget(this, dbItem));
            }

         }

      }
      catch (DatabaseException e) {

      }
      return result;
   }
   
   /**
    * Retourne la liste des dernières transactions financières.
    * 
    * @param number
    *           - le nombre de transactions à retourner.
    * @return La liste des dernières transactions.
    */
   public LinkedList<FinancialTransaction>
                                    getLatestFinancialTransaction(int number) {
      LinkedList<DBFinancialTransaction> list;
      LinkedList<FinancialTransaction> result = new LinkedList<>();

      try {
         list = dbController.getLatestDbFinancialTransactions(number);
         
         for (DBFinancialTransaction dbItem : list) {
            
            FinancialTransaction transaction = 
                 cache.getReference(FinancialTransaction.class, dbItem.getId());
            
            if (transaction == null) {
               transaction = new FinancialTransaction(this, dbItem);
               cache.putToCache(transaction);
            }
            
            result.add(transaction);

         }
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core",
               "Unable to load the latest financial transactions from database.");
      }
      return result;
   }

   /**
    * Sauvegarde ou met à jour le compte donné dans la base de données.
    * 
    * @param account
    *           - le compte à sauver.
    */
   public void saveAccount(Account account) {
      try {
         dbController.saveToDatabase(account.getDBAccount());

         if (!accounts.contains(account)) {
            accounts.addItem(account);
         } else {
            // Mise à jour des transactions.
            lastTransactions.setChangedAndNotifyObservers();
         }
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the account "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core",
                               "Unable to save the account to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour la catégorie donnée dans la base de données.
    * 
    * @param category
    *           - la catégorie à sauver.
    */
   public void saveCategory(Category category) {
      try {
         dbController.saveToDatabase(category.getDBCategory());

         if (!primaryCategories.contains(category)) {
            primaryCategories.addItem(category);
         } else {
            primaryCategories.setChangedAndNotifyObservers();
         }
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the category "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core",
                               "Unable to save the category to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour la sous-catégorie donnée dans la base de données,
    * ainsi que dans la liste donnée.
    * 
    * @param category
    *           - la sous-catégorie à sauver.
    * @param list
    *           - la liste de sous-catégories à modifier.
    */
   public void saveSubCategory(Category category, CategoryList list) {
      try {
         dbController.saveToDatabase(category.getDBCategory());

         if (!list.contains(category)) {
            list.addItem(category);
         } else {
            list.setChangedAndNotifyObservers();
         }
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the category "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core",
                               "Unable to save the category to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour la récurrence donnée dans la base de données.
    * 
    * @param recurrence
    *           - la récurrence à sauver.
    */
   public void saveRecurrence(Recurrence recurrence) {
      try {
         dbController.saveToDatabase(recurrence.getDBRecurrence());
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the budget "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the budget to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour le budget donné dans la base de données.
    * 
    * @param budget
    *           - le budget à sauver.
    */
   public void saveBudget(Budget budget) {
      try {
         dbController.saveToDatabase(budget.getDBBudget());

         if (!budgets.contains(budget)) {
            budgets.addItem(budget);
         } else {
            alerts.setChangedAndNotifyObservers();
            lastTransactions.setChangedAndNotifyObservers();
         }
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the budget "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the budget to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour le budget à la volée donné dans la base de
    * données.
    * 
    * @param budget
    *           - le budget à sauver.
    */
   public void saveBudgetOnTheFly(BudgetOnTheFly budget) {
      try {
         dbController.saveToDatabase(budget.getDBBudgetOnTheFly());
         
         if (!budgets.contains(budget)) {
            budgets.addItem(budget);
         } else {
            alerts.setChangedAndNotifyObservers();
            lastTransactions.setChangedAndNotifyObservers();
         }

         cache.putToCache(budget);
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the budget "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the budget to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour l'utilisateur donné dans la base de données.
    * 
    * @param user
    *           - l'utilisateur à sauver.
    */
   public void saveUser(User user) {
      try {
         dbController.saveToDatabase(user.getDBUser());

         if (!users.contains(user)) {
            users.addItem(user);
         } else {
            lastTransactions.setChangedAndNotifyObservers();
         }
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the user "
               + "to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the user to database.");
      }
   }

   /**
    * Sauvegarde ou met à jour la transaction donnée dans la base de données.
    * 
    * @param transaction
    *           - la transaction à sauver.
    */
   public void saveFinancialTransaction(FinancialTransaction transaction)
                                             throws AmountUnavailableException{
      try {
         if(transaction.isExpense()) {
            transaction.getAccount().debit(transaction.getAmount());
         } else {
            transaction.getAccount().debit(-transaction.getAmount());
         }
         dbController.saveToDatabase(transaction.getDBFinancialTransaction());
         saveAccount(transaction.getAccount());
         
         // Mise à jour des alertes si dépassement
         Budget budget = transaction.getBudget();
         if (transaction.isExpense() && !budget.isPositive()) {
            alerts.addOrUpdate(new Alert(this, budget));
         }
         
         // Mise à jour de la liste des dernières transactions.
         lastTransactions.addFirst(transaction);
         if(lastTransactions.size() > numberOfTransactions) {
            lastTransactions.removeLast();
         }
         
         cache.putToCache(transaction);
      }
      catch (DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Unable to save the budget "
                        + " to database, because of constraint violation.");
      }
      catch (DatabaseException e) {
         MidasLogs.errors.push("Core", "Unable to save the budget to database.");
      }
   }

   /**
    * Supprime un compte de la base de données.
    * 
    * @param account
    *           - le compte à supprimer
    */
   public void deleteAccount(Account account) throws DatabaseException,
         DatabaseConstraintViolation {
      dbController.deleteDbAccount(account.getId());
      accounts.removeItem(account);
   }

   /**
    * Supprime une récurrence de la base de données.
    * 
    * @param recurrence
    *           - la récurrence à supprimer
    */
   public void deleteRecurrence(Recurrence recurrence) {
      try {
         dbController.deleteDbRecurrence(recurrence.getId());
      }
      catch (DatabaseException | DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core", "Error while deleting a recurrence");
      }
   }

   /**
    * Supprime la récurrence d'un budget de la base de données.
    * 
    * @param budget
    *           - la récurrence à supprimer.
    */
   public void deleteRecurrenceBudget(Budget budget) {
      try {
         int tmpId = budget.getRecurrence().getId();
         budget.getDBBudget().setDbRecurrence(null);
         saveBudget(budget);
         dbController.deleteDbRecurrence(tmpId);
      }
      catch (DatabaseException | DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core",
               "Error while deleting a recurrence of budget");
      }
   }

   /**
    * Supprime la récurrence d'une transaction financière de la base de données.
    * 
    * @param account
    *           - la récurrence d'une transaction financière a supprimer.
    */
   public void deleteRecurrenceFinancialTransaction(
         FinancialTransaction transaction) {
      try {
         int tmpId = transaction.getRecurrence().getId();
         transaction.getDBFinancialTransaction().setDbRecurrence(null);
         saveFinancialTransaction(transaction);
         dbController.deleteDbRecurrence(tmpId);
      }
      catch (AmountUnavailableException e) {
         MidasLogs.errors.push(e.getMessage());
      }
      catch (DatabaseException | DatabaseConstraintViolation e) {
         MidasLogs.errors.push("Core",
               "Error while deleting a recurrence of fianancial transaction");
      }
   }

   /**
    * Désactive un compte, le compte est archivé dans la base de données et
    * n'est plus disponible dans les operations comptables.
    * 
    * @param account
    *           - le compte à désactiver.
    */
   public void deactivateAccount(Account account) {
      account.getDBAccount().setEnabled(false);
      saveAccount(account);
      accounts.removeItem(account);
   }

   /**
    * Désactive un budget, le budget est archivé dans la base de données et
    * n'est plus disponible dans les operations comptables.
    * 
    * @param budget
    *           - le budget à désactiver.
    */
   public void deactivateBudget(Budget budget) {
      budget.getDBBudget().setEnabled(false);
      saveBudget(budget);
      budgets.removeItem(budget);
   }

   /**
    * Désactive un utilisateur, l'utilisateur est archivé dans la base de
    * données et n'est plus disponible dans les operations comptables.
    * 
    * @param user
    *           - l'utilisateur à désactiver.
    */
   public void deactivateUser(User user) {
      user.getDBUser().setEnabled(false);
      saveUser(user);
      users.removeItem(user);
   }

   /**
    * Désactive une catégorie, la catégorie est archivée dans la base de données
    * et n'est plus disponible dans les operations comptables.
    * S'il s'agit d'une catégorie parent, ses enfants sont également activés.
    * 
    * @param category
    *           - la catégorie à désactiver.
    *        list
    *           - La liste contenant la catégorie. (Ignorée si c'est une
    *             catégorie parente)
    */
   public void deactivateCategory(Category category, CategoryList list) {
      category.getDBCategory().setEnabled(false);
      if(category.isChild()) {
         saveSubCategory(category, list);
         list.removeItem(category);
      } else {
         saveCategory(category);

         // On désactive aussi tous les enfants de la catégorie.
         CategoryList children = getChildren(category);
         for(Category c : children.getList()) {
            deactivateCategory(c, list);
         }
         primaryCategories.removeItem(category);
      }
   }
}
