package com.api.auto.testcase;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_Login extends TC_Base {
	
	protected RequestSpecification buildRequest() throws Exception {
		//Initiate Body 
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("password", PropertiesFileUtils.getConfig("password"));
		userInfo.put("account", PropertiesFileUtils.getConfig("account"));
		System.out.println("loginBody " + userInfo);
		//Initiate URL
		RestAssured.baseURI = PropertiesFileUtils.getConfig("baseURL");
		RestAssured.basePath = PropertiesFileUtils.getConfig("loginPath");
		return 	RestAssured.given()
				.contentType(ContentType.JSON)
				.body(userInfo);
	}

	@Test (priority = 0)
	public void validateLoginStatusCode() {
		//Kiểm chứng đăng nhập thành công với HTTP status code = 200 ok
		Assert.assertEquals(response.getStatusCode(),200, "Status code check Failed!");
	}
	@Test (priority = 1)
	public void validateMessage() {
		//Kiểm chứng Response trả về có trường message và nội dung của message là “Đăng nhập thành công”
		Assert.assertEquals(resBody.asString().contains("message"), true, "Message check Failed!");
		Assert.assertEquals(bodyJson.getString("message"), "Đăng nhập thành công", "Content of Message is not matched!");
	}

	@Test (priority = 2)
	public void validateToken() throws IOException {
		//Kiểm chứng Response trả về có chứa trường token
		Assert.assertEquals(resBody.asString().contains("token"), true, "Token check Failed!");
		//Lưu token
		PropertiesFileUtils.setToken("token", bodyJson.getString("token"));
	}
	@Test (priority = 3)
	public void validateUserType() {
		//Kiểm chứng Response trả về có chứa thông tin user và trường Type
		boolean isContainUserInfoAndTypeField = resBody.asString().contains("user") && resBody.asString().contains("type");
		Assert.assertEquals(isContainUserInfoAndTypeField, true, "User type check Failed!");
		//Kiểm chứng trường Type có phải là "UNGVIEN"
		Assert.assertEquals(bodyJson.getString("user.type"),"UNGVIEN", "Type is not matched!");
	}
	@Test (priority = 4)
	public void validateUserAccount() throws Exception {
		//Kiểm chứng Response trả về có chứa thông tin user và trường account
		boolean isContainUserInfoAndAccountField = resBody.asString().contains("user") && resBody.asString().contains("account");
		Assert.assertTrue(isContainUserInfoAndAccountField,"User account check Failed!");
		//Kiểm chứng trường account có khớp với account đăng nhập
		Assert.assertEquals(bodyJson.getString("user.account"),PropertiesFileUtils.getConfig("account"),"Account is not matched!");
		//Kiểm chứng Response trả về có chứa thông tin user và trường password
		boolean isContainUserInfoAndPasswordField = resBody.asString().contains("user") && resBody.asString().contains("password");
		Assert.assertTrue(isContainUserInfoAndPasswordField,"User password check Failed!");
		//Kiểm chứng trường password có khớp với password đăng nhập
		Assert.assertEquals(bodyJson.getString("user.password"),PropertiesFileUtils.getConfig("password"),"Password is not matched!");
	}
}

