package br.com.css.radarsaude.api.controller;

import br.com.css.radarsaude.domain.model.entity.Gender;
import br.com.css.radarsaude.domain.model.entity.Person;
import br.com.css.radarsaude.domain.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTest {

    private Person validPerson;
    private final String BASE_URI = "/api/persons/";
    private final PageRequest PAGE_REQUEST = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
    private final String URI_PAGE_REQUEST = String.format("?page=%d&size=1%d&order=%s&direction=%s", 0, 10, "name", Sort.Direction.ASC);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService service;

    @Autowired
    private ObjectMapper mapper;

    String personJson;

    //MÉTODO USADO PRA POPULAR O BANCO COM DADOS PARA TESTE
       private void populateDataForTest(){
           for (int i = 0; i < 100; i++) {
               Person person = Person.builder()
                       .birthDate(LocalDate.of(1990, 6, 10).plusDays(i))
                       .email(String.format("person%d@person%d.com", i, i))
                       .gender(Gender.MALE)
                       .name(String.format("Person %d", i))
                       .phoneNumber("0000000000")
                       .build();

               service.save(person);
           }
       }

    @BeforeAll
    void setUp() {
        populateDataForTest();

        validPerson = Person.builder()
                .birthDate(LocalDate.now())
                .email("personcontrollertest@personcontrolertest.com")
                .gender(Gender.FEMALE)
                .phoneNumber("0000000000")
                .name("personcontrollertest")
                .build();

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
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Testa Update deveria retornar 200 OK e LastUpdateDate maior que lastUpdateDate do responseBody")
    void update() throws Exception {

        long id = generateRandomId();

        Person person = service.findById(id);

        person.setBirthDate(LocalDate.of(2022, 01, 01));

        String json = mapper.writeValueAsString(person);

        mockMvc.perform(RestDocumentationRequestBuilders.put(BASE_URI + id)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate").value("2022-01-01"));
    }

    @Test
    @DisplayName("Testa atualização parcial de uma pessoa deveria retorna 200 OK se o nome foi alterado corretamente.")
    void patch() throws Exception {

        long id = generateRandomId();

        mockMvc.perform(RestDocumentationRequestBuilders.patch(BASE_URI + id)
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Zacarias\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Zacarias"));
    }

    @Test
    @DisplayName("Testa exclusão lógica de uma pessoa, deveria retornar 200 OK")
    void exclude() throws Exception {

        long id = generateRandomId();

        Person person = service.findById(id);
        person.setExcluded(false);
        service.save(person);

        mockMvc.perform(RestDocumentationRequestBuilders.delete(BASE_URI + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertTrue(service.findById(id).isExcluded());
    }

    @Test
    @DisplayName("Testa findAll com a constante URI_PAGE_REQUEST e PAGE_REQUEST" +
            " deveria retorna a mesma Quantidade de registos do qtdRegistrosBD")
    void findAll() throws Exception {
        long qtdRegistrosBD = service.findAll(PAGE_REQUEST).getTotalElements();


        mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + URI_PAGE_REQUEST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalElements").value(qtdRegistrosBD));
    }

    @Test
    @DisplayName("Testa /find sem passar parâmetros deveria retornar 404 BAD_REQUEST")
    void findWithoutParameters() throws Exception {
        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/find" + URI_PAGE_REQUEST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("Testa /find somente com o nome da pessoal, " +
            "deveria retornar somente pessoas com nome informado e retornar 200 OK se ao menos um registro for localizado")
    void findByOnlyName() throws Exception {

        long id = generateRandomId();

        Person person = service.findById(id);

        String find = String.format("&name=%s", person.getName());

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/find" + URI_PAGE_REQUEST + find)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].name").isNotEmpty())
                .andReturn();

        Assertions.assertTrue(response.getResponse().getContentAsString().contains(person.getName()));
    }

    @Test
    @DisplayName("Testa /find somente com o email da pessoal, " +
            "deveria retornar somente pessoas com email informado e retornar 200 OK se ao menos um registro for localizado")
    void findByOnlyEmail() throws Exception {

        long id = generateRandomId();

        Person person = service.findById(id);

        String find = String.format("&email=%s", person.getEmail());

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/find" + URI_PAGE_REQUEST + find)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].email").isNotEmpty())
                .andReturn();

        Assertions.assertTrue(response.getResponse().getContentAsString().contains(person.getEmail()));
    }

    @Test
    @DisplayName("Testa /find com nome e email da pessoal, " +
            "deveria retornar somente pessoas com nome ou email informado e retornar 200 OK se ao menos um registro for localizado")
    void findByNameAndEmail() throws Exception {

        long id = generateRandomId();

        Person person = service.findById(id);

        String find = String.format("&email=%s&name=%s", person.getEmail(), person.getName());

        MvcResult response = mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URI + "/find" + URI_PAGE_REQUEST + find)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].email").isNotEmpty())
                .andReturn();

        Assertions.assertTrue(response.getResponse().getContentAsString().contains(person.getEmail()));
        Assertions.assertTrue(response.getResponse().getContentAsString().contains(person.getName()));

    }

    private Long generateRandomId() {
        return Double.valueOf(Math.random() * 100).longValue();
    }
}