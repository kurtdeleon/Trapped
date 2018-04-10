package cave;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import annotation.Locked;
import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import annotation.Interceptor;
import chamber.Chamber1;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import proxy.EnterCondition;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

public class CaveMaker {
	private HashMap<Class<?>, Object> chamberMap;
	private Object currentChamber;
	private List<String> listOfAllClasses;
	private List<String> listOfAllInterceptors;
	
	private String GetInterceptor(String code) {
		
		for ( String interceptorName : listOfAllInterceptors )
		{
			try
			{
				Class<?> chamberInterceptor = Class.forName(interceptorName);
				
				if ( chamberInterceptor.isAnnotationPresent(Interceptor.class) ) 
				{
					if ( chamberInterceptor.getAnnotation(Interceptor.class).code().equals(code) )
					{
						return chamberInterceptor.getName();
					}
				}
			}
			catch (Exception e) { System.exit(1); }
		}
		
		return null;
	}
	
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
					if (chamberClass.isAnnotationPresent(Locked.class)) 
					{
						ByteBuddy byteBuddy = new ByteBuddy();
						DynamicType.Builder<Object> builder = (Builder<Object>) byteBuddy.subclass(chamberClass).implement( proxy.EnterCondition.class );
						
						try
						{
							// GET NAME OF INTERCEPTOR CLASS
							String interceptorName = GetInterceptor( chamberClass.getAnnotation(Locked.class).code() );
							
							if ( interceptorName != null )
							{
								// GET INTERCEPTOR CLASS 
								Class<?> interceptorClass = Class.forName( interceptorName );
								
								// ACQUIRE LIST OF METHOD NAMES AND MATCH USING BUILDER
								Method[] interceptorMethods = interceptorClass.getDeclaredMethods();
								for ( Method interceptorMethod : interceptorMethods )
								{
									builder = builder.method(ElementMatchers.named(interceptorMethod.getName())).
											intercept(MethodDelegation.to(interceptorClass));
								}

								// CREATE YOUR UNLOADED CLASS
								Unloaded<Object> unloadedClass = builder.make();
								
								// LOAD CLASS TO JVM
								Loaded<?> loaded = unloadedClass.load( getClass().getClassLoader() );
								Class<?> dynamicType = loaded.getLoaded();
								
								chamberInstance = dynamicType.newInstance();
								
								System.out.println("A locked room of type Room5 has been created.");
							}
						}
						catch (Exception e){ System.out.println("A locked room has been detected."); }
					}
					
					System.out.println(chamberInstance.getClass().toString()); //TESTER
					chamberMap.put( chamberClass, chamberInstance );
				}
			}
			catch (Exception e){}
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
		
		FastClasspathScanner scannerClass = new FastClasspathScanner( chamber.Chamber1.class.getPackage().getName() );
		ScanResult resultClass = scannerClass.scan();
		listOfAllClasses = resultClass.getNamesOfAllClasses();
		
		FastClasspathScanner scannerInterceptor = new FastClasspathScanner( proxy.EnterCondition.class.getPackage().getName() );
		ScanResult resultInterceptor = scannerInterceptor.scan();
		listOfAllInterceptors = resultInterceptor.getNamesOfAllClasses();
		
		LoadChambers();
		LoadChamberMethodsAndFields();
		
		currentChamber = chamberMap.get( Chamber1.class );
		PrintDescription();
	}
	
	public void PrintDescription() throws Exception
	{
		Method met = currentChamber.getClass().getDeclaredMethod("GetDescription");
		System.out.println( met.invoke(currentChamber) );
	}
	
	public void Move(String direction)
	{
		Class<?> roomClass = currentChamber.getClass();
		boolean roomFound = false;
		
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
							if (fieldClass.isAnnotationPresent(Locked.class))
							{
								Object tempRoom = chamberMap.get(fieldClass);
								
								if (tempRoom.getClass().getSuperclass() == Object.class)
								{
									currentChamber = chamberMap.get(fieldClass);
									PrintDescription();
								}
								else
								{
									if ( ((EnterCondition) tempRoom).canEnter() ) 
									{
										System.out.println(((EnterCondition) tempRoom).enterMessage());
										currentChamber = tempRoom.getClass().getSuperclass().newInstance();
										
										// REPLACE PROXY IN CHAMBERMAP
										chamberMap.put(fieldClass, currentChamber); 
									}
									else 
									{
										System.out.println(((EnterCondition) tempRoom).unableToEnterMessage());
									}
								}
								
							}
							else
							{
								currentChamber = chamberMap.get(fieldClass);
								PrintDescription();
							}
						}
						else
						{
							System.out.println( fieldDirection.accessMessage() );
						}
						
						roomFound = true;
						break;
					}
				}
			}
			
			if (!roomFound) {
				System.out.println("There's no path there... unless you can walk through walls. Which you can't.");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void Perform(String action, String subject)
	{
		Class<?> clazz = currentChamber.getClass();
		
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
							System.out.println(method.invoke(currentChamber, subject));
							methodFound = true;
						}
						else if ( subject == null && method.getParameterCount() == 0 )
						{
							System.out.println(method.invoke(currentChamber));
							methodFound = true;
						}

						break;
					}
				}
			}
			if (!methodFound) {
				System.out.println("Invalid command!");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}