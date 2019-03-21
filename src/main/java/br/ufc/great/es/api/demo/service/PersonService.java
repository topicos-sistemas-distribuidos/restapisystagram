package br.ufc.great.es.api.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.ufc.great.es.api.demo.model.Person;
import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.repository.IPersonRepository;

/**
 * Class the manipulate the repository of Person
 * @author armandosoaressousa
 *
 */
@Service
public class PersonService extends AbstractService<Person, Long>{
	@Autowired
	IPersonRepository personRepository;
	
	//public PromotionArea globalPromotionArea = PromotionArea.getInstance();

	@Override
	protected JpaRepository<Person, Long> getRepository() {
		return personRepository;
	}
	
	/**
	 * Find a person by User
	 * @param user User
	 * @return Person
	 */
	public Person getPersonByUser(Users user) {
		return personRepository.findByUser(user);
	}
	
	/**
	 * Find a person by name
	 * @param name 
	 * @return Person
	 */
	public Person getPersonByName(String name) {
		return personRepository.findByName(name);
	}
		
}