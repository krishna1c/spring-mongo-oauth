package sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.Role;

/**
 * Mongo repository for the roles
 * @author pmincz
 *
 */
public interface RoleRepository extends MongoRepository<Role, String> {
	public Role findByRoleId(Long roleId);
	public Role findByIsDefault(Boolean isDefault);
}
