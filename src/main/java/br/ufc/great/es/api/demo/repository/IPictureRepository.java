package br.ufc.great.es.api.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.great.es.api.demo.model.Person;
import br.ufc.great.es.api.demo.model.Picture;


@Repository
public interface IPictureRepository extends JpaRepository<Picture, Long>{
	List<Picture> findByPerson(Person person);
}
