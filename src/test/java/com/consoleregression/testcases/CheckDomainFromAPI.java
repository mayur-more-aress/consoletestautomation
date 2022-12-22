package com.consoleregression.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import com.base.TestBase;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CheckDomainFromAPI extends TestBase {
	public static ExtentTest logger;

	public CheckDomainFromAPI() {
		super();
	}

	@Test
	public static void checkDomainAvailabilityAffilias() {
		String strDomainName = null;
		String strTld[] = { ".com.au" };

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		for (String tld : strTld) {
			RestAssured.baseURI = "https://cdn.stage.provisioning-api.melbourneit.com.au/v1";
			RequestSpecification request = RestAssured.given();
			request.header("Authorization", "Basic dGVzdDp0ZXN0cGFzc3dvcmQ=");
			Response response = request.get("/dmg/domains/" + strDomainName + tld + "/check");

			int statusCode = response.getStatusCode();
			System.out.println(response.asString());
			if (statusCode == 200) {
				System.out.println("Domain is available: " + strDomainName + tld);
				test.log(LogStatus.PASS, "Domain is available: " + strDomainName + tld);
			} else {
				test.log(LogStatus.ERROR, "Domain not available: " + strDomainName + tld);
			}
		}
	}
}
