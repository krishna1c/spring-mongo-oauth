package sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.Counter;

public interface CounterRepository extends MongoRepository<Counter, String> {}
