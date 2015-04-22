package sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	public Role findByRoleId(Long roleId);
}
