package cave;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import chamber.Chamber1;
import chamber.EnterCondition;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public class CaveMaker {
	private HashMap<Class<?>, Object> chamberMap;
	private Object currentChamber;
	private List<String> listOfAllClasses;
	
	public void LoadChambers() throws Exception
	{
		for (String className : listOfAllClasses)
		{
			try
			{
				Class<?> chamberClass = Class.forName(className);
				Object chamberInstance = chamberClass.newInstance();
				if ( chamberClass.isAnnotationPresent(Chamber.class) )
				{
					chamberMap.put( chamberClass, chamberInstance );
				}
			}
			catch (Exception e)
			{
				//System.out.println("shit has been detected xD");
			}
		}
	}
	
	public void LoadChamberMethodsAndFields() throws IllegalArgumentException, IllegalAccessException
	{
		for ( Class<?> chamberClass : chamberMap.keySet() )
		{
			Object tempChamber;
			tempChamber = chamberMap.get( chamberClass );
			
			for ( Field fld : chamberClass.getDeclaredFields() )
			{
				if ( fld.isAnnotationPresent(Direction.class) )
				{
					Class<?> fieldClass = fld.getType();
					Object chamberInstance = chamberMap.get(fieldClass);
					fld.setAccessible(true);
					fld.set(tempChamber, chamberInstance);
				}
			}
			
			for (Method met : chamberClass.getDeclaredMethods())
			{
				if (met.isAnnotationPresent(Command.class))
				{
					met.setAccessible(true);
				}
			}
		}
	}
	
	public void Load() throws Exception
	{
		chamberMap = new HashMap<Class<?>, Object>();
		
		FastClasspathScanner scanner = new FastClasspathScanner( Chamber1.class.getPackage().getName() );
		ScanResult result = scanner.scan();
		
		listOfAllClasses = result.getNamesOfAllClasses();
		
		LoadChambers();
		LoadChamberMethodsAndFields();
		
		currentChamber = chamberMap.get( Chamber1.class );
//		PrintDescription();
	}
	
	public String PrintDescription() throws Exception
	{
		Method met = currentChamber.getClass().getDeclaredMethod("GetDescription");
//		System.out.println( met.invoke(currentChamber) );
		return (String) met.invoke(currentChamber);
		
		/*Method met2 = currentChamber.getClass().getDeclaredMethod("GetCommands");
		Method met3 = currentChamber.getClass().getDeclaredMethod("GetRoomItems");
		System.out.println( met2.invoke(currentChamber) );	
		System.out.println( met3.invoke(currentChamber) );*/
	}
	
	public String Move(String direction)
	{
		Class<?> roomClass = currentChamber.getClass();
		boolean roomFound = false;
		String ret = "";
		
		try
		{
			for (Field fld : roomClass.getDeclaredFields())
			{
				if (fld.isAnnotationPresent(Direction.class))
				{
					Direction fieldDirection = fld.getAnnotation(Direction.class);
					
					if (fieldDirection.direction().equals(direction))
					{
						Class<?> fieldClass = fld.getType();
						
						if ( fieldDirection.accessible() )
						{
							currentChamber = chamberMap.get(fieldClass);
							PrintDescription();
						}
						else
						{
							ret = fieldDirection.accessMessage();
						}
						
						roomFound = true;
						break;
					}
				}
			}
			
			if (!roomFound) {
				ret = "There's no path there... unless you can walk through walls. Which you can't.";
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
	
	public String Perform(String action, String subject)
	{
		Class<?> clazz = currentChamber.getClass();
		
		String ret = "";
		
		try
		{
			boolean methodFound = false;
			for (Method method : clazz.getDeclaredMethods())
			{
				if (method.isAnnotationPresent(Command.class))
				{
					Command command = method.getAnnotation(Command.class);
				
					if (command.command().equalsIgnoreCase(action))
					{
						if ( subject != null && method.getParameterCount() > 0 )
						{
							ret = (String) method.invoke(currentChamber, subject);
							methodFound = true;
						}
						else if ( subject == null && method.getParameterCount() == 0 )
						{
							ret = (String) method.invoke(currentChamber);
							methodFound = true;
						}

						break;
					}
				}
			}
			if (!methodFound) {
				ret = "Invalid command!";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
	
}

