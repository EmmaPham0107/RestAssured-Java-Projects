package Lab8_Practice;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class validGet {
	private int userId = 1;
	private Response response;
	private JsonPath bodyJson;
	
	@BeforeClass
	public void initMethod() {
		
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users/{userId}";
		
		RequestSpecification request = RestAssured.given()
				.pathParam("userId", userId);
		
		response = request.get();
		bodyJson = response.jsonPath();
		System.out.println(response.asPrettyString());
		
	}
	
	@Test
	public void TC01_StatusCodeChecked() {
		Assert.assertEquals(response.getStatusCode(), 200, "Status check Failed!");
	}
	@Test
	public void TC02_idChecked() {
		Assert.assertTrue(response.asString().contains("id"), "Id check Failed!");
	}
	@Test
	public void TC03_emailChecked() {
		Assert.assertTrue(response.asString().contains("email"), "Email check Failed!");
	}
	@Test
	public void TC04_firstnameChecked() {
		Assert.assertTrue(response.asString().contains("first_name"), "First name check Failed!");
	}
	@Test
	public void TC05_lastnameChecked() {
		Assert.assertTrue(response.asString().contains("last_name"), "Last name check Failed!");
	}
	@Test
	public void TC06_avatarChecked() {
		Assert.assertTrue(response.asString().contains("avatar"), "Avatar check Failed!");
	}
	@Test
	public void TC07_verifyOnMatchingId() {
		Assert.assertEquals(bodyJson.getInt("data.id"), userId, "Id is not matched!");
	}
	
	@AfterClass
	public void afterClass() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
}
