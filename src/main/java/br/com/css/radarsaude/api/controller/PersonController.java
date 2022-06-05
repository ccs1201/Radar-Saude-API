package br.com.css.radarsaude.api.controller;

import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.model.representation.PersonResponse;
import br.com.css.radarsaude.domain.model.representation.util.mapper.PersonMapper;
import br.com.css.radarsaude.domain.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RequestMapping(value = "/api/persons", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
@AllArgsConstructor
public class PersonController {


    PersonService personService;
    PersonMapper personMapper;

    @PostMapping
    @Operation(description = "Save an Person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse save(@Valid @RequestBody Person person) {

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
    public PersonResponse patch(@PathVariable Long personId, @RequestBody Map<String, Object> valuesToUpdate) {

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
    @Parameters({
            @Parameter(name = "pageNumber", description = "Number of page result"),
            @Parameter(name = "pageSize", description = "Number of elements to be returned per page"),
            @Parameter(name = "orderBy", description = "The field by which the result should be sorted "),
            @Parameter(name = "direction", description = "The Direction of sorting ASC (ASCENDING) or DESC (DESCENDING)")})
    public Page<PersonResponse> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        Page<Person> persons = personService.findAll(PageRequest.of(pageNumber, pageSize, direction, orderBy));

        return personMapper.toPage(persons);

    }

    @GetMapping("{personId}")
    @Operation(description = "Find an Person by their ID")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponse findById(@PathVariable Long personId) {

        return personMapper.toResponseModel(personService.findById(personId));
    }


    @GetMapping("/find")
    @Operation(description = "Find persons by their name and or e-email")
    @Parameters({
            @Parameter(name = "pageNumber", description = "Number of page result"),
            @Parameter(name = "pageSize", description = "Number of elements to be returned per page"),
            @Parameter(name = "orderBy", description = "The field by which the result should be sorted "),
            @Parameter(name = "direction", description = "The Direction of sorting ASC (ASCENDING) or DESC (DESCENDING)"),
            @Parameter(name = "name", description = "Name of the person to search."),
            @Parameter(name = "email", description = "Email of the Person to search")})
    public Page<PersonResponse> find(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(defaultValue = "name") String orderBy,
                                     @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                     @RequestParam @Nullable String name,
                                     @RequestParam @Nullable String email) {

        Page<Person> persons = personService.findByNameOrEmailContaining(
                name, email, PageRequest.of(pageNumber, pageSize, direction, orderBy));

        return personMapper.toPage(persons);
    }

}
