package sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.User;

/**
 * Mongo repository for the users
 * @author pmincz
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
	public User findByUserId(Long userId);
	public User findByUsername(String username);
}