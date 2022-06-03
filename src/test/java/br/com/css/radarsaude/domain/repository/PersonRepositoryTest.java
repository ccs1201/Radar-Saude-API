package br.com.css.radarsaude.domain.repository;

import br.com.css.radarsaude.domain.model.entity.Gender;
import br.com.css.radarsaude.domain.model.entity.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonRepositoryTest {

    Person person;
    @Autowired
    PersonRepository repository;

    @BeforeAll
    void setUp() {
        person = Person.builder()
                .birthDate(LocalDate.of(1990, 6, 10))
                .email("person@person.com")
                .gender(Gender.MALE)
                .name("Test Person")
                .phoneNumber("48123456789")
                .build();
        savePerson();
    }

    @AfterAll
    @DisplayName("Tenta excluir a Person usada nos testes.")
    void deletePerson() {
        repository.deleteById(person.getId());

        assertTrue(repository.findById(person.getId()).isEmpty());
    }

    @Test
    @DisplayName("Tenta salvar um Person")
    void savePerson() {
        Person persisted = repository.save(person);

        assertEquals(persisted.getId(), person.getId());

    }

    @Test
    @DisplayName("Alterando a name da Person para novo teste")
    void updatePerson(){

        Person personFromBD = repository.findAll().get(0);
        personFromBD.setName("novo teste");
        long id = personFromBD.getId();

        Person updated = repository.save(personFromBD);

        assertEquals("novo teste", updated.getName());
        assertEquals(id, updated.getId());
        assertTrue(updated.getCreationDate().isBefore(updated.getLastUpdateDate()));
    }

    @Test
    @DisplayName("Buscando Person pelo ID")
    void findById(){
        assertTrue(repository.findById(person.getId()).isPresent());
    }



}