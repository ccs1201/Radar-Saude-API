package br.com.css.radarsaude.domain.service;

import br.com.css.radarsaude.domain.annotation.ServiceEntityType;
import br.com.css.radarsaude.domain.annotation.EntityServiceType;
import br.com.css.radarsaude.domain.exception.persistence.EntityDataIntegrityViolationException;
import br.com.css.radarsaude.domain.exception.persistence.EntityPersistException;
import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.model.representation.util.GenericEntityUpdateMergerUtil;
import br.com.css.radarsaude.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
@AllArgsConstructor
@EntityServiceType(ServiceEntityType.PERSON)
public class PersonService implements ServiceInterface<Person> {

    PersonRepository repository;
    GenericEntityUpdateMergerUtil mergerUtil;


    @Override
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
    public Person update(Long id, Map<String, Object> valuesToUpdate) {
        Person personToUpdate = this.findById(id);

        mergerUtil.updateModel(valuesToUpdate, personToUpdate, Person.class);

        return repository.save(personToUpdate);
    }

    @Override
    public Person findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Pessoa com ID: %d não existe.", id)));
    }

    @Override
    public void exclude(Long id) {

        Person person = this.findById(id);

        person.setExcluded(true);

        repository.save(person);

    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Person> findByNameOrEmailContaining(String nome, String email, Pageable pageable) {
        return repository.findByNameOrEmailContaining(nome, email, pageable);
    }

    @Override
    public Page<Person> findExcluded(Boolean excluded, Pageable pageable) {
        return repository.findbyExcluded(excluded, pageable);
    }
}
