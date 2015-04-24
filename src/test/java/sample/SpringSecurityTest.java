package sample;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;

import sample.controller.UserRestController;
import sample.domain.UserEntry;
import sample.util.AbstractRestTest;
import sample.util.IntegrationTestUtil;


public class SpringSecurityTest extends AbstractRestTest {
	
	@InjectMocks
	UserRestController controller;
	
	@Test
	public void postUnauthorized() throws Exception {
		// @formatter:off
		mvc.perform(post("/users")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error", is("unauthorized")));
		// @formatter:on
	}

	@Test
	public void postAuthorized() throws Exception {
		String accessToken = getAccessToken("admin", "123456");
		
		UserEntry userEntry = new UserEntry("fistName", "lastName", "email", "password", "username");
		
		mvc.perform(
						post("/users").header("Authorization", "Bearer " + accessToken)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(IntegrationTestUtil.convertObjectToJsonBytes(userEntry)))
								.andExpect(status().isOk());
	}

}
