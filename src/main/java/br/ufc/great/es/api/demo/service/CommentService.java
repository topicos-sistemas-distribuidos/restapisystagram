package br.ufc.great.es.api.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.ufc.great.es.api.demo.model.Comment;
import br.ufc.great.es.api.demo.model.Person;
import br.ufc.great.es.api.demo.repository.ICommentRepository;

@Service
public class CommentService extends AbstractService<Comment, Long>{
	@Autowired
	private ICommentRepository commentRepository;
	@Override
	protected JpaRepository<Comment, Long> getRepository() {
		return commentRepository;
	}

	/**
	 * Busca o comentario por pessoa
	 * @param person Pessoa
	 * @return comentario
	 */
	public List<Comment> getCommentByPerson(Person person) {
		return commentRepository.findByPerson(person);
	}
	
	/**
	 * Busca o comentário por descricao
	 * @param description Description
	 * @return comentario
	 */
	public List<Comment> getCommentByDescription(String description) {
		return commentRepository.findByDescription(description);
	}
}
