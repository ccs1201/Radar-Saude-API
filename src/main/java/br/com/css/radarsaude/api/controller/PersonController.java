package br.com.css.radarsaude.api.controller;

import br.com.css.radarsaude.domain.annotation.ServiceType;
import br.com.css.radarsaude.domain.annotation.ServiceTypeEntity;
import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.model.representation.PersonResponse;
import br.com.css.radarsaude.domain.model.representation.util.mapper.PersonMapper;
import br.com.css.radarsaude.domain.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/persons", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class PersonController {

    @ServiceType(ServiceTypeEntity.PERSON)
    PersonService personService;
    PersonMapper personMapper;

    @PostMapping
    @Operation(description = "Save an Person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse save(@RequestBody @Valid Person person) {

        return personMapper.toResponseModel(personService.save(person));
    }

    @PutMapping("/{personId}")
    @Operation(description = "Update an Person")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponse update(@PathVariable Long personId, @RequestBody @Valid Person person) {

        return personMapper.toResponseModel(personService.update(personId, person));
    }

    @PatchMapping("/{personId}")
    @Operation(description = "Partial update for an Person")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponse patch(@PathVariable Long personId, Map<String, Object> valuesToUpdate) {

        return personMapper.toResponseModel(personService.patch(personId, valuesToUpdate));
    }

    @DeleteMapping("/{personId}")
    @Operation(description = "Exclude an Person")
    @ResponseStatus(HttpStatus.OK)
    public void exclude(@PathVariable Long personId) {

        personService.exclude(personId);

    }

    @GetMapping
    @Operation(description = "list all Persons plus pageable")
    @ResponseStatus(HttpStatus.OK)
    public Page<PersonResponse> findAll(@PageableDefault(size = 20, direction = Sort.Direction.ASC, sort = "nome")
                                        Pageable pageable) {

        return null;

    }

    @RequestMapping("{/find}")
    @Operation(description = "Find persons by their name or e-email")
    public Page<PersonResponse> find() {
        return null;
    }

}
