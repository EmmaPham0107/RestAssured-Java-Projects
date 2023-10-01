package com.api.auto.testcase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class TC_API_CreateWork extends TC_Base {
	
	protected RequestSpecification buildRequest() throws Exception  {
		//Initiate Body 
		Map <String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("nameWork", PropertiesFileUtils.getConfig("myWork"));
		userInfo.put("experience", PropertiesFileUtils.getConfig("myExperience"));
		userInfo.put("education", PropertiesFileUtils.getConfig("myEducation"));
		System.out.println("createWorkBody " + userInfo);
		//Initiate URL
		RestAssured.baseURI = PropertiesFileUtils.getConfig("baseURL");
		RestAssured.basePath = PropertiesFileUtils.getConfig("createWorkPath");
		
		return RestAssured.given()
				.contentType(ContentType.JSON)
				.header("token",PropertiesFileUtils.getToken("token"))
				.body(userInfo);
	}
	
	@Test (priority = 0)
	public void validateCreateWorkStatusCode() {
		//Kiểm chứng tạo thành công và nhận về HTTP status code 201 Created
		Assert.assertEquals(response.getStatusCode(), 201, "Status check Failed!");
	}
	@Test (priority = 1)
	public void validateId() {
		//Kiểm chứng Response trả về có chứa trường id
		Assert.assertTrue(resBody.asString().contains("id"), "Id check Failed!");
	}
	@Test (priority = 2)
	public void validateNameOfWorkMatched() throws Exception {
		//Kiểm chứng Response trả về có trường nameWork
		Assert.assertTrue(resBody.asString().contains("nameWork"), "Name work check Failed!");
		//Kiểm chứng trường nameWork có khớp với nameWork lúc tạo
		Assert.assertEquals(bodyJson.getString("nameWork"), PropertiesFileUtils.getConfig("myWork"), "Name of work is not matched!");
	}
	@Test (priority = 3)
	public void validateExperienceMatched() throws Exception {
		//Kiểm chứng Response trả về có trường experience
		Assert.assertTrue(resBody.asString().contains("experience"), "Experience check Failed!");
		//Kiểm chứng trường experience có khớp với experience lúc tạo
		Assert.assertEquals(bodyJson.getString("experience"), PropertiesFileUtils.getConfig("myExperience"), "Experience is not matched!");
	}
	@Test (priority = 4)
	public void validateEducationMatched() throws Exception {
		//Kiểm chứng Response trả về có trường education
		Assert.assertTrue(resBody.asString().contains("education"), "Education check Failed!");
		//Kiểm chứng trường education có khớp với education lúc tạo
		Assert.assertEquals(bodyJson.getString("education"), PropertiesFileUtils.getConfig("myEducation"), "Education is not matched!");
	}
	
}
