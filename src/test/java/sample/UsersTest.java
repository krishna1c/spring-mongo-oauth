package sample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import sample.domain.User;
import sample.domain.UserEntry;
import sample.repository.UserRepository;
import sample.util.AbstractRestTest;
import sample.util.IntegrationTestUtil;

public class UsersTest extends AbstractRestTest {
	
	@Autowired
	private UserRepository userRepo;

	@Test
	public void getUsers() throws Exception {
		// @formatter:off
		mvc.perform(get("/users")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// @formatter:on
	}
	
	@Test
	public void getUser() throws Exception {
		// @formatter:off
		mvc.perform(get("/users/-1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));
		// @formatter:on
	}
	
	@Test
	public void postUser() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		
		UserEntry userEntry = new UserEntry("fistName", "lastName", "email", "password", "username");
		
		mvc.perform(
						post("/users").header("Authorization", "Bearer " + accessToken)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(IntegrationTestUtil.convertObjectToJsonBytes(userEntry)))
								.andExpect(status().isOk());
	}
	
	@Test
	public void deleteUser() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		postUser();
		User user = userRepo.findByUsername("username");
		
		mvc.perform(MockMvcRequestBuilders.delete("/users/"+user.getUserId()).header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void updateUser() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		postUser();
		User user = userRepo.findByUsername("username");
		
		UserEntry userEntry = new UserEntry("fistName2", "lastName2", "email2", "password2", "username2");
		
		mvc.perform(
				MockMvcRequestBuilders.put("/users/"+user.getUserId()).header("Authorization", "Bearer " + accessToken)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(IntegrationTestUtil.convertObjectToJsonBytes(userEntry)))
								.andExpect(status().isOk());
	}
	
	@Test
	public void getUsersSummary() throws Exception {
		// @formatter:off
		mvc.perform(get("/users/summary")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		// @formatter:on
	}

}
