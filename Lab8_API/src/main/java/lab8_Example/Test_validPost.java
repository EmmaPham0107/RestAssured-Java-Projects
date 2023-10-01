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



public class Test_validPost {
	
	private UserInfo user;
	private Response response;
	private JsonPath bodyJson;
	
	@BeforeClass
	public void initMethod() {
		
		user = new UserInfo ("Tuan", "Nam", 30, "Ky su");
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api/users";
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(user);
		
		//Tao post request
		response = request.post(); //luu response of API
		bodyJson = response.jsonPath(); //body of response da convert sang JSON
		System.out.println(response.asPrettyString());
		
	}
	
	@Test
	public void TC01_StatusCodeChecked () {
		Assert.assertEquals(response.getStatusCode(), 201, "Status check Failed!");
	}
	@Test
	public void TC02_idChecked () {
		Assert.assertTrue(response.asString().contains("id"), "Id field check Failed!");
	}
	@Test
	public void TC03_createdAtChecked () {
		Assert.assertTrue(response.asString().contains("createdAt"), "createdAt field check Failed!");
	}
	@Test
	public void TC04_verifyOnMatchingName () {
		Assert.assertEquals(user.getName(), bodyJson.getString("name"),"Name is not matched!");
	}
	@Test
	public void TC05_verifyOnMatchingGender() {
		Assert.assertEquals(user.getGender(), bodyJson.getString("gender"), "Gender is not matched!");
	}
	@Test
	public void TC06_verifyOnMatchingAge () {
		Assert.assertEquals(user.getAge(), bodyJson.getInt("age"), "Age is not matched!");
	}
	@Test
	public void TC07_verifyOnMatchingJob () {
		Assert.assertEquals(user.getJob(), bodyJson.getString("job"), "Job is not matched!");
	}
	
	@AfterClass
	public void afterTest() {
		RestAssured.basePath = null;
		RestAssured.baseURI = null;
	}
	
}
