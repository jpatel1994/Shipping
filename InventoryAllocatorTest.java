import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;

/**
* Class which tests the InventoryAllocator code. This tests uses JUnit library.
*/
public class InventoryAllocatorTest 
{

   /**
   * This test case will check if the inputs are case insensitive or not
   */
   @Test
   public void testToCheckCaseSensitivity() 
   {
       Map<String, List<Inventory>> inventoryList = new HashMap<>();

       InventoryAllocator inventory = new InventoryAllocator();

       inventory.addInventory("apple", "OWD", 5, inventoryList);
       inventory.addInventory("APPLE", "dm", 5, inventoryList);

       // We will use linked hash map as the input for each of the order placed to maintain the order of the input
       Map<String, Integer> PlacedOrder = new HashMap<String, Integer>();
       PlacedOrder.put("aPple", 10);

       Map<String, List<Product>> cheapestShipmentInventory = inventory.CheapestShipment(inventoryList, PlacedOrder);

       assertTrue("Result can't be null or empty in the inventory",(cheapestShipmentInventory != null && cheapestShipmentInventory.size() > 0));

       for (Entry<String, List<Product>> entry : cheapestShipmentInventory.entrySet()) 
       {
           String inventoryName = entry.getKey();
           List<Product> productList = entry.getValue();
           if (inventoryName.equals("owd")) 
           {
               for (Product product : productList) 
               {
                   if (product.getName().equals("apple")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test failed as amount of apples that were processed from the owd stock are not what was to happen.",count == 5);
                   } 
                   
               }
           } 
           else if (inventoryName.equals("dm")) 
           {
               for (Product product : productList) 
               {
                   if (product.getName().equals("apple")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test failed as amount of apples that were processed from the dm stock are not what was to happen.",count == 5);
                   } 
               }
           } 
          
       }

   }

   /**
   * Test to check only one product and one inventory. Do not need this test
   */
   @Test
   public void testToCheckOneItemAndOneInventory() 
   {
       Map<String, List<Inventory>> inventoryList = new HashMap<>();

       InventoryAllocator inventory = new InventoryAllocator();

       inventory.addInventory("aPPle", "owd", 1, inventoryList);

       // We will use linked hash map as the input for each of the order placed
       // to maintain the order of the input
       Map<String, Integer> inputOrderPlaced = new HashMap<String, Integer>();
       inputOrderPlaced.put("apple", 1);

       Map<String, List<Product>> cheapestShipmentResult = inventory
               .CheapestShipment(inventoryList, inputOrderPlaced);

       assertTrue("Result can't be null or empty in the inventory",(cheapestShipmentResult != null && cheapestShipmentResult.size() > 0));

       for (Entry<String, List<Product>> entry : cheapestShipmentResult.entrySet()) 
       {
           String inventoryName = entry.getKey();
           List<Product> productList = entry.getValue();
           if (inventoryName.equals("owd")) 
           {
               for (Product product : productList) 
               {
                   if (product.getName().equals("apple")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test failed as amount of apples that were processed from the owd stock are not what was to happen",count == 1);
                   }
               }
           } 
       }
   }

   /**
   * Test to check zero orders being placed
   */
   @Test
   public void testToCheckZeroOrdersRequestFromInventory() 
   {
       Map<String, List<Inventory>> inventoryList = new HashMap<>();

       InventoryAllocator inventoryAllocator = new InventoryAllocator();

       inventoryAllocator.addInventory("aPPle", "owd", 1, inventoryList);

       // We will use linked hash map as the input for each of the order placed
       // to maintain the order of the input
       Map<String, Integer> inputOrderPlaced = new HashMap<String, Integer>();
       inputOrderPlaced.put("apple", 0);

       Map<String, List<Product>> Result = inventoryAllocator.CheapestShipment(inventoryList, inputOrderPlaced);
       assertTrue("Result can't be null or empty in the inventory",(Result != null && Result.isEmpty()));
   }

