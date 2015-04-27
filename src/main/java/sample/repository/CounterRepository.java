package sample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import sample.domain.Counter;

/**
 * Mongo repository for the counters
 * @author pmincz
 *
 */
public interface CounterRepository extends MongoRepository<Counter, String> {}
