package br.ufc.great.es.api.demo.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Person extends AbstractModel<Long>{
	private static final long serialVersionUID = 1L;
	@JsonBackReference(value="user-person")
	@OneToOne(fetch = FetchType.LAZY)
	private Users user;
	@Column(length=255)
	private String name;
	@Column(length=255)
	private String address;
	@Column(length=255)
	private String city;
	@Column(length=255)
	private String state;
	@Column(length=8)
	private String cep;
	private double latitude=0;
	private double longitude=0;

	@JsonBackReference(value="person-comment")
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Comment> comments = new LinkedList<>();
	
	@JsonBackReference(value="person-picture")
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Picture> pictures = new LinkedList<>();
	
	@JsonBackReference(value="person-post")
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Post> posts = new LinkedList<>();

	public Person() {
	}
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	
	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void addComment(Comment comment, Person person) {
		comment.setPerson(person);
		this.getComments().add(comment);
	}
	public Comment getMyComment(Long id) {
		List<Comment> list = this.getComments();
		
		for (Comment element : list) {
			if (element.getId()==id) {
				return element;
			}
		}
		return null;
	}

	public void addPicture(Picture picture, Person person) {
		picture.setPerson(person);
		this.getPictures().add(picture);
	}
	public Picture getMyPicture(Long id) {
		List<Picture> list = this.getPictures();
		
		for (Picture element : list) {
			if (element.getId()==id) {
				return element;
			}
		}
		return null;
	}

	public void addPost(Post post) {
		this.posts.add(post);
	}
	
}