   /**
   * Test to check invalid or non existent orders being placed
   */
   @Test
   public void InvalidOrNonExistentOrdersTest() 
   {
       Map<String, List<Inventory>> inventoryList = new HashMap<>();

       InventoryAllocator inventory = new InventoryAllocator();

       inventory.addInventory("apple", "owd", 1, inventoryList);

       Map<String, Integer> inputOrderPlaced = new HashMap<String, Integer>();
       inputOrderPlaced.put("owd", 0);
       inputOrderPlaced.put("banana", 0);

       Map<String, List<Product>> Result = inventory.CheapestShipment(inventoryList, inputOrderPlaced);

       //declare an expected boolean condition in a program
       assertTrue("Result can't be null or empty in the inventory",(Result != null && Result.isEmpty()));

   }

   /**
   * Test to check when there are zero products in an inventory but the input
   * order placed is requesting more products to be shipped
   */
   @Test
   public void ZeroProductsInAnInventoryTest() 
   {
       Map<String, List<Inventory>> inventoryList = new HashMap<>();

       InventoryAllocator inventory = new InventoryAllocator();

       inventory.addInventory("apple", "owd", 0, inventoryList);

       Map<String, Integer> inputOrderPlaced = new HashMap<String, Integer>();
       inputOrderPlaced.put("apple", 1);

       Map<String, List<Product>> Result = inventory
               .CheapestShipment(inventoryList, inputOrderPlaced);

       //declare an expected boolean condition in a program
       assertTrue("Result can't be null or empty in the inventory", (Result != null && Result.isEmpty()));
   }

   /**
   * Test to check if adding the same product to the same inventory updates
   * the existing inventory count.
   */
   @Test
   public void AddingSameProductTest() 
   {

       InventoryAllocator inventorypro = new InventoryAllocator();

       Map<String, List<Inventory>> products = new HashMap<>();
     
       inventorypro.addInventory("apple", "owd", 1, products);
       inventorypro.addInventory("apple", "owd", 2, products);

       Map<String, Integer> inputOrderPlaced = new HashMap<String, Integer>();
       inputOrderPlaced.put("apple", 1);

       Map<String, List<Product>> Result = inventorypro.CheapestShipment(products, inputOrderPlaced);
       
       //declare an expected boolean condition in a program
       assertTrue("Result can't be null or empty in the inventory", (Result != null && Result.size() > 0));

 
       for (Entry<String, List<Inventory>> entry : products.entrySet()) 
       {
           String productName = entry.getKey();
           List<Inventory> inventoryList = entry.getValue();

           if (productName.equals("apple")) {
               for (Inventory inventory : inventoryList) 
               {
                   if (inventory.getName().equals("owd")) 
                   {
                       int count = inventory.getItemCount();
                       assertTrue("Test failed as amount of apples that were processed from the owd stock are not what was to happen.",
                               count == 2);
                   } 
               }
           }

       }

   }

   /**
   * Test to check base conditions null input orders
   */
   @Test
   public void NullInputOrderTest() 
   {
       Map<String, List<Inventory>> inventoryList = new HashMap<>();

       InventoryAllocator inventory = new InventoryAllocator();

       
       inventory.addInventory("apple", "owd", 5, inventoryList);
       inventory.addInventory("orange", "owd", 10, inventoryList);

       inventory.addInventory("banana", "dm", 5, inventoryList);
       inventory.addInventory("orange", "dm", 10, inventoryList);

      
       Map<String, Integer> inputOrder = null;

       Map<String, List<Product>> Result = inventory.CheapestShipment(inventoryList, inputOrder);
       
       //declare an expected boolean condition in a program
       assertTrue("Result can't be null or empty in the inventory",(Result != null && Result.isEmpty()));
   }

   /**
   * Test to check base condition when inventory is empty.
   */
   @Test
   public void testNullInventory() 
   {

       InventoryAllocator inventoryAllocator = new InventoryAllocator();

       Map<String, List<Inventory>> inventoryList = null;

       // We will use linked hash map as the input for each of the order placed
       // to maintain the order of the input
       Map<String, Integer> inputOrderPlaced = new LinkedHashMap<String, Integer>();
       inputOrderPlaced.put("apple", 5);
       inputOrderPlaced.put("banana", 5);
       inputOrderPlaced.put("orange", 5);

       Map<String, List<Product>> cheapestShipmentInventoryToProductResultMap = inventoryAllocator
               .CheapestShipment(inventoryList, inputOrderPlaced);

       assertTrue("Result can't be null or empty in the inventory",
               (cheapestShipmentInventoryToProductResultMap != null
                       && cheapestShipmentInventoryToProductResultMap.isEmpty()));
   }

