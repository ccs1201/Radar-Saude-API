package br.com.css.radarsaude.domain.service;

import br.com.css.radarsaude.domain.exception.persistence.EntityDataIntegrityViolationException;
import br.com.css.radarsaude.domain.exception.persistence.EntityNotFoundException;
import br.com.css.radarsaude.domain.exception.persistence.EntityPersistException;
import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.model.representation.util.GenericEntityUpdateMergerUtil;
import br.com.css.radarsaude.domain.repository.PersonRepository;
import br.com.css.radarsaude.domain.repository.especification.PersonQueriesSpecification;
import br.com.css.radarsaude.domain.service.exception.NoRequestParameterFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonService implements ServiceInterface<Person> {

    private final PersonRepository repository;
    @Lazy
    private final GenericEntityUpdateMergerUtil mergerUtil;


    @Override
    @Transactional
    public Person save(Person entity) {
        try {
            return repository.save(entity);

        } catch (IllegalArgumentException e) {
            throw new EntityPersistException(String.format("Erro ao salva Pessoa. \nDetalhes:\n %s", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException("Dados inválidos. Cheque os campos obrigatórios. Verifique os Detalhes:\n ", e);
        }

    }

    @Override
    @Transactional
    public Person update(Long id, Person person) {

        Person personToUpdate = this.findById(id);
        BeanUtils.copyProperties(person, personToUpdate, "id");
        try {
            return repository.save(personToUpdate);
        } catch (ConstraintViolationException e) {
            throw new EntityDataIntegrityViolationException("Erro ao Atualizar Person", e);
        }

    }

    @Override
    @Transactional
    public Person patch(Long personId, Map<String, Object> updateValues) {

        Person personToUpdate = this.findById(personId);

        mergerUtil.updateModel(updateValues, personToUpdate, Person.class);

        return repository.save(personToUpdate);
    }

    @Override
    public Person findById(Long personId) {
        return repository.findById(personId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Pessoa com ID: %d não existe.", personId)));
    }

    @Override
    @Transactional
    public void exclude(Long personId) {

        Person person = this.findById(personId);

        person.setExcluded(true);

        repository.save(person);

    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Person> findByNameOrEmailContaining(String name, String email, Pageable pageable) {

        if (!StringUtils.hasText(name) && !StringUtils.hasText(email)) {
            throw new NoRequestParameterFoundException("Nenhum valor informado para consulta.\n Informe ao menos um dos seguintes parâmetros: name ou email");
        }

        Page<Person> page;

        if (StringUtils.hasText(name) && StringUtils.hasText(email)) {
            page = repository.findByNameIgnoreCaseOrEmailContainingIgnoreCase(name, email, pageable);

        } else if (StringUtils.hasText(name)) {
            page = repository.findByNameContainingIgnoreCase(name, pageable);

        } else {
            page = repository.findByEmailContainingIgnoreCase(email, pageable);
        }

        if (page.isEmpty()) {
            throw new EntityNotFoundException(String.format("Nenhum registro encontrado com os parâmetros: Nome: %s e Email: %s", name, email));
        }

        return page;
    }

    @Override
    public Page<Person> findExcluded(Boolean excluded, Pageable pageable) {
        return repository.excludedIs(excluded, pageable);
    }

    @Override
    public Page<Person> findByNameOrEmailCriteria(String name, String email, Pageable pageable) {

        if (!StringUtils.hasText(name) && !StringUtils.hasText(email)) {
            throw new NoRequestParameterFoundException("Nenhum valor informado para consulta.\n Informe ao menos um dos seguintes parâmetros: name ou email");
        }

        if (StringUtils.hasText(name) && StringUtils.hasText(email)) {
            return repository.findAll(PersonQueriesSpecification.namelike(name).or(PersonQueriesSpecification.emailLike(email)), pageable);

        } else if (StringUtils.hasText(name)) {
            return repository.findAll(PersonQueriesSpecification.namelike(name), pageable);

        } else {
            return repository.findAll(PersonQueriesSpecification.emailLike(email), pageable);
        }
    }
}
