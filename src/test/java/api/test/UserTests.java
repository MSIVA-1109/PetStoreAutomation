package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayLoad;
	public Logger logger;
	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayLoad = new User();
		userPayLoad.setId(faker.idNumber().hashCode());
		userPayLoad.setUsername(faker.name().username());
		userPayLoad.setFirstName(faker.name().firstName());
		userPayLoad.setLastName(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		userPayLoad.setPassword(faker.internet().password(5, 10));
		userPayLoad.setPhone(faker.phoneNumber().cellPhone());
		//logs
		logger=LogManager.getLogger(this.getClass());
	}
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("******Createing User******");
		Response response=UserEndpoints.createUser(userPayLoad);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******User Created********");
	}
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("*******Reading User Info********");
		Response response=UserEndpoints.readUser(this.userPayLoad.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******User Info is Displayed********");
	}
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("*******Updating User Info********");
		//Update data using payload
		userPayLoad.setFirstName(faker.name().firstName());
		userPayLoad.setLastName(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		Response response=UserEndpoints.updateUser(this.userPayLoad.getUsername(), userPayLoad);
		response.then().log().body();
//		response.then().log().body().statusCode(200);	//Rest Assured Chai Assertion
		Assert.assertEquals(response.getStatusCode(), 200);
		//Checking Data after update
		Response responseafterupdate=UserEndpoints.readUser(this.userPayLoad.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
		logger.info("*******User Info Updated********");
	}
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("*******Deleting User********");
		Response response=UserEndpoints.deleteUser(this.userPayLoad.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******User Deleted********");
	}
}
