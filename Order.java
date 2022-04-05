/**
Name: Joseph Roesch
Date: 03/303/2019
Course/Section: IT206.003
Assignment 4
Description:
This program will allow a user to input an order and its details, print all all orders, print priority orders,
and allow a user to search by storeName and print details about orders with that storeName. The program will
start with asking a user to enter a number to select which option they would like perform. If they select to
input an order, after inputting the order details, it will ask the user if they would like to enter another order.
The program uses methods main, getChoice, doChoices, addOrder, addOrderDetails, displayAllOrders, displayPriorityOrders,
and findOrders. The program will not quit until the user enters '5' in the selection process. The program will not
validate but uses try/catch method as the DDC will validate the user input. When entering an order, if a user
inputs incorrect orderID, they will be prompted and brought back to the main selection menu. If the user enters
a correct orderID, then the user cannot exit the program until they enter correct order details. With the three
printing processes, the program will call the DDC to create the string and then the program will print the string
that is passed through. The program will track the number of orders by using a static variable that counts the number
of variables in the DDC.
*/
public class Order{
   private String orderID;
   private double orderCost;
   private String storeName;
   private String contactName;
   private String orderStatus;
   private String priority;
   //NumberofOrders used to count the number of orders.
   private static int numberOfOrders = 0;
   //totalCost used to hold the totalCost and be divided by the number of orders for the avg
   private static double totalCost = 0;
   //average static because it is not a trait of a single Order object
   private static double average;
   //all finals are used for validation and are private because they won't be used outside the DDC and i don't want users to see the validation variables.
   private static final int MAX_STORE_NAME = 48;
   private static final int VALIDATE_NUM_ORDERS = 0;
   private static final int VALIDATE_NAME_MIN = 6;
   private static final int VALIDATE_NAME_MAX = 38;
   private static final int VALID_ORDER_ID = 11;
   
