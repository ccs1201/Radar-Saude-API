package br.com.css.radarsaude.api.controller;

import br.com.css.radarsaude.domain.model.entity.Gender;
import br.com.css.radarsaude.domain.model.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTest {

    Person validPerson;
    Person invalidPerson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonController controller;

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

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/persons")
                .content(personJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());

    }

    @Test
    @DisplayName("Testa Update deveria retornar 200 OK e LastUpdateDate maior que lastUpdateDate do responseBody")
    void update() throws Exception {

        Person person = new Person();

        String responseBody = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/persons/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        BeanUtils.copyProperties(responseBody,person);

        person.setBirthDate(LocalDate.of(2022,01,01));

        String json = mapper.writeValueAsString(person);

        String afterupdateResponseBody = mockMvc.perform(RestDocumentationRequestBuilders.put("/api/persons/4")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();


    }

    @Test
    void patch() {
    }

    @Test
    void exclude() {
    }

    @Test
    void findAll() {
    }

    @Test
    void find() {
    }
}