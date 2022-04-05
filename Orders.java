/**
Description:
This program will allow a user to input an order and its details, print all all orders, print priority orders,
and allow a user to search by storeName and print details about orders with that storeName. The program will
start with asking a user to enter a number (1-5) to select which option they would like perform. If they select to
input an order, after inputting the order details, it will ask the user if they would like to enter another order.
The program uses methods main, getChoice, doChoices, addOrder, addOrderDetails, displayAllOrders, displayPriorityOrders,
and findOrders. The program will not quit until the user enters '5' in the selection process. The program will not
validate but uses try/catch method as the DDC will validate the user input. When entering an order, if a user
inputs incorrect orderID, they will be prompted and brought back to the main selection menu. A correct OderID is 
'SOP-XXXXXX' where the X is a digit. If the user enters a correct orderID, then the user cannot exit the program until 
they enter correct order details. The order details are storeName (cannot be blank and cannot be more than 48 characters), 
contactName (cannot be blank and characters must be greater than 5 and less than 38 with one space seperating them), 
orderCost (must be greater than 0), orderStatus (either, “Pending Approval”, “In Processing”, “Ready for Shipment”, or “Shipped”), 
and priority(either "yes" or "no"). With the three printing processes, the program will call the DDC to create the 
string and then the program will print the string that is passed through. The program will track the number of orders by using a 
static variable that counts the number
of variables in the DDC.
*/
import javax.swing.JOptionPane;
public class Orders{
   public static void main(String[] args) {
   //int used to set the length for the Order array
      final int MAX_NUM_ORDERS = 32;
      //array used to hold all the orders.
      Order[] allOrders = new Order[MAX_NUM_ORDERS];
      int menuChoices = getChoice();
      doChoices(menuChoices, allOrders);
   }
     /**
   Method Purpose: To hold the structure for the choice that the user input and carry out the function that the user
   entered. It will use a switch/case function to call the method that the user wants to do. It will not allow
   the user to exit until they enter 5 when prompted. 
   Parameter: integer menuChoices, Order allOrders[]
   Return Type: void.
   */
      public static void doChoices(int menuChoices, Order allOrders[]){
      //loop used to exit if the user inputs 5.
      while (menuChoices != 5) {
         //switch case method used to call the four functions depending on the user input.
         switch(menuChoices) {
            case 1:
            //if statement make sure there is enough room in the array.
               if(Order.getNumberOfOrders() < allOrders.length)
               {
                  try{
                     allOrders[Order.getNumberOfOrders()]= addOrder(allOrders); 
                  }
                  catch(IllegalArgumentException e){
                     JOptionPane.showMessageDialog(null, "The order could not be completed. "+e.getMessage());
                  }
                  
               }
               else { JOptionPane.showMessageDialog(null, "The order list is full. No more orders can be added.");
               }
               break;
              
            case 2:
               displayAllOrders(allOrders);
               break;
                
            case 3:
               displayPriorityOrders(allOrders);
               break;
            case 4:
               findOrders(allOrders);
               break;
               
            default:
               throw new RuntimeException("Unknnown error in menu choice");
         }
         menuChoices = getChoice();
      }
   }
    /**
   Method Purpose: To create an order with a valid orderID. If an invalid orderID is entered, the program will revert
   back to the starting menu.
   Parameter: Order[] allOrders.
   Return Type: Order object.
   */
   public static Order addOrder(Order[] allOrders){
      //this is set up so if the user enters an invalid orderID, it will return to where the method was called in doChoices.
      Order aNewOrder = new Order(JOptionPane.showInputDialog("Enter the Order ID: "));
      int point = Order.getNumberOfOrders();
      addOrderDetails(allOrders, point, aNewOrder);
      return aNewOrder;
         }
    /**
   Method Purpose: To add the order details when an Order object has been created with an OrderID. The program will
   not exit until correct details have been entered.
   Parameter: Order[] allOrders, integer point, Order aNewOrder.
   Return Type: void.
   */
   public static void addOrderDetails(Order[] allOrders, int point, Order aNewOrder){
      //tracker is used to exit the loop if a correct input has been entered.
      boolean tracker = false;
      //the user cannot exit the program until they enter a valid input for each order detail.
      do{
         try {
            aNewOrder.setOrderCost(Double.parseDouble(JOptionPane.showInputDialog(
            "Enter the order cost: ")));
            tracker = true;                       
         }
         catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "You didn't enter a number");           
         }
         catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
      
      }while(!tracker);
      
      tracker = false;
      
      do{
         try {
            aNewOrder.setStoreName(JOptionPane.showInputDialog("Enter the store name: "));
            tracker = true;
         }
         catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
      }while(!tracker);
      
      tracker = false;
      
      do{
         try {
            aNewOrder.setContactName(JOptionPane.showInputDialog("Enter the contact name: "));
            tracker = true;
         }
         catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
      }while(!tracker);
      
      tracker = false;
      
      do{
         try {
            aNewOrder.setOrderStatus(JOptionPane.showInputDialog("Enter the order status: "));
            tracker = true;
         }
         catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
      }while(!tracker);
      
      tracker = false;
        
      do{
         try {
            aNewOrder.setPriority(JOptionPane.showInputDialog("Enter priority, either 'yes' or 'no': "));
            tracker = true;
         }
         catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
         }
      }while(!tracker);
   }
    /**
   Method Purpose: To display all the orders entered so far. If no orders have been entered, the program will display
   an error message.
   Parameter: Order[] allOrders
   Return Type: void
   */
   public static void displayAllOrders(Order[] allOrders){
      //string used to hold the information of all orders entered.
      String printThat = "";
      //used to see if there are any orders entered.
      if(Order.getNumberOfOrders() > 0){
         
         for(int x=0;x < Order.getNumberOfOrders(); x++){
            printThat += allOrders[x].toStringAllOrders();
         }
         //the method must add the static method to the string because it couldn't be completed in the DDC.
          printThat+="\nAverage cost of all orders: " + Order.getAverage();
         JOptionPane.showMessageDialog(null, printThat);
      }
      else {
         JOptionPane.showMessageDialog(null, "There are no orders to print");
      }
   }
     /**
   Method Purpose: To display all the priority orders. If no orders have been made, an error message will display.
   If no priority orders exist, an error message will display.
   Parameter: Order[] allOrders.
   Return Type: void
   */
   public static void displayPriorityOrders(Order[] allOrders){
      String printThat = "";
      if(Order.getNumberOfOrders() > 0){
         
         int x;
         int count=0;
         for(x=0;x<Order.getNumberOfOrders();x++){
            if(allOrders[x].getPriority().equalsIgnoreCase("yes")){
               printThat+=allOrders[x].toStringPriorityOrders();
               count++;
            }
            
            x++;
         }
        
         
         if(count>0){
            JOptionPane.showMessageDialog(null, printThat);
         }
         else {
            JOptionPane.showMessageDialog(null, "No Priority Orders exist");
         }
      }
      else{
         JOptionPane.showMessageDialog(null, "There are no orders to print.");
      }
   }
     /**
   Method Purpose: To allow th user to enter a storeName for the program to search for. The program will validate the
   input for the search keyword in the same the the storeName is validated. If no orders were found with that search
   name, it print an error message. If there is one found, it will print the string that was given by toStringPriorityOrders
   Parameter: Order[] allOrders
   Return Type: void
   */
      public static void findOrders(Order[] allOrders){
      if(Order.getNumberOfOrders() > 0){
         String printThat = "";
         int x;
         boolean validator = false;
         String searchItem;
         do{
            searchItem = JOptionPane.showInputDialog("Enter the store name you would like to search: ");
            if(searchItem==null||searchItem.equals("")){
               JOptionPane.showMessageDialog(null, "Search Item cannot be blank.");
            }
            else if (searchItem.length() > Order.getMaxStoreName()){
               JOptionPane.showMessageDialog(null, "Store name cannot be more than 48 characters including spaces");
            }
            else{
               validator = true;
            }
         }while(!validator);
         int count=0;
         for(x=0;x<Order.getNumberOfOrders();x++){
            if(allOrders[x].getStoreName().equals(searchItem)){
               printThat+=allOrders[x].toStringFindOrders();
               count++;
            }
            x++;
         }
         if(count>0){
            JOptionPane.showMessageDialog(null, printThat);
         }
         else {
            JOptionPane.showMessageDialog(null, "No Orders with that store name were found.");
         }
      }
      else{
         JOptionPane.showMessageDialog(null, "There are no orders to print.");
      }
   }
    /**
   Method Purpose: To allow a user to enter a selection of 1-5 to see what the user wants to do, either add order,
   display all orders, display priority orders, find order, or quit the program. 
   Parameter: none.
   Return Type: int
   */
   public static int getChoice(){
      int choice;
      do{
         try {
            choice = Integer.parseInt(JOptionPane.showInputDialog(
               "Enter your selection:"
               + "\n(1) Add Order"
               + "\n(2) Display All Orders"
               + "\n(3) Display Priority Orders"
               + "\n(4) Find Order"
               + "\n(5) Close Program"
               ));
               
         }
         //if the user entered anything but a digit, choice will = 0.
         catch (NumberFormatException e) {
            choice = 0;
         }
         //user will not continue until they enter a digit 1-5.
         if (choice< 0 || choice > 5) {
            JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a valid menu option.");
         }
      }while (choice< 1 || choice > 5);
      return choice;
   }
}