   private static final String VALIDATE_ORDER_ID = "SOP-";
   //this accessor is used to validate the input for the findOrder method.
   public static int getMaxStoreName(){ return MAX_STORE_NAME;}
   //orderStatus can be one of four strings so I used an array to hold the validation.
   private static final String VALIDATE_ORDER_STATUS[] = {"pending approval", "in processing", 
   "ready for shipment", "shipped"};
   //this is the only constructor because having an orderID is the only way to make an Order object.
   public Order(String orderID){
      if(orderID == null||orderID.equals("")){
         throw new IllegalArgumentException("Order ID cannot be blank");
      }
      if(orderID.length() != VALID_ORDER_ID){
         throw new IllegalArgumentException("Order ID must be 'SOP-XXXXXXX' where X is a digit");
      }
      String vali = orderID.substring(0, 4);
      if (!vali.equals(VALIDATE_ORDER_ID)){
         throw new IllegalArgumentException("Order ID must start with 'SOP-'");
      }
      String valida = orderID.substring(4, 11);
      int x = 0;
      while(x < valida.length()) {
         if (!Character.isDigit(valida.charAt(x))) {
            throw new IllegalArgumentException("Order ID must be 'SOP-XXXXXXX' where X is a digit");
         }
         ++x;
      }
      this.orderID = orderID;
      numberOfOrders++;
   }
   //mutator to validate orderCost
   public void setOrderCost(double orderCost) {
      if(orderCost > VALIDATE_NUM_ORDERS) {
         this.orderCost = orderCost;
         setTotalCost(this.orderCost);
      }
      else {
         throw new IllegalArgumentException("Order cost must be a number greater than 0.");
      }
   }
   public void setStoreName(String storeName) {
      if(storeName==null||storeName.equals("")){
         throw new IllegalArgumentException("Store name cannot be blank");
      }
      else if (storeName.length() > MAX_STORE_NAME){
         throw new IllegalArgumentException("Store name cannot be more than 48 characters including spaces");
      }
      else{
      this.storeName = storeName;
      }
   }
   //mutator used for contactName
   public void setContactName(String contactName){
      contactName = contactName.trim();
      if(contactName.length() < VALIDATE_NAME_MIN || contactName.length() > VALIDATE_NAME_MAX) {
         throw new IllegalArgumentException("First and Last name must be greater than 5 letters and less than 38 letters separated with a space");
      }
      //this will count the times going through the while loop
      int counter = 0;
      //validator to see if the character is a digit or not.
      boolean checker = false;
      //loop will end if checker is true or it goes through the whole string. If contactName is correct, counter will reamin false.
      while(counter < contactName.length() && !checker){
         checker = Character.isDigit(contactName.charAt(counter));
         counter++;
      }
      if (checker) {
         throw new IllegalArgumentException("There can be no numbers in the contact name");
      }
      //counter will be used to count the number of spaces.
      counter = 0;
      //i will serve as the counter for the loop
      int i = 0;
      //char will serve as the holder for the specific char in the string to validate
      char check;
      //loop will exit if more than 1 space is found or it goes through the whole string.
      while(i < contactName.length() && counter<2) {
         check = contactName.charAt(i);
         if (check==' '){
            counter++;
         }
         i++;
      }
      if (counter!=1) {
         throw new IllegalArgumentException("There can only be one space separating the first and last name");
      }
      else {
         this.contactName = contactName;
      }
   }
   //mutator or Orderstatus.
   public void setOrderStatus(String orderStatus){
      //x will serve as the counter for the loop and as the position in the validation array.
      int x = 0;
      //vali will be true if the validation is correct
      boolean vali = false;
      //loop will exit if vali is true or it goes through the validation array.
      while(x < VALIDATE_ORDER_STATUS.length && !vali){
         if(orderStatus.equalsIgnoreCase(VALIDATE_ORDER_STATUS[x])) {
            vali = true;
         }
         else{
         ++x;
         }
      }
      if (!vali){
         throw new IllegalArgumentException("Order Status must be either, “Pending Approval”, “In Processing”, “Ready for Shipment”, or “Shipped”.");
      }
      else {
         this.orderStatus = orderStatus;
      }
   }
   //mutator for priority.
   public void setPriority( String priority) {
      if (priority.equals("yes") || priority.equals("no")){
         this.priority = priority;
      }
      else {
         throw new IllegalArgumentException("Priority must be 'yes' or 'no'");
      }
      
   }
   //mutator for total cost. will be called everytime a total cost is validated and entered. A user cannot enter more
   //than one orderCost for an order. It is private so that the user cannot access it.
   private void setTotalCost(double orderCost){
      totalCost = this.orderCost + totalCost;
      setAverage();
   }
   //mutator for average. It will be called by the implementation so it is public. Even though the numberOfOrders and
   //total cost are managed by the DDC, it still has an if statement to make sure it isn't dividing by 0.
   public static void setAverage(){
      if(numberOfOrders > 0){
      average = totalCost/numberOfOrders;}
   }
   //mutator for numberOfOrders. Private because only the DDC will handle it. It'll be called everytime an Order object is created.
   private static void setNumberOfOrders(){
      numberOfOrders++;
   }
   //accessors for variables.
   public String getOrderID() { return this.orderID;}
   public double getOrderCost() { return this.orderCost;}
   public String getStoreName() { return this.storeName;}
   public String getContactName() { return this.contactName;}
   public String getOrderStatus() { return this.orderStatus;}
   public String getPriority() { return this.priority;}
   //public because the numberOfOrders are important to use in the implementation.
   public static int getNumberOfOrders() { return numberOfOrders;}
   //public because the displayAllOrders needs to get this to print because the DDC using it in toStringDisplayAllOrders would not work.
   public static double getAverage() { return average;}
   //private because only the DDC wil use this function.
   private static double getTotalCost() { return totalCost;}
   //special purpose method to return a string with all the needed information for the displayAllOrders method.
   public String toStringAllOrders(){
      return "Order ID: " + this.getOrderID()
         +"\nOrder Cost: " + this.getOrderCost() 
         +"\nStore Name: " + this.getStoreName() 
         +"\nContact Name: " + this.getContactName()
         +"\nOrder Status: " + this.getOrderStatus()
         +"\nPriority? : " + this.getPriority()
         
         +"\n\n"
        ;
   }
   //special purpose method to return a string with all the needed information for the displayPriorityOrders method.
   public String toStringPriorityOrders(){
      return 
         "\nOrder Cost: " + this.getOrderCost() 
         +"\nStore Name: " + this.getStoreName() 
         +"\nContact Name: " + this.getContactName()
         
         
         
         +"\n\n"
        ;
   }
   //special purpose method to return a string with all the needed information for the findOrders methods.
   public String toStringFindOrders(){
      return "Order ID: " + this.getOrderID()
         +"\nOrder Cost: " + this.getOrderCost() 
          
         +"\nContact Name: " + this.getContactName()
         +"\nOrder Status: " + this.getOrderStatus()
         +"\nPriority? : " + this.getPriority()
         
         +"\n\n"
        ;
   }
}