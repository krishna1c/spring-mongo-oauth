package sample.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Counter entity
 * @author pmincz
 *
 */
@Document(collection = "counter")
public class Counter extends BaseEntity {
	
	public Counter(String name, long sequence) {
		this.name = name;
		this.sequence = sequence;
	}

	private String name;
	private long sequence;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSequence() {
		return sequence;
	}
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

}
