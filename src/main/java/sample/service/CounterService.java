package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import sample.domain.Counter;
import sample.repository.CounterRepository;

@Service
public class CounterService {
	public static final String USER_ID_SEQUENCE_NAME = "user_id";
	public static final String EXPENSE_ID_SEQUENCE_NAME = "expense_id";

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CounterRepository repo;

	public long getNextUserIdSequence() {
		return increaseCounter(USER_ID_SEQUENCE_NAME);
	}
	
	public long getNextExpenseIdSequence() {
		return increaseCounter(EXPENSE_ID_SEQUENCE_NAME);
	}

	private long increaseCounter(String counterName){
		try {
			Query query = new Query(Criteria.where("name").is(counterName));
			Update update = new Update().inc("sequence", 1);
			Counter counter = mongoTemplate.findAndModify(query, update, Counter.class);
			return counter.getSequence();
		} catch(NullPointerException e) {
			repo.save(new Counter(counterName, 0L));
			return increaseCounter(counterName);
		}
	}
}
