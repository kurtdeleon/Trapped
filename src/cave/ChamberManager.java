package cave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;

import annotation.Chamber;
import player.Item;

public class ChamberManager {
	
	private File save;
	private Scanner sc;
	
	private String GetChamberData( HashMap<Class<?>, Object> chamberMap )
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		pw.println( chamberMap.size() );
		for ( Object chamberInstance : chamberMap.values() ) 
		{
			Class<?> chamberClass = chamberInstance.getClass();			
			
			if ( chamberClass.isAnnotationPresent(Chamber.class) )
		    {
				pw.print( chamberClass.getName() + " " + "true" + " " );
		
				Method SaveRoomData = null;
				try {
					SaveRoomData = chamberClass.getDeclaredMethod("SaveRoomData");
					SaveRoomData.setAccessible(true);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				
				try {
					pw.println( SaveRoomData.invoke(chamberInstance) );
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
		    }
		    else
		    {
		    	pw.println( chamberClass.getSuperclass().getName() + " " + "false" );
		    }
			pw.println( "---" );
		}
		
		return sw.toString();
	}
	
	private String GetPlayerStatus()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		pw.print( player.Status.name + " ");
		pw.print( player.Status.GetHealth() + " ");
		pw.println( player.Status.GetHunger() );
		
		return sw.toString();
	}
	
	private String GetPlayerInventory()
	{
		return player.Inventory.SaveInventoryState();
	}
	
	public void SaveData( HashMap<Class<?>, Object> chamberMap )
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter("savefile.txt", "UTF-8");
			writer.println( GetChamberData( chamberMap ).trim() );
			writer.println( GetPlayerStatus().trim() );
			writer.println( GetPlayerInventory().trim() );
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Class<?>, Object> LoadData( HashMap<Class<?>, Object> chamberMap ) throws IllegalArgumentException, IllegalAccessException
	{	
		try {
			save = new File("savefile.txt");
			sc = new Scanner(save);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			chamberMap = LoadChamberData( chamberMap );
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		LoadPlayerStatus();
		LoadPlayerInventory();
		
		return chamberMap;
	}
	
	private HashMap<Class<?>, Object> LoadChamberData( HashMap<Class<?>, Object> chamberMap ) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		int numberOfChambers = sc.nextInt();
		sc.nextLine();
		
		for ( int i = 0; i < numberOfChambers; i++ )
		{
			String className = sc.next();
			
			Class<?> chamberClass = Class.forName(className);
			boolean isUnlocked = sc.nextBoolean();
			sc.nextLine();
			
			if ( isUnlocked ) 
			{
				Object chamberInstance = chamberMap.get( chamberClass );
				
				while ( !sc.hasNext("---") )
				{
					String fieldName = sc.nextLine();
					System.out.println(fieldName);
					Field fld = chamberClass.getDeclaredField( fieldName );
					fld.setAccessible(true);
					
					Class<?> fieldClass = fld.getType();
					if ( fieldClass == Boolean.class )
					{
						boolean newVal = sc.nextBoolean();
						fld.setBoolean( chamberInstance, newVal );
					}
					else if ( fieldClass == Integer.class )
					{
						int newVal = sc.nextInt();
						fld.setInt( chamberInstance, newVal );
					}
					else if ( fieldClass == Item.class )
					{
						int newVal = sc.nextInt();
						Object fieldValue = fld.get( chamberInstance );
						Method setValue = fieldValue.getClass().getDeclaredMethod( "SetStock", Integer.TYPE );
						setValue.invoke( fieldValue, newVal );
					}
					sc.nextLine();
				}
			}
			sc.nextLine(); // skip "---"
		}
		
		return chamberMap;
	}
	
	private void LoadPlayerStatus()
	{
		player.Status.name = sc.next();
		player.Status.SetHealth( sc.nextInt() );
		player.Status.SetHunger( sc.nextInt() );
		sc.nextLine();
	}
	
	private void LoadPlayerInventory() throws IllegalArgumentException, IllegalAccessException
	{
		while ( sc.hasNext() )
		{
			String fieldName = sc.nextLine();
			int value = sc.nextInt();
			sc.nextLine();
			System.out.println( fieldName + " " + value);
			Item tempItem = (Item) player.Inventory.GetItem( fieldName );
			tempItem.SetStock( value );
		}
	}
}
