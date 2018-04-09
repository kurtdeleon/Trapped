package player;

import java.lang.reflect.Field;

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
	
	public static Item SHARP_ROCK = new Item( new String[] {"sharp rock", "sharp", "rock", "knife"}, 
			0, 
			true, 
			"", 
			"", 
			"" );
	
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
}
