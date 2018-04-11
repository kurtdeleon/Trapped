package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import annotation.Command;
import player.Item;

public class BaseChamber {
	
	public Item GetItem(String item) throws IllegalArgumentException, IllegalAccessException
	{
		for ( Field fld : this.getClass().getDeclaredFields() )
    	{
    		if ( fld.getType().equals(Item.class) ) {
    			
    			fld.setAccessible(true);
    			Item tempItem = (Item) fld.get(this);
    			
    			if ( tempItem.CheckIfItemName(item) )
    			{
    				return tempItem;
    			}
    		}
    	}
		return null;
	}
	
	public List<String> GetCommands()
	{
    	List<String> commands = new ArrayList<String>();
    	
    	for ( Method met : this.getClass().getDeclaredMethods() )
    	{
    		if (met.isAnnotationPresent(Command.class) ) 
    		{
    			commands.add( met.getAnnotation(Command.class).command() );
    		}
    	}
    	
		return commands;
	}
	
	public List<String> GetRoomItems()
	{
		List<String> roomItems = new ArrayList<String>();
		
		for ( Field fld : this.getClass().getDeclaredFields() )
    	{
    		if ( fld.getType().equals(Item.class) ) 
    		{
				try 
				{
					fld.setAccessible(true);
					Item tempItem = (Item) fld.get(this);
					if ( tempItem.HasStock() )
					{
						roomItems.add( tempItem.GetMainItemName() );
					}
					
				} 
				catch (Exception e) {} 
    		}
    	}
		return roomItems;
	}
	
	public List<String> GetInventoryList()
    {
        List<String> inventoryList = new ArrayList<String>();
       
        for ( Field fld : player.Inventory.class.getDeclaredFields() )
        {
            if ( fld.getType().equals(Item.class) )
            {
                try
                {
                    fld.setAccessible(true);
                    Item tempItem = (Item) fld.get(null);
                    if ( tempItem.HasStock() )
                    {
                    	inventoryList.add( tempItem.GetMainItemName() + " (" + tempItem.GetStock() + ")" );
                    }
                   
                }
                catch (Exception e) {}
            }
        }
        return inventoryList;
    }
	
	@Command(command="use")
	public String Use(String item)
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    
		try 
		{
			Item inventoryItem = player.Inventory.GetItem( item );
			
			if ( inventoryItem == null )
			{
				pw.println("Item not found in inventory.");
				pw.println();
			}
			else
			{
				/*
				 * ADD HERE THE EFFECTS OF USING GLOBAL ITEMS
				 * ADD HERE THE EFFECTS OF USING GLOBAL ITEMS
				 * ADD HERE THE EFFECTS OF USING GLOBAL ITEMS
				 * ADD HERE THE EFFECTS OF USING GLOBAL ITEMS
				 */
				if ( inventoryItem.CheckIfItemName("ration") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						player.Status.AddHealth(80);
						pw.println( inventoryItem.GetUseMessage() );
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else if ( inventoryItem.CheckIfItemName("bandage") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						player.Status.AddHunger(80);
						pw.println( inventoryItem.GetUseMessage() );
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else if ( inventoryItem.CheckIfItemName("leaf") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						player.Status.AddHealth(10);
						pw.println( inventoryItem.GetUseMessage() );
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else if ( inventoryItem.CheckIfItemName("white mushroom") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						player.Status.RemoveHealth(30);
						player.Status.RemoveHunger(20);
						pw.println( inventoryItem.GetUseMessage() );
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else if ( inventoryItem.CheckIfItemName("cockroach") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						player.Status.RemoveHealth(50);
						player.Status.AddHunger(30);
						pw.println( inventoryItem.GetUseMessage() );
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else if ( inventoryItem.CheckIfItemName("poop") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						pw.println( inventoryItem.GetUseMessage() );
						GameState.PLAYER_DEAD = true;
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else if ( inventoryItem.CheckIfItemName("berry") )
				{
					if ( inventoryItem.HasStock() )
					{
						inventoryItem.RemoveStock(1);
						player.Status.AddHunger(30);
						pw.println( inventoryItem.GetUseMessage() );
					}
					else
					{
						pw.println( inventoryItem.GetNoSupplyMessage() );
					}
				}
				else
				{
					pw.println("Can't do that.");
				}
			}
		} 
		catch (Exception e) {} 
		
		pw.println();
		return sw.toString();
	}
	
	@Command(command="take")
	public String Take(String item) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		try 
		{
			Item roomItem = GetItem( item );
			Item inventoryItem = player.Inventory.GetItem( item );
			
			if ( roomItem == null || inventoryItem == null )
			{
				pw.println("Item not found in room.");
				pw.println();
			}
			else
			{
				if ( roomItem.HasStock() )
				{
					inventoryItem.AddStock( roomItem.GetStock() ); 
					roomItem.SetStock(0);
					pw.println( roomItem.GetTakeMessage() );
				}
				else
				{
					pw.println( roomItem.GetNoSupplyMessage() );
				}
			}
		} 
		catch (Exception e) { e.printStackTrace(); System.exit(1);} 
		
		return sw.toString();
	}
}
