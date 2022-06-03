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
    }

    @AfterAll
    @Test
    @DisplayName("Tenta excluir a Person usada nos testes.")
    void deletePerson() {

    }

    @Test
    @DisplayName("Testa salvar um Person válido!")
    void savePerson() {
        Person persisted = repository.save(person);

        assertEquals(persisted.getId(), person.getId());

    }


}