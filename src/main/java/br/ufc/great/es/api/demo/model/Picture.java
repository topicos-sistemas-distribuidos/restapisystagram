package br.ufc.great.es.api.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Picture extends AbstractModel<Long>{
	private static final long serialVersionUID = 1L;
	private String name;
	private String path;
	private String systemName;
	
	@OneToOne
	private Person person;
	@OneToOne(cascade=CascadeType.ALL)
	private Post post;
	
	public Picture() {
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}
