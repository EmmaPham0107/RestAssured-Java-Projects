package Lab8_Practice;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class invalidGet {
	
	private int userId = 000;
	private Response response;
	private JsonPath bodyJson;
	
	@BeforeClass
	public void initMethod() {
		
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users/{userId}";
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.pathParam("userId", userId);
		
		response = request.get();
		bodyJson = response.jsonPath();
		System.out.println(response.asString());
		
	}
	
	@Test
	public void TC08_StatusCodeChecked() {
		Assert.assertEquals(response.getStatusCode(), 404, "Status check Failed!");
	}
	@Test
	public void TC09_MessageChecked() {
		Assert.assertTrue(response.asString().contains("message"), "Message field check Failed!");
	}
	@Test
	public void TC10_verifyOnContentOfMessage() {
		Assert.assertEquals(bodyJson.getString("message"), "User not found!","Content of message shows incorrect!");
	}
	
	@AfterClass
	public void afterClass() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
}
