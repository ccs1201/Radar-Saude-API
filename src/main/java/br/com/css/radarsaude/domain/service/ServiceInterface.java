package br.com.css.radarsaude.domain.service;

import br.com.css.radarsaude.domain.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Interface para as classes de serviço
 * todas as classes que a implementarem
 * deve ser anotados com:
 *
 *<p></p>
 *
 * @param <ENTITY> classe de entidade
 *                 que a implementação representa.
 *<p></p>
 *
 * @<code>@EntityServiceType</code> para
 * garantir a desambiguação de IoC do Spring.
 *
 * <p></p>
 *
 * Não esqueça de acionar no @code{enum ServiceEntityType}
 * as Entidades que devem ser anotadas.
 */

interface ServiceInterface<ENTITY> {


    ENTITY save(ENTITY entity);

    ENTITY update(Long id, ENTITY entity);

    Person patch(Long id, Map<String, Object> updateValues);

    ENTITY findById(Long id);

    void exclude(Long id);

    Page<Person> findAll(Pageable pageable);

    Page<ENTITY> findByNameOrEmailContaining(String nome, String email, Pageable pageable);

    Page<ENTITY> findExcluded(Boolean excluded, Pageable pageable);

}
