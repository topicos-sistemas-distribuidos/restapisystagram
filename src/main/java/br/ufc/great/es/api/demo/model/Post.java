package br.ufc.great.es.api.demo.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Post extends AbstractModel<Long>{
	private static final long serialVersionUID = 1L;
	private Date date;
	private int likes=0;

	@OneToOne(fetch = FetchType.LAZY)
	private Person person;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Picture picture;
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Comment> comments = new LinkedList<>();
	
	public Post() {
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public void addComment(Comment comment) {
		this.getComments().add(comment);
	}
}
