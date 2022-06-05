package br.com.css.radarsaude.domain.model.representation;

import br.com.css.radarsaude.domain.model.entity.Gender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Schema(description = "Representation of an person used for responses")
public class PersonResponse {

    private Long id;

    private String name;

    private Gender gender;

    private LocalDate birthDate;

    private String phoneNumber;

    private String email;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;

    private boolean excluded;
}
