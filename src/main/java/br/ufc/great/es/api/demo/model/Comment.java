package br.ufc.great.es.api.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment extends AbstractModel<Long>{
	private static final long serialVersionUID = 1L;
	private String description;
	private Date date;

	@JsonBackReference(value="comment-person")
	@OneToOne(fetch = FetchType.LAZY)
	private Person person;
	
	public Comment() {
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
