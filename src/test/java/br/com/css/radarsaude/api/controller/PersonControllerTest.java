package br.com.css.radarsaude.api.controller;

import br.com.css.radarsaude.domain.model.entity.Gender;
import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.repository.PersonRepository;
import br.com.css.radarsaude.domain.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTest {

    private Person validPerson;
    private Person invalidPerson;
    private final String BASE_URI = "/api/persons/";


    long id = 4; //Id da pessoal que será utilizado nos testes

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService service;

    @Autowired
    private ObjectMapper mapper;

    String personJson;


    @BeforeEach
    void setUp() {
        validPerson = Person.builder()
                .birthDate(LocalDate.of(1990, 6, 10))
                .email("person@person.com")
                .gender(Gender.MALE)
                .name("Test Person")
                .phoneNumber("48123456789")
                .build();

        invalidPerson = Person.builder().build();

        try {
            personJson = mapper.writeValueAsString(validPerson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("Testa save")
    void save() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URI)
                        .content(personJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

    }

    @Test
    @DisplayName("Testa Update deveria retornar 200 OK e LastUpdateDate maior que lastUpdateDate do responseBody")
    void update() throws Exception {


        Person person = service.findById(id);

        person.setBirthDate(LocalDate.of(2022, 01, 01));

        String json = mapper.writeValueAsString(person);

        mockMvc.perform(RestDocumentationRequestBuilders.put(BASE_URI + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("2022-01-01"));
    }

    @Test
    @DisplayName("Testa atualização parcial de uma pessoa deveria retorna 200 OK se o nome foi alterado corretamente.")
    void patch() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.patch(BASE_URI + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Zacarias\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Zacarias"));
    }

    @Test
    @DisplayName("Testa exclusão lógica de uma pessoa, deveria retornar 200 OK")
    void exclude() throws Exception {

        Person person = service.findById(id);
        person.setExcluded(false);
        service.save(person);

        mockMvc.perform(RestDocumentationRequestBuilders.delete(BASE_URI+ id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(service.findById(id).isExcluded());
    }

    @Test
    @DisplayName("testa findAll com page=0, size=10, direction=ASC, sort=name deveria retorna a mesma Quantidade de registos do qtdRegistrosBD")
    void findAll() throws Exception {
        long qtdRegistrosBD = service.findAll( PageRequest.of(0,10, Sort.Direction.valueOf("ASC"), "name")).getTotalElements();

        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("totalElements").value(qtdRegistrosBD));
    }

    @Test
    void find() {
    }
}