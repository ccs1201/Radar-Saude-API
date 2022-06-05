package br.com.css.radarsaude.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Entity
@Schema(description = "Full implementation of an person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @Column(updatable = false)
    @EqualsAndHashCode.Include
    @JsonIgnore
    private Long id;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    private Gender gender;

    @NotNull
    @PastOrPresent(message = "Data de Nascimento é posterior a data atual")
    @Column(nullable = false)
    private LocalDate birthDate;

    @NotBlank(message = "Número do telefone não pode estar vazio.")
    @Length(min = 10, max = 11, message = "Número telefone inválido.\n Telefone deve conter entre 10 e 11 dígitos e conter somente números sem formatação.\n Ex 4831215000")
    @Column(length = 11, nullable = false)
    private String phoneNumber;

    @NotNull
    @Email(message = "E-mail é obrigatório.")
    @Column(length = 50, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    @JsonIgnore
    @Column(nullable = false)
    private boolean excluded;


}
