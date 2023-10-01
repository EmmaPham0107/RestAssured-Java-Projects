package com.api.auto.testcase;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public abstract class TC_Base {

	protected Response response;
	protected JsonPath bodyJson;
	protected ResponseBody resBody;
	
	protected abstract RequestSpecification buildRequest() throws Exception;
	
	@BeforeClass
	public void initMethod() throws Exception {
		RequestSpecification request = buildRequest();
		response = request.post();
		resBody = response.getBody();
		bodyJson = response.jsonPath();
		System.out.println(getClass().getName() + "\r\n" + response.asPrettyString());
	}
	
	@AfterClass
	public void afterClass() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
}
