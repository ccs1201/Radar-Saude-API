package br.com.css.radarsaude.domain.repository;

import br.com.css.radarsaude.domain.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    Page<Person> findByNameIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    Page<Person> excludedIs(Boolean excluded, Pageable pageable);

    Page<Person> findAll(Specification<Person> specification, Pageable pageable);

    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Person> findByEmailContainingIgnoreCase(String email, Pageable pageable);

}
