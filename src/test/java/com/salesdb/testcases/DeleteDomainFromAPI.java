package com.salesdb.testcases;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class DeleteDomainFromAPI {

	public static void deleteDomainFromAPIAfilias(String domainName) {
		RestAssured.baseURI = "https://cdn.stage.provisioning-api.melbourneit.com.au/v1";
		RequestSpecification request = RestAssured.given();

		// Add a header stating the Request body is a JSON
		request.header("Authorization", "Basic dGVzdDp0ZXN0cGFzc3dvcmQ=");
		// Delete the request and check the response
		Response response = request.delete("/dmg/domains/" + domainName + "");

		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		if (statusCode == 202) {
			System.out.println("Domain deleted Successfully!");
		} else {
			System.out.println("Unable to delete domain");
		}
	}

	public static void deleteDomainFromAPITucows(String domainName) {
		RestAssured.baseURI = "https://cdn.stage.provisioning-api.melbourneit.com.au/v1";
		RequestSpecification request = RestAssured.given();
		
		// Add a header stating the Request body is a JSON
		request.header("Authorization", "Basic dGVzdDp0ZXN0cGFzc3dvcmQ=");
		// Delete the request and check the response
		Response response = request.delete("/dmg-tucows/domains/"+domainName+"");

		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		if (statusCode == 202) {
			System.out.println("Domain deleted Successfully!");
		} else {
			System.out.println("Unable to delete domain");
		}
	}
	

	public static void deleteDomainFromAPIProduction(String domainName) {
		RestAssured.baseURI = "**** Production URL here ****";
		RequestSpecification request = RestAssured.given();

		// Add a header stating the Request body is a JSON
		request.header("Authorization", "*****Production Credentials Here*****");
		// Delete the request and check the response
		Response response = request.delete("/dmg/domains/" + domainName + "");

		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		if (statusCode == 202) {
			System.out.println("Domain deleted Successfully!");
		} else {
			System.out.println("Unable to delete domain");
		}
	}
}
