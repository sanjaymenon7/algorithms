package zombie;

import java.util.HashMap;

public class ItemCollection {

	 public HashMap<String,Integer> Contents = new HashMap<String,Integer>();
	  public int TotalValue;
	  public int TotalWeight;

	  public void AddItem(Item item,int quantity) {
	    if(Contents.containsKey(item.Description)) 
	    	Contents.put(item.Description,Contents.get(item.Description)+ quantity) ;
	    else Contents.put(item.Description, quantity);
	    TotalValue += quantity * item.Value;
	    TotalWeight += quantity * item.Weight;
	  }

	  public ItemCollection Copy() {
		  ItemCollection ic = new ItemCollection();
	    ic.Contents = new HashMap<String,Integer>(this.Contents);
	    ic.TotalValue = this.TotalValue;
	    ic.TotalWeight = this.TotalWeight;
	    return ic;
	  }
}
