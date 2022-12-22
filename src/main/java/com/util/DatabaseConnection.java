package com.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Properties;

import com.base.TestBase;

public class DatabaseConnection extends TestBase
{
	static Connection c = null;
	static Statement stmt = null;
	static int serviceid=0;
	static Properties p=new Properties();
	
	public static void connectToDatabase() throws SQLException, IOException 
	{	
		FileReader reader=new FileReader("src\\main\\java\\com\\config\\database.properties");
		p.load(reader);
		try 
	    {
	       Class.forName("org.postgresql.Driver");
	       c = DriverManager.getConnection(p.getProperty("connectionString"),p.getProperty("userName"),p.getProperty("password"));
	    } 
	    catch(Exception e) 
	    {
	       e.printStackTrace();
	       System.err.println(e.getClass().getName()+": "+e.getMessage());
	       System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}
	
	public static void editProductExpiryDate(String expiryDate,String productCode, String greenCode) throws SQLException
	{
		 //Select query:
		String selectServiceIdQuery = p.getProperty("getServiceIdQuery");
		String getServiceIdQuery = MessageFormat.format(selectServiceIdQuery,"'"+productCode+"'","'"+greenCode+"'");
		System.out.println(getServiceIdQuery);
	    stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(getServiceIdQuery );
        while ( rs.next() ) 
        {
            serviceid = rs.getInt("serviceid");
            System.out.println( "ID = " + serviceid );
            System.out.println();
        }
        rs.close();
        stmt.close();
	    
        //Update Query:
        String selectExpiryDateQuery = p.getProperty("updateExpiryDateQuery");
		String getExpiryDateQuery = MessageFormat.format(selectExpiryDateQuery,"'"+expiryDate+"'",serviceid);
		System.out.println(getExpiryDateQuery);
        stmt = c.createStatement();
        stmt.executeUpdate(getExpiryDateQuery);
        System.out.println("Updated successfully");	  
        stmt.close();
        c.close();
	}
	     
	public static String getTestdoaminForauTransfer() throws SQLException
	{
		String  domainName = null;
		String getDomainNameQuery = p.getProperty("getDomainNameForauTransfers");
		System.out.println(getDomainNameQuery);
        stmt = c.createStatement();
        System.out.print("query executing");
        ResultSet rs  = stmt.executeQuery(getDomainNameQuery) ;
        System.out.print("query executed");
        while ( rs.next() ) 
        {
            domainName =rs.getString("domain");
            System.out.println( "Domain Name: " + domainName );
            System.out.println();
        }
        rs.close();
        stmt.close();
        c.close();
        return domainName;
	}
	
	public static void updateTldId(String domainName) throws SQLException
	{
		String selectAltTldId = p.getProperty("updateAltTldId");
		String getAltTldIdQuery = MessageFormat.format(selectAltTldId,"'"+domainName+"'");
		System.out.println(getAltTldIdQuery);
		stmt = c.createStatement();
        stmt.executeUpdate(getAltTldIdQuery);
        System.out.println("Updated successfully");	 
        stmt.close();
        c.close();
	}
	
	public static String fetchRegisteredDomainName(String virtualization, String accountref,String tld) throws Exception
	{
		String  regdomainName = null;
		String virtualizationId = null;
		if(virtualization.equalsIgnoreCase("Netregistry"))
		{
			virtualizationId = "1";
		}
		else if(virtualization.equalsIgnoreCase("MITRetail"))
		{
			virtualizationId = "19";
		}
		else if(virtualization.equalsIgnoreCase("TPPW"))
		{
			virtualizationId = "10";
		}
		else if(virtualization.equalsIgnoreCase("MITDPS"))
		{
			virtualizationId = "17";
		}
		else
		{
			throw new Exception("Virtualization id for "+virtualization+" not exist");
		}
		String getRegisteredDomainName = p.getProperty("fetchRegisteredDomain");
		String getRegDomainQuery = MessageFormat.format(getRegisteredDomainName,"'"+accountref+"'",virtualizationId,"'%."+tld+"'");
		System.out.println(getRegDomainQuery);
        stmt = c.createStatement();
        System.out.print("query executing");
        ResultSet rs  = stmt.executeQuery(getRegDomainQuery) ;
        System.out.print("query executed");
        while ( rs.next() ) 
        {
        	regdomainName =rs.getString("domain");
            System.out.println( "Domain Name: " + regdomainName );
            System.out.println();
        }
        rs.close();
        stmt.close();
        c.close();
        return regdomainName;
	}
}
