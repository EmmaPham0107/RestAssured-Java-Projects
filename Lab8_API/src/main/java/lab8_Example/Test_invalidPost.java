package lab8_Example;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Test_invalidPost {

	private UserInfo user;
	private Response response;
	private JsonPath bodyJson;
	
	@BeforeClass
	public void initMethod() {
		
		user = new UserInfo (null, "Nu", 25, "Ke toan");
		RestAssured.baseURI = "https://reqres.in/api/users";
		RestAssured.basePath = "/api/users";
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(user);
		
		response = request.post();
		bodyJson = response.jsonPath();
	}
	
	@Test
	public void TC08_StatusCodeChecked() {
		Assert.assertEquals(response.getStatusCode(), 400, "Status check Failed!");
	}
	
	@Test
	public void TC09_MessageFieldChecked() {
		Assert.assertTrue(response.asString().contains("message"),"Message field check Failed!");
	}
	
	@Test
	public void TC10_VerifyOnMessageContainName() {
		String resName = bodyJson.getString("message");
		if(resName==null) {
			resName = "";
		}
		Assert.assertTrue(resName.contains("name"),"Message does not contain invalid field!");
	}
	
	@AfterClass
	public void afterClass() {
		RestAssured.baseURI = null;
		RestAssured.basePath = null;
	}
	
}
