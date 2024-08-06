package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userID,String userName,String fname,String lname,String useremail,String pwd,String phoneno)
	{
		User UserPayload=new User();
		UserPayload.setId(Integer.parseInt(userID));
		UserPayload.setUsername(userName);
		UserPayload.setFirstName(fname);
		UserPayload.setLastName(lname);
		UserPayload.setEmail(useremail);
		UserPayload.setPassword(pwd);
		UserPayload.setPhone(phoneno);
		Response response=UserEndpoints.createUser(UserPayload);
		Assert.assertEquals(response.getStatusCode(),200);
	}
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)

	public void testDeletUserByName(String userName)
	{
		Response response=UserEndpoints.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
