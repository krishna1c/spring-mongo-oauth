package sample.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public abstract class BaseEntity {
	@JsonIgnore
	@Id
    private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
