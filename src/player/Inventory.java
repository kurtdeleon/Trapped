package player;

import java.lang.reflect.Field;

import item.Item;

public class Inventory {
	
	public static boolean HAS_FLASHLIGHT = false;
	
	public static Item RATIONS = new Item( new String[] {"rations", "ration", "food"}, 
			0, 
			true, 
			"", 
			"You ate some RATIONS. You feel fuller.", 
			"You don't have any left. You feel regret for eating them so soon." );
	
	public static Item BANDAGES = new Item( new String[] {"bandages", "bandage", "medicine"}, 
			0, 
			true, 
			"", 
			"You put bandages on your wounds. You feel slightly better.", 
			"Seems like you've run out of bandages... damn." );
	
	public static Item JADE = new Item( new String[] {"jade", "jade piece"}, 
			0, 
			true,
			"", 
			"", 
			"" );
	
	public static Item VINES = new Item( new String[] {"vine", "vines", "tough vines", "rope"}, 
			0, 
			true, 
			"", 
			"", 
			"" );
	
	public static Item WHITE_MUSHROOM = new Item( new String[] {"white mushroom"}, 
			0, 
			true, 
			"", 
			"You decide to eat it after some time of deliberation.\nIt tastes like death. Bad idea.", 
			"You can't find any white mushrooms in your backpack." );
	
	public static Item BERRY = new Item( new String[] {"berry", "berries", "strawberries", "fruit"}, 
			0, 
			true, 
			"", 
			"They taste a bit sweet, but mostly bland.", 
			"You ran out of berries." );
	
	public static Item SHARP_ROCK = new Item( new String[] {"sharp rock", "sharp", "rock", "knife"}, 
			0, 
			true, 
			"", 
			"", 
			"" );
	
	public static Item CLAY_POT = new Item( new String[] {"clay pot", "pot", "pail", "container"}, 
			0, 
			true, 
			"", 
			"", 
			"");
	
	public static Item WATER = new Item( new String[] {"water", "liquid"}, 
			0, 
			true, 
			"", 
			"", 
			"");
	
	public static Item COCKROACH = new Item( new String[] {"cockroach", "insect"},  
			0, 
			true, 
			"", 
			"You've been told that insects are rich in protein.\nYou feel fuller, but you've probably contracted something.", 
			"It's in your stomach.");
	
	public static Item POOP = new Item( new String[] {"poop", "shit", "feces"}, 
			0, 
			true, 
			"", 
			"Not sure how you managed to take it... but you decide to eat it.\nGood job, dummy. You died.", 
			"It's in your stomach.");
	
	public static Item BROKEN_BOAT = new Item( new String[] {"broken boat", "broken vessel", "broken ship", "broken watercraft"}, 
			0, 
			true, 
			"", 
			"", 
			"");
	
	public static Item BOAT = new Item( new String[] {"boat", "vessel", "ship", "watercraft"}, 
			0, 
			true, 
			"", 
			"", 
			"");
	
	public static Item LEAVES = new Item( new String[] {"leaf", "leaves"}, 
			0, 
			true, 
			"", 
			"You apply the leaves on your wounds. You feel slightly better.", 
			"You don't have extra leaves anymore.");
	
	public static Item GetItem(String item) throws IllegalArgumentException, IllegalAccessException
	{
		for ( Field fld : player.Inventory.class.getDeclaredFields() )
    	{
    		if ( fld.getType().equals(Item.class) ) {
    			Item tempItem = (Item) fld.get(null);
    			
    			if ( tempItem.CheckIfItemName(item) )
    			{
    				return tempItem;
    			}
    		}
    	}
		return null;
	}
	
	public static void EmptyBackpack() throws IllegalArgumentException, IllegalAccessException
	{
		for ( Field fld : player.Inventory.class.getDeclaredFields() )
    	{
    		if ( fld.getType().equals(Item.class) ) {
    			Item tempItem = (Item) fld.get(null);
    			
    			tempItem.SetStock(0);
    		}
    	}
	}
}
