package br.com.css.radarsaude.domain.model.representation.util.mapper;

import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.model.representation.PersonResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PersonMapper implements MapperInterface<PersonResponse, Person> {

    ModelMapper modelMapper;

    @Override
    public PersonResponse toResponseModel(Person person) {
        return modelMapper.map(person, PersonResponse.class);
    }

    @Override
    public Page<PersonResponse> toPage(Page<Person> page) {
        return page.map(this::toResponseModel);
    }

    @Override
    public Collection<PersonResponse> toCollection(Page<Person> page) {
        return page.stream()
                .toList()
                .stream()
                .map(this::toResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PersonResponse> toCollection(Collection<Person> collection) {
        return collection.stream()
                .map(this::toResponseModel)
                .collect(Collectors.toList());
    }
}
