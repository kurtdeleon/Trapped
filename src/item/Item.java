package item;

import java.util.Arrays;
import java.util.List;

public class Item {
	private int stock;
	private boolean isInventoryItem;
	private String takeMessage, useMessage, noSupplyMessage;
	private List<String> names;
	
	public Item(String[] names, int stock, boolean isInventoryItem, String takeMessage, String useMessage, String noSupplyMessage) {
		super();
		this.names = Arrays.asList(names);
		this.SetStock(stock);
		this.isInventoryItem = isInventoryItem;
		this.takeMessage = takeMessage;
		this.useMessage = useMessage;
		this.noSupplyMessage = noSupplyMessage;
	}
	
	public String GetMainItemName(){
		return names.get(0);
	}
	
	public List<String> GetItemName() {
		return names;
	}
	
	public boolean CheckIfItemName(String str) {
		return names.contains(str.toLowerCase());
	}

	public int GetStock() {
		return stock;
	}
	
	public void SetStock(int stock) {
		this.stock = stock;
	}
	
	public void AddStock(int stock) {
		this.stock += stock;
	}
	
	public void RemoveStock(int stock) {
		this.stock -= stock;
	}
	
	public boolean HasStock(){
		if ( stock <= 0 ) {
			return false;
		}
		return true;
	}

	public boolean IsInventoryItem() {
		return isInventoryItem;
	}
	
	public String GetUseMessage() {
		return useMessage;
	}

	public String GetTakeMessage() {
		return takeMessage;
	}

	public String GetNoSupplyMessage() {
		return noSupplyMessage;
	}
}
