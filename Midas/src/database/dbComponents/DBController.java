package database.dbComponents;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import database.utils.*;

public class DBController {
   
   private DBAccess dbAccess;
   
   public DBController() {
      
      this.dbAccess = new DBAccess("jdbc:sqlite:Midas.sqlite3");
      
   }
   
   public void clearDatabase() throws DatabaseException, DatabaseConstraintViolation {
      String sqlString1;
      String sqlString2;
      String sqlString3;
      String sqlString4;
      String sqlString5;
      String sqlString6;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      PreparedStatement preparedStatement3 = null;
      PreparedStatement preparedStatement4 = null;
      PreparedStatement preparedStatement5 = null;
      PreparedStatement preparedStatement6 = null;
      try {
         sqlString1 = "DELETE FROM FinancialTransaction";
         sqlString2 = "DELETE FROM OnTheFlyBudget";
         sqlString3 = "DELETE FROM Budget";
         sqlString4 = "DELETE FROM User";
         sqlString5 = "DELETE FROM Account";
         sqlString6 = "DELETE FROM Category";
         
         preparedStatement1 = dbAccess.getPreparedStatement(sqlString1);
         preparedStatement2 = dbAccess.getPreparedStatement(sqlString2);
         preparedStatement3 = dbAccess.getPreparedStatement(sqlString3);
         preparedStatement4 = dbAccess.getPreparedStatement(sqlString4);
         preparedStatement5 = dbAccess.getPreparedStatement(sqlString5);
         preparedStatement6 = dbAccess.getPreparedStatement(sqlString6);
         this.delete(preparedStatement1);
         this.delete(preparedStatement2);
         this.delete(preparedStatement3);
         this.delete(preparedStatement4);
         this.delete(preparedStatement5);
         this.delete(preparedStatement6);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement1);
      }
   }
      
   private void insert(PreparedStatement preparedStatement, DBComponent dbComponent) throws DatabaseException, DatabaseConstraintViolation {
      try {
         preparedStatement.execute();
         // r�cup�rer l'identifiant cr�er par la BDD
         ResultSet result = preparedStatement.getGeneratedKeys();
         result.next();
         dbComponent.setId(result.getInt(1));
         
      } catch (SQLException e) {
         if (e.getErrorCode() == 19 ) { // Violation d'une contrainte de la BDD
            DBErrorHandler.constraintViolation();
         } else {
            DBErrorHandler.executionError(e);
         }
      }
   }
   
   private void insertWithoutSettingID(PreparedStatement preparedStatement, DBComponent dbComponent) throws DatabaseException, DatabaseConstraintViolation {
      try {
         preparedStatement.execute();         
      } catch (SQLException e) {
         if (e.getErrorCode() == 19 ) { // Violation d'une contrainte de la BDD
            DBErrorHandler.constraintViolation();
         } else {
            DBErrorHandler.executionError(e);
         }
      }
   }
   
   private void update (PreparedStatement preparedStatement) throws DatabaseException, DatabaseConstraintViolation {
      try {
         preparedStatement.execute();      
      } catch (SQLException e) {
         if (e.getErrorCode() == 19 ) { // Violation d'une contrainte de la BDD
            DBErrorHandler.constraintViolation();
         } else {
            DBErrorHandler.executionError(e);
         }
      }
   }
   
   private void delete (PreparedStatement preparedStatement) throws DatabaseException, DatabaseConstraintViolation {
      try {
         preparedStatement.execute();      
      } catch (SQLException e) {
         if (e.getErrorCode() == 19 ) { // Violation d'une contrainte de la BDD
            DBErrorHandler.constraintViolation();
         } else {
            DBErrorHandler.executionError(e);
         }
      }
   }
   
   private ResultSet select (PreparedStatement preparedStatement) throws DatabaseException {
      ResultSet resultSet = null;
      try {
         resultSet = preparedStatement.executeQuery(); 
      } catch (SQLException e) {
         DBErrorHandler.executionError(e);
      }
      return resultSet;
   }
   
   public DBUser createDBUser() {
      return new DBUser();
   }
   
   public DBUser getUBUser(int id) throws DatabaseException {

      String sqlString = "SELECT Use_Id, firstName, lastName FROM User WHERE Use_ID = ?";
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBUser dbUser = null;
      
      try {
         preparedStatement.setInt(1, id);
         ResultSet result = this.select(preparedStatement);
         
         result.next();
         dbUser = new DBUser();
         dbUser.setId((result.getInt(1)));
         dbUser.setFirstName(result.getString(2));
         dbUser.setLastName(result.getString(3));

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbUser;
   }
   
   public LinkedList<DBUser> getAllDBUsers() throws DatabaseException {

      String sqlString = "SELECT Use_Id, firstName, lastName FROM User";
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBUser dbUser;
      LinkedList<DBUser> dbUsers = new LinkedList<DBUser>();
      
      try {
         ResultSet result = this.select(preparedStatement);
         
         while (result.next()) {
            dbUser = new DBUser();
            dbUser.setId((result.getInt(1)));
            dbUser.setFirstName(result.getString(2));
            dbUser.setLastName(result.getString(3));
            dbUsers.add(dbUser);
         }

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbUsers;
   }
   
   public void saveToDatabase(DBUser dbUser) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         if (dbUser.getId() == null) {
            sqlString = "INSERT INTO User " +
                        "VALUES (null, ?, ?)";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
            preparedStatement.setString(1, dbUser.getFirstName());
            preparedStatement.setString(2, dbUser.getLastName());
            this.insert(preparedStatement, dbUser);
         } else {
            sqlString = "UPDATE User " +
                        "SET firstName = ?, lastName = ? " +
                        "WHERE Use_Id = ?";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
            preparedStatement.setString(1, dbUser.getFirstName());
            preparedStatement.setString(2, dbUser.getLastName());
            preparedStatement.setInt(3, dbUser.getId());
            this.update(preparedStatement);
         }
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
   }
   
   public void deleteDbUser(Integer id) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         sqlString = "DELETE FROM User " +
                     "WHERE Use_Id = ?";
         preparedStatement = dbAccess.getPreparedStatement(sqlString);
         preparedStatement.setInt(1, id);
         this.delete(preparedStatement);
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
   }
   
   public DBAccount createDBAccount() {
      return new DBAccount();
   }
   
   public DBAccount getDbAccount(int id) throws DatabaseException {

      String sqlString = "SELECT Acc_ID, Name, BankName, AccountNumber, Amount, AccountLimit " +
      		             "FROM Account " +
      		             "WHERE Acc_ID = ?";
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBAccount dbAccount = null;
      
      try {
         preparedStatement.setInt(1, id);
         ResultSet result = this.select(preparedStatement);
         
         result.next();
         dbAccount = new DBAccount();
         dbAccount.setId((result.getInt(1)));
         dbAccount.setName((result.getString(2)));
         dbAccount.setNameBank((result.getString(3)));
         dbAccount.setAccountNumber((result.getString(4)));
         dbAccount.setAmount((result.getDouble(5)));
         dbAccount.setOverdraftLimit((result.getDouble(6)));     

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbAccount;
   }
   
   public LinkedList<DBAccount> getAllDBAccounts() throws DatabaseException {

      String sqlString = "SELECT Acc_Id, Name, BankName, AccountNumber, Amount, AccountLimit FROM Account";
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBAccount dbAccount;
      LinkedList<DBAccount> dbAccounts = new LinkedList<DBAccount>();
      
      try {
         ResultSet result = this.select(preparedStatement);
         
         while (result.next()) {
            dbAccount = new DBAccount();
            dbAccount.setId((result.getInt(1)));
            dbAccount.setName((result.getString(2)));
            dbAccount.setNameBank((result.getString(3)));
            dbAccount.setAccountNumber((result.getString(4)));
            dbAccount.setAmount((result.getDouble(5)));
            dbAccount.setOverdraftLimit((result.getDouble(6)));
            dbAccounts.add(dbAccount);
         }

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbAccounts;
   }
   
   public void saveToDatabase(DBAccount dbAccount) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         if (dbAccount.getId() == null) {
            sqlString = "INSERT INTO Account " +
                        "VALUES (null, ?, ?, ?, ?, ?)";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
                        
            preparedStatement.setString(1, dbAccount.getName());
            preparedStatement.setString(2, dbAccount.getNameBank());
            preparedStatement.setString(3, dbAccount.getAccountNumber());
            preparedStatement.setDouble(4, dbAccount.getAmount());
            preparedStatement.setDouble(5, dbAccount.getOverdraftLimit());
            
            this.insert(preparedStatement, dbAccount);
         } else {
            sqlString = "UPDATE Account " +
                        "SET Name = ?, BankName = ?, AccountNumber = ?, Amount = ?, AccountLimit = ? " +
                        "WHERE Acc_Id = ?";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
            preparedStatement.setString(1, dbAccount.getName());
            preparedStatement.setString(2, dbAccount.getNameBank());
            preparedStatement.setString(3, dbAccount.getAccountNumber());
            preparedStatement.setDouble(4, dbAccount.getAmount());
            preparedStatement.setDouble(5, dbAccount.getOverdraftLimit());
            preparedStatement.setInt(7, dbAccount.getId());
            this.update(preparedStatement);
         }
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }   
   }
   
   public void deleteDbAccount(Integer id) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         sqlString = "DELETE FROM Account " +
                     "WHERE Acc_Id = ?";
         preparedStatement = dbAccess.getPreparedStatement(sqlString);
         preparedStatement.setInt(1, id);
         this.delete(preparedStatement);
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
   }
   
   public DBFinancialTransaction createFinancialTransaction() {
       return new DBFinancialTransaction();
    }
   
   public DBFinancialTransaction getDbFinancialTransaction(int id) throws DatabaseException {

	  //Manque certaines infos encore (pas � g�rer � priori d'ici le 23.04.2013 	
      String sqlString = "SELECT Tra_ID, Amount, Date, Reason, Acc_ID FROM FinancialTransaction WHERE Tra_ID = ?"; 
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBFinancialTransaction dbFinancialTransaction = null;
      
      try {
         preparedStatement.setInt(1, id);
         ResultSet result = this.select(preparedStatement);
         
         result.next();
         dbFinancialTransaction = new DBFinancialTransaction();
         dbFinancialTransaction.setId((result.getInt(1)));
         dbFinancialTransaction.setAmount((result.getDouble(2)));
         dbFinancialTransaction.setDate((result.getDate(3)));
         dbFinancialTransaction.setReason((result.getString(4)));
         dbFinancialTransaction.setDbAccount((result.getInt(5)));

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbFinancialTransaction;
   }
   
   public LinkedList<DBFinancialTransaction> getAllDBFinancialTransactions() throws DatabaseException {

	  String sqlString = "SELECT Tra_ID, Amount, Date, Reason, Acc_ID FROM FinancialTransaction"; 
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBFinancialTransaction dbFinancialTransaction = null;
      LinkedList<DBFinancialTransaction> dbFinancialTransactions = new LinkedList<DBFinancialTransaction>();
      
      try {
         ResultSet result = this.select(preparedStatement);
         
         while (result.next()) {
             dbFinancialTransaction = new DBFinancialTransaction();
             dbFinancialTransaction.setId((result.getInt(1)));
             dbFinancialTransaction.setAmount((result.getDouble(2)));
             dbFinancialTransaction.setDate((result.getDate(3)));
             dbFinancialTransaction.setReason((result.getString(4)));
             dbFinancialTransaction.setDbAccount((result.getInt(5)));
             dbFinancialTransactions.add(dbFinancialTransaction);
         }

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbFinancialTransactions;
   }
   
   public void saveToDatabase(DBFinancialTransaction dbFinancialTransaction) throws DatabaseConstraintViolation, DatabaseException {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         if (dbFinancialTransaction.getId() == null) {
            sqlString = "INSERT INTO FinancialTransaction " +
                        "VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
                        
            preparedStatement.setDouble(1, dbFinancialTransaction.getAmount());
            preparedStatement.setDate  (2, new java.sql.Date(dbFinancialTransaction.getDate().getTime()));
            preparedStatement.setString(3, dbFinancialTransaction.getReason());
            if (dbFinancialTransaction.getDbCategory() != null) {
               preparedStatement.setInt(4, dbFinancialTransaction.getDbCategory());
            }
            if (dbFinancialTransaction.getDbBudget() != null) {
               preparedStatement.setInt(5, dbFinancialTransaction.getDbBudget());   
            }
            if (dbFinancialTransaction.getDbAccount() != null) {
               preparedStatement.setInt(6, dbFinancialTransaction.getDbAccount());   
            } else {
               DBErrorHandler.constraintViolation();
            }
            if (dbFinancialTransaction.getDbUser() != null) {
               preparedStatement.setInt(7, dbFinancialTransaction.getDbUser());   
            } else {
               DBErrorHandler.constraintViolation();
            }
            
            this.insert(preparedStatement, dbFinancialTransaction);
         } else {
            sqlString = "UPDATE FinancialTransaction " +
                        "SET Amount = ?, Date = ?, Reason = ?, Cat_ID = ?, Bud_ID = ?, Acc_ID = ?, Use_ID = ?" +
                        "WHERE Tra_ID = ?";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
            
            preparedStatement.setDouble(1, dbFinancialTransaction.getAmount());
            preparedStatement.setDate  (2, new java.sql.Date(dbFinancialTransaction.getDate().getTime()));
            preparedStatement.setString(3, dbFinancialTransaction.getReason());
            preparedStatement.setInt(4, 0);
            preparedStatement.setString(5, "B");
            preparedStatement.setDouble(6, dbFinancialTransaction.getDbAccount());
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, dbFinancialTransaction.getId());
            this.update(preparedStatement);
         }
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }   
   }
   
   public void deleteDbFinancialTransaction(Integer id) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         sqlString = "DELETE FROM FinancialTransaction " +
                     "WHERE Tra_Id = ?";
         preparedStatement = dbAccess.getPreparedStatement(sqlString);
         preparedStatement.setInt(1, id);
         this.delete(preparedStatement);
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
   }
   
   public DBCategory createCategory() {
       return new DBCategory();
    }
   public DBCategory getDbCategory(int id) throws DatabaseException {

	  //Manque certaines infos encore (pas � g�rer � priori d'ici le 23.04.2013 	
      String sqlString = "SELECT Cat_ID, Name, Par_Cat_ID FROM Category WHERE Cat_ID = ?"; 
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBCategory dbCategory = null;
      
      try {
         preparedStatement.setInt(1, id);
         ResultSet result = this.select(preparedStatement);
         
         result.next();
         dbCategory = new DBCategory();
         dbCategory.setId((result.getInt(1)));
         dbCategory.setName((result.getString(2)));
         dbCategory.setParentDBCategory((result.getInt(3)));

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbCategory;
   }
   
   public LinkedList<DBCategory> getAllDBCategory() throws DatabaseException {

	  String sqlString = "SELECT Cat_ID, Name, Par_Cat_ID FROM Category"; 
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBCategory dbCategory = null;
      LinkedList<DBCategory> dbCategories = new LinkedList<DBCategory>();
      
      try {
         ResultSet result = this.select(preparedStatement);
         
         while (result.next()) {
        	 dbCategory = new DBCategory();
             dbCategory.setId((result.getInt(1)));
             dbCategory.setName((result.getString(2)));
             dbCategory.setParentDBCategory((result.getInt(3)));
             dbCategories.add(dbCategory);
         }

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbCategories;
   }  

   public void saveToDatabase(DBCategory dbCategory) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         if (dbCategory.getId() == null) {
            sqlString = "INSERT INTO Category " +
                        "VALUES (null, ?, ?)";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
                        
            preparedStatement.setString(1, dbCategory.getName());
            if (dbCategory.getParentDBCategory() != null) {
               preparedStatement.setInt(2, dbCategory.getParentDBCategory());
            }
            
            this.insert(preparedStatement, dbCategory);
         } else {
            sqlString = "UPDATE Category " +
                        "SET Name = ?, Par_Cat_ID = ? " +
                        "WHERE Cat_ID = ?";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
            preparedStatement.setString(1, dbCategory.getName());
            preparedStatement.setInt(2, dbCategory.getParentDBCategory());
            preparedStatement.setInt(3, dbCategory.getId());
            
            
            this.update(preparedStatement);
         }
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }   
   }
   
   public void deleteDbCategory(Integer id) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         sqlString = "DELETE FROM Category " +
                     "WHERE Cat_ID = ?";
         preparedStatement = dbAccess.getPreparedStatement(sqlString);
         preparedStatement.setInt(1, id);
         this.delete(preparedStatement);
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
   }
   
   public DBBudget createDbBudget() {
      return new DBBudget();
   }
   
   public DBBudget getDbBudget(int id) throws DatabaseException {

      String sqlString = "SELECT Budget.Bud_Id, Name, BudgetLimit, Acc_ID " +
      		             "FROM Budget " +
      		             "WHERE Budget.Bud_Id = ?";
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBBudget dbBudget = null;
      
      try {
         preparedStatement.setInt(1, id);
         ResultSet result = this.select(preparedStatement);
         
         result.next();
         dbBudget = new DBBudget();
         dbBudget.setId(result.getInt(1));
         dbBudget.setName(result.getString(2));  
         dbBudget.setLimit(result.getDouble(3));
         dbBudget.setDbAccount(result.getInt(4));
         
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbBudget;
   }
   
   public LinkedList<DBBudget> getAllDbBudgets() throws DatabaseException {

      String sqlString = "SELECT Budget.Bud_Id, Name, BudgetLimit, Acc_ID " +
                         "FROM Budget";
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBBudget dbBudget;
      LinkedList<DBBudget> dbBudgets = new LinkedList<DBBudget>();
      
      try {
         ResultSet result = this.select(preparedStatement);
         
         while (result.next()) {
            dbBudget = new DBBudget();
            dbBudget.setId(result.getInt(1));
            dbBudget.setName(result.getString(2));
            dbBudget.setLimit(result.getDouble(3));
            dbBudget.setDbAccount(result.getInt(4));
            dbBudgets.add(dbBudget);
         }

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbBudgets;
   }
   
   public void saveToDatabase(DBBudget dbBudget) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         if (dbBudget.getId() == null) {
            sqlString = "INSERT INTO Budget " +
                        "VALUES (null, ?, ?, ?)";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
                        
            preparedStatement.setString(1, dbBudget.getName());
            preparedStatement.setDouble(2, dbBudget.getLimit());
            preparedStatement.setInt(3, dbBudget.getDbAccount());
            this.insert(preparedStatement, dbBudget);
         } else {
            sqlString = "UPDATE Budget " +
                        "SET Name = ?, BudgetLimit = ?, Acc_ID = ? " +
                        "WHERE Bud_Id = ?";
            preparedStatement = dbAccess.getPreparedStatement(sqlString);
            
            preparedStatement.setString(1, dbBudget.getName());
            preparedStatement.setDouble(2, dbBudget.getLimit());
            preparedStatement.setInt(3, dbBudget.getDbAccount());
            preparedStatement.setInt(4, dbBudget.getId());
            this.update(preparedStatement);
         }
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }   
   }
   
   public void deleteDbBudget(Integer id) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString;
      PreparedStatement preparedStatement = null;
      try {
         sqlString = "DELETE FROM Budget " +
                     "WHERE Bud_Id = ?";
         preparedStatement = dbAccess.getPreparedStatement(sqlString);
         preparedStatement.setInt(1, id);
         this.delete(preparedStatement);
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
   }
   
   public DBBudgetOnTheFly createDbBudgetOnTheFly() {
      return new DBBudgetOnTheFly();
   }
   
   public DBBudgetOnTheFly getDbBudgetOnTheFly(int id) throws DatabaseException {

      String sqlString = "SELECT OnTheFlyBudget.Bud_Id, Name, BudgetLimit, Acc_ID, Start, End " +
                         "FROM OnTheFlyBudget JOIN Budget ON Budget.Bud_Id = OnTheFlyBudget.Bud_Id " +
                         "WHERE Budget.Bud_Id = ?";
      
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBBudgetOnTheFly dbBudgetOnTheFly = null;
      
      try {
         preparedStatement.setInt(1, id);
         ResultSet result = this.select(preparedStatement);
         
         result.next();
         dbBudgetOnTheFly = new DBBudgetOnTheFly();
         // Budget
         dbBudgetOnTheFly.setId(result.getInt(1));
         dbBudgetOnTheFly.setName(result.getString(2));  
         dbBudgetOnTheFly.setLimit(result.getDouble(3));
         dbBudgetOnTheFly.setDbAccount(result.getInt(4));
         // OnTheFlyBudget
         dbBudgetOnTheFly.setStart(result.getDate(5));
         dbBudgetOnTheFly.setEnd(result.getDate(6));
         
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbBudgetOnTheFly;
   }
   
   public LinkedList<DBBudgetOnTheFly> getAllDbBudgetOnTheFly() throws DatabaseException {

      String sqlString = "SELECT OnTheFlyBudget.Bud_Id, Name, BudgetLimit, Acc_ID, Start, End " +
      		             "FROM Budget JOIN OnTheFlyBudget ON Budget.Bud_Id = OnTheFlyBudget.Bud_Id ";
      
      PreparedStatement preparedStatement = dbAccess.getPreparedStatement(sqlString);
      DBBudgetOnTheFly dbBudgetOnTheFly;
      LinkedList<DBBudgetOnTheFly> dbBudgetsOnTheFly = new LinkedList<DBBudgetOnTheFly>();
      
      try {
         ResultSet result = this.select(preparedStatement);
         
         while (result.next()) {
            dbBudgetOnTheFly = new DBBudgetOnTheFly();
            // Budget
            dbBudgetOnTheFly.setId(result.getInt(1));
            dbBudgetOnTheFly.setName(result.getString(2));  
            dbBudgetOnTheFly.setLimit(result.getDouble(3));
            dbBudgetOnTheFly.setDbAccount(result.getInt(4));
            // OnTheFlyBudget
            dbBudgetOnTheFly.setStart(result.getDate(5));
            dbBudgetOnTheFly.setEnd(result.getDate(6));
            dbBudgetsOnTheFly.add(dbBudgetOnTheFly);
         }

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
         dbAccess.destroyPreparedStatement(preparedStatement);
      }
      return dbBudgetsOnTheFly;
   }
   
   public void saveToDatabase(DBBudgetOnTheFly getDbBudgetOnTheFly) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString1;
      String sqlString2;
      java.sql.Connection connection = null;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      try {
         
         connection = dbAccess.getConnection();
         connection.setAutoCommit(false);
         
         if (getDbBudgetOnTheFly.getId() == null) {
            sqlString1 = "INSERT INTO Budget " +
                         "VALUES (null, ?, ?, ?) ";
            sqlString2 = "INSERT INTO OnTheFlyBudget " +
                         "VALUES (?, ?, ?); ";
            
            preparedStatement1 = dbAccess.getPreparedStatement(sqlString1);
            preparedStatement2 = dbAccess.getPreparedStatement(sqlString2);
            
            // Budget
            preparedStatement1.setString(1, getDbBudgetOnTheFly.getName());
            preparedStatement1.setDouble(2, getDbBudgetOnTheFly.getLimit());
            preparedStatement1.setInt(3, getDbBudgetOnTheFly.getDbAccount());
            this.insert(preparedStatement1, getDbBudgetOnTheFly);
            // OnTheFlyBudget
            preparedStatement2.setInt(1, getDbBudgetOnTheFly.getId());
            preparedStatement2.setDate(2, new java.sql.Date(getDbBudgetOnTheFly.getStart().getTime()));
            preparedStatement2.setDate(3, new java.sql.Date(getDbBudgetOnTheFly.getEnd().getTime()));
            this.insertWithoutSettingID(preparedStatement2, getDbBudgetOnTheFly);
            
            connection.commit();
         } else {
            sqlString1 = "UPDATE Budget " +
                         "SET Name = ?, BudgetLimit = ?, Acc_ID = ? " +
                         "WHERE Bud_Id = ?";
            sqlString2 = "UPDATE OnTheFlyBudget " +
                         "SET Start = ?, End = ? " +
                         "WHERE Bud_Id = ? ";
            preparedStatement1 = dbAccess.getPreparedStatement(sqlString1);
            preparedStatement2 = dbAccess.getPreparedStatement(sqlString2);

            //Budget
            preparedStatement1.setString(1, getDbBudgetOnTheFly.getName());
            preparedStatement1.setDouble(2, getDbBudgetOnTheFly.getLimit());
            preparedStatement1.setInt(3, getDbBudgetOnTheFly.getDbAccount());
            preparedStatement1.setInt(4, getDbBudgetOnTheFly.getId());
            this.update(preparedStatement1);
            // OnTheFlyBudget
            preparedStatement2.setDate(1, new java.sql.Date(getDbBudgetOnTheFly.getStart().getTime()));
            preparedStatement2.setDate(2, new java.sql.Date(getDbBudgetOnTheFly.getEnd().getTime()));
            preparedStatement2.setInt(3, getDbBudgetOnTheFly.getId());
            this.update(preparedStatement2);
            
            connection.commit();
         }
      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
      // preparedStatement2 sera aussi d�truit avec cette commande
         dbAccess.destroyPreparedStatement(preparedStatement1); 
      }   
   }
   
   public void deleteDbBudgetOnTheFly(Integer id) throws DatabaseException, DatabaseConstraintViolation {
      String sqlString1;
      String sqlString2;
      java.sql.Connection connection = null;
      PreparedStatement preparedStatement1 = null;
      PreparedStatement preparedStatement2 = null;
      try {
         
         connection = dbAccess.getConnection();
         connection.setAutoCommit(false);
         
         sqlString1 = "DELETE FROM OnTheFlyBudget " +
         		       "WHERE Bud_Id = ?";
         sqlString2 = "DELETE FROM Budget " +
                      "WHERE Bud_Id = ?";
         preparedStatement1 = dbAccess.getPreparedStatement(sqlString1);
         preparedStatement2 = dbAccess.getPreparedStatement(sqlString2);

         //Budget
         preparedStatement1.setInt(1, id);
         this.update(preparedStatement1);
         // OnTheFlyBudget
         preparedStatement2.setInt(1, id);
         this.update(preparedStatement2);
         
         connection.commit();

      } catch (SQLException e) {
         DBErrorHandler.resultSetError(e);
      }
      finally {
      // preparedStatement2 sera aussi d�truit avec cette commande
         dbAccess.destroyPreparedStatement(preparedStatement1); 
      }   
   }
   
}