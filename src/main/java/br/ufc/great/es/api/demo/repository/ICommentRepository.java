package br.ufc.great.es.api.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.great.es.api.demo.model.Comment;
import br.ufc.great.es.api.demo.model.Person;


/**
 * Interface repositório de Usuário baseada no JPARepository do Spring
 * @author armandosoaressousa
 *
 */
@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPerson(Person person);
	List<Comment> findByDescription(String description);
}