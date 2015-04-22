package sample.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "user")
public class User extends BaseEntity {
	
	@Indexed
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	@JsonIgnore
	private String password;
	private String username;
	@JsonIgnore
	private Long roleId;
	
	public User() {}
	
	public User(String id, Long userId, String firstName, String lastName, String email,
			String password, String username, Long roleId) {
		super();
		this.setId(id);
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.username = username;
		this.roleId = roleId;
	}

	public User(Long userId, String firstName, String lastName, String email,
			String password, String username, Long roleId) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.username = username;
		this.roleId = roleId;
	}

	public User(User user) {
		super();
		this.userId = user.userId;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.email = user.email;
		this.password = user.password;
		this.username = user.username;
		this.roleId = user.roleId;
		this.setId(user.getId());
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
