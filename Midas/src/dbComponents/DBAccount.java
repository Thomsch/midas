package dbComponents;

public class DBAccount extends DBComponent {

   String name;
   String nameBank;
   String accountNumber;
   String description;
   Double amount;
   Double overdraftLimit;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getNameBank() {
      return nameBank;
   }

   public void setNameBank(String nameBank) {
      this.nameBank = nameBank;
   }

   public String getAccountNumber() {
      return accountNumber;
   }

   public void setAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Double getAmount() {
      return amount;
   }

   public void setAmount(Double amount) {
      this.amount = amount;
   }

   public Double getoverdraftLimit() {
      return overdraftLimit;
   }

   public void setoverdraftLimit(Double overdraftLimit) {
      this.overdraftLimit = overdraftLimit;
   }
   
   
}
