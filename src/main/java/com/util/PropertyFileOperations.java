package com.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyFileOperations 
{
	static String filePath = "src\\main\\java\\com\\config\\testAccounts.properties";
	static Properties prop = new Properties();
	
	
	public static String readProperties(String propName) throws IOException 
	{	
		String strGreenCode = null;
	    try (InputStream input = new FileInputStream(filePath)) 
	    {
            prop.load(input);
            strGreenCode = prop.getProperty(propName);
            System.out.println(prop.getProperty(strGreenCode));
        }
	    catch (IOException ex) 
	    {
            ex.printStackTrace();
        }
	    return strGreenCode;
	}
	
	public static void writeProperties(String propName, String propValue) throws IOException 
	{	
		FileInputStream in = new FileInputStream(filePath);
	    prop.load(in);
	    in.close();
		try (OutputStream output = new FileOutputStream(filePath)) 
		{
	       prop.setProperty(propName, propValue);
	       prop.store(output, null);
	       output.close();
	    } 
		catch (IOException io) 
		{
	        io.printStackTrace();
	    }
	}
	
	public static String readApiProperty(String propName) throws IOException 
	{	
		String fileName = "src\\main\\java\\com\\config\\resellerapi.properties";
		String propValue = null;
	    try (InputStream input = new FileInputStream(fileName)) 
	    {
            prop.load(input);
            propValue = prop.getProperty(propName);
            System.out.println(prop.getProperty(propValue));
        }
	    catch (IOException ex) 
	    {
            ex.printStackTrace();
        }
	    return propValue;
	}
}
