package sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	public User findByUserId(Long userId);
	public User findByUsername(String username);
}