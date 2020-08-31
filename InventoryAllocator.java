import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class InventoryAllocator 
{

  // Method which will process input order inline
   public Map<String, List<Product>> CheapestShipment(Map<String, List<Inventory>> InventoryList, Map<String, Integer> CountOrder) 
   {

       Map<String, List<Product>> Result = new LinkedHashMap<>();

       if (InventoryList == null || InventoryList.isEmpty() || CountOrder == null || CountOrder.isEmpty()) 
       {
           return Result;
       }

       for (Entry<String, Integer> entrySet : CountOrder.entrySet()) 
       {

           String productName = entrySet.getKey();
           Integer quantity = entrySet.getValue();

           
           if (productName == "" || quantity == 0 || quantity == 0) 
           {
               System.out.println( "All test passed");
               continue;
           }

           List<Inventory> InventoryproductList = InventoryList.get(productName.toLowerCase());

           if (InventoryproductList != null) 
           {
               for (Inventory inventory : InventoryproductList) 
               {
                   if (inventory.getItemCount() < 1) 
                   {
                       continue;
                   }

                   List<Product> ProductList = Result.get(inventory.getName().toLowerCase());

                   if (ProductList == null) 
                   {
                	   ProductList = new LinkedList<Product>();
                	   Result .put(inventory.getName().toLowerCase(),ProductList);
                   }

                   if (quantity > inventory.getItemCount()) 
                   {
                       quantity -= inventory.getItemCount();
                       ProductList.add(new Product(productName.toLowerCase(), inventory.getItemCount()));
                       inventory.setItemCount(0);
                   } 
                   else 
                   {
                       inventory.setItemCount(inventory.getItemCount() - quantity);
                       ProductList.add(new Product(productName.toLowerCase(), quantity));
                       quantity = 0;
                   }
               }
           }
       }
       return Result ;
   }

   // Add inventory to product map
   public void addInventory(String productName, String inventoryName, int count,Map<String, List<Inventory>> Inventory) 
   {

       if (productName == null || productName.trim().isEmpty() || inventoryName == null
               || inventoryName.trim().isEmpty()) {
           return;
       }

       if (Inventory == null) 
       {
          Inventory = new HashMap<String, List<Inventory>>();
       }

     
       List<Inventory> inventoryList = Inventory.get(productName.toLowerCase());
       if (inventoryList == null) 
       {
           inventoryList = new LinkedList<Inventory>();
           Inventory.put(productName.toLowerCase(), inventoryList);
       }
       Inventory inventory = this.getInventoryFromList(inventoryName, inventoryList);
       if (inventory != null) {
           inventory.setItemCount(inventory.getItemCount() + count);
       }
   }

   // Check to to see if any inventory are existing in the list, if any exist then add new inventory to 0 item as 
   // a count
   public Inventory getInventoryFromList(String inventoryName, List<Inventory> inventoryList) 
   {
       Inventory result = null;
       for (Inventory inventory : inventoryList) 
       {
           if (inventory.getName().equalsIgnoreCase(inventoryName)) 
           {
               result = inventory;
               break;
           }
       }
       if (result == null) 
       {
           result = new Inventory(inventoryName.toLowerCase(), 0);
           inventoryList.add(result);
       }
       return result;
   }
}