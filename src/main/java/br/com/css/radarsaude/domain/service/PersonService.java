package br.com.css.radarsaude.domain.service;

import br.com.css.radarsaude.domain.exception.persistence.EntityDataIntegrityViolationException;
import br.com.css.radarsaude.domain.exception.persistence.EntityNotFoundException;
import br.com.css.radarsaude.domain.exception.persistence.EntityPersistException;
import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.model.representation.util.GenericEntityUpdateMergerUtil;
import br.com.css.radarsaude.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@Service
@AllArgsConstructor
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
    public Person patch(Long personId, Map<String, Object> updateValues) {

        Person personToUpdate = this.findById(personId);

        mergerUtil.updateModel(updateValues, personToUpdate, Person.class);

        return repository.save(personToUpdate);
    }

    @Override
    public Person findById(Long personId) {
        return repository.findById(personId).orElseThrow(() -> new EntityNotFoundException(String.format("Pessoa com ID: %d não existe.", personId)));
    }

    @Override
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
    public Page<Person> findByNameOrEmailContaining(String nome, String email, Pageable pageable) {
        Page<Person> page = repository.findByNameOrEmailContaining(nome, email, pageable);

        if (page.isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("Nenhum registro encontrado com os parâmetros: Nome: %s e Email: %s", nome, email));
        }

        return page;
    }

    @Override
    public Page<Person> findExcluded(Boolean excluded, Pageable pageable) {
        return repository.findExcluded(excluded, pageable);
    }
}
