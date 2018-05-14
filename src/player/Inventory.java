package player;

import java.lang.reflect.Field;

import item.*;

public class Inventory {
	
	public static boolean HAS_FLASHLIGHT = false;
	
	public static Item RATIONS = new Ration();
	public static Item BANDAGES = new Bandage();
	public static Item JADE = new Jade();
	public static Item VINES = new Vine();
	public static Item WHITE_MUSHROOM = new WhiteMushroom();
	public static Item BERRY = new Berry();
	public static Item SHARP_ROCK = new SharpRock();
	public static Item CLAY_POT = new ClayPot();
	public static Item WATER = new Water();
	public static Item COCKROACH = new Cockroach();
	public static Item POOP = new Poop();
	public static Item BROKEN_BOAT = new BrokenBoat();
	public static Item BOAT = new Boat();
	public static Item LEAVES = new Leaf();
	
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
	
	// tester
	public static void main(String[] args) throws Exception {
		String item = "boat";
		for ( Field fld : player.Inventory.class.getDeclaredFields() )
    	{
			if ( fld.getType().equals(Item.class) ) {
				Item tmp = (Item) fld.get(null);
				System.out.println("---");
	    		System.out.println(tmp.getClass().getSuperclass().getDeclaredMethod("GetMainItemName").invoke(tmp));
	    		try {
	    			System.out.println(fld.get(null).getClass().getDeclaredMethod("doEffect"));
	    		} catch (NoSuchMethodException e) {
	    			System.out.println("no effects");
	    		}
			}
    	}
	}
}
