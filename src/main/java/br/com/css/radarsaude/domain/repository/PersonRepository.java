package br.com.css.radarsaude.domain.repository;

import br.com.css.radarsaude.domain.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Page<Person> findByNameOrEmailContaining(String nome, String email, Pageable pageable);

    Page<Person> findExcluded(Boolean excluded, Pageable pageable);
}
