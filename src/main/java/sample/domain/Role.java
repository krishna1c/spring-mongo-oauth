package sample.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "role")
public class Role extends BaseEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
		
	public Role(Long roleId, String name, Boolean isDefault) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.isDefault = isDefault;
	}

	private Long roleId;
	private String name;
	private Boolean isDefault;

	@Override
	public String getAuthority() {
		return name;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