   /**
   * Test to check order being shipped from different inventories and also
   * check both the leftout products in the inventory and orders shipped from
   * inventories.
   */
   @Test
   public void OrderShippedTest() 
   {
       Map<String, List<Inventory>> productToInventoryListMap = new HashMap<>();

       InventoryAllocator inventorypro = new InventoryAllocator();

       
       inventorypro.addInventory("apple", "owd", 5, productToInventoryListMap);
       inventorypro.addInventory("orange", "owd", 10, productToInventoryListMap);

       inventorypro.addInventory("banana", "dm", 5, productToInventoryListMap);
       inventorypro.addInventory("orange", "dm", 10, productToInventoryListMap);

      
       Map<String, Integer> inputOrder = new LinkedHashMap<String, Integer>();
       inputOrder.put("apple", 5);
       inputOrder.put("banana", 5);
       inputOrder.put("orange", 15);

       Map<String, List<Product>> Result = inventorypro.CheapestShipment(productToInventoryListMap, inputOrder);

       //declare an expected boolean condition in a program
       assertTrue("Result can't be null or empty in the inventory", (Result != null && Result.size() > 1));

       // Test left out inventory products count
       for (Entry<String, List<Inventory>> entry : productToInventoryListMap.entrySet()) 
       {
           String productName = entry.getKey();
           List<Inventory> inventoryList = entry.getValue();

           if (productName.equals("apple")) {
               for (Inventory inventory : inventoryList) 
               {
                   if (inventory.getName().equals("owd")) 
                   {
                       int count = inventory.getItemCount();
                       //declare an expected boolean condition in a program
                       assertTrue("Test failed as amount of apples that were processed from the dm stock are not what was to happen.",
                               count == 0);
                   } 
               }
           } 
           else if (productName.equals("banana")) 
           {
               for (Inventory inventory : inventoryList) 
               {
                   if (inventory.getName().equals("dm")) 
                   {
                       int count = inventory.getItemCount();
                       //declare an expected boolean condition in a program
                       assertTrue("Test failed as amount of banana that were processed from the owd stock are not what was to happen.",
                               count == 0);
                   } 
               }
           } 
           else if (productName.equals("orange")) 
           {
               for (Inventory inventory : inventoryList) 
               {
                   if (inventory.getName().equals("owd")) 
                   {
                       int count = inventory.getItemCount();
                       assertTrue("Test failed as amount of oranges that were processed from the owd stock are not what was to happen.",
                               count == 0);
                   } 
                   else if (inventory.getName().equals("dm")) 
                   {
                       int count = inventory.getItemCount();
                       assertTrue("Test failed as amount of oranges that were processed from the dm stock are not what was to happen.",
                               count == 5);
                   } 
               }
           }

       }

       // shipment test result
       for (Entry<String, List<Product>> entry : Result.entrySet()) 
       {
           String inventoryName = entry.getKey();
           List<Product> productList = entry.getValue();
           if (inventoryName.equals("owd")) 
           {
               for (Product product : productList) 
               {
                   if (product.getName().equals("apple")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test failed as amount of apples that were processed from the owd stock are not what was to happen.",
                               count == 5);
                   } 
                   else if (product.getName().equals("orange")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test failed as amount of oranges that were processed from the owd stock are not what was to happen.",
                               count == 10);
                   }
               }
           } 
           else if (inventoryName.equals("dm")) 
           {
               for (Product product : productList) 
               {
                   if (product.getName().equals("banana")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test failed as amount of banana that were processed from the dm stock are not what was to happen.",
                               count == 5);
                   } 
                   else if (product.getName().equals("orange")) 
                   {
                       int count = product.getCount();
                       assertTrue("Test Failed as the count of oranges processed from dm inventory is not as expected.",
                               count == 5);
                   }
               }
           }
       }
   }
}
