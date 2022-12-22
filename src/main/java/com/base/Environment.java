package com.base;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources(
	"file:src/main/java/com/config/${env}.properties" 
)

public interface Environment extends Config{
	
	//Sales DB
	String salesdburl();
	
	//Console Admin
	String consoleadminurl();
	
	//Old Shopping Cart
	String oldcart_domainsearchurl_domainz();
	String oldcart_domainsearchurl_netregistry();
	String oldcart_domainsearchurl_melbourneit();
	String oldcart_domainsearchurl_webcentral();
	String oldcart_domainsearchurl_tppw();
	
	//New Shopping Cart
	String newcart_domainsearchurl_netregistry();
	
	//Customer Portal
	String customerportalurl_domainz();
	String customerportalurl_netregistry();
	String customerportalurl_melbourneit();
	String customerportalurl_webcentral();
	String customerportalurl_tpp();
	String customerportalurl_mitdps();
	
	//Reseller Portal
	String resellerportalurl_tpp();
	String resellerportalurl_mitdps();
	
	//Obsidian Url
	String obsidian_url();
		
	//RRPproxy Portal
	String rrpproxy_tpp();
	
	//Payment Gateway
	String braintreeurl();
	
	//Reseller Api Portal
	String resellerapiportalurl_mitdps();
	String resellerapiportalurl_tpp();
	
	//Others
	String carturl();
	String cartloginurl();
	String carturl_domainz();
	String username();
	String password();
	String browser();
	
	
	@Key("db.hostname")
	String getDBHostname();
	
	@Key("db.port")
	int getDBPort();
	
	@Key("db.username")
	String getDBUsername();
	
	@Key("db.password")
    String getDBPassword();
	
}
