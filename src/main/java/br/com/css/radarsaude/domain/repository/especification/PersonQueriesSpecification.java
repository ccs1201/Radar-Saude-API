package br.com.css.radarsaude.domain.repository.especification;

import br.com.css.radarsaude.domain.model.entity.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonQueriesSpecification {

    public static Specification<Person> namelike(String name) {

        return (root, query, builder) ->
                builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Person> emailLike(String email) {
        return (root, query, builder) ->
                builder.like(root.get("email"), "%" + email + "%");
    }

}
