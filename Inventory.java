
public class Inventory 
{
	private String name;
	private int itemCount;

	   public Inventory(String name, int count) 
	   {
	       this.name = name;
	       this.itemCount = count;
	   }

	   public String getName() 
	   {
	       return name;
	   }

	   public void setName(String name) 
	   {
	       this.name = name;
	   }

	   public int getItemCount() 
	   {
	       return itemCount;
	   }

	   public void setItemCount(int itemCount) 
	   {
	       this.itemCount = itemCount;
	   }

	   @Override
	   public String toString() 
	   {
	       return "Inventory [name=" + name + ", itemCount=" + itemCount + "]";
	   }

}